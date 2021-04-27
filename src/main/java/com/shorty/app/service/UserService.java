package com.shorty.app.service;

import com.shorty.app.entity.ConfirmationToken;
import com.shorty.app.entity.Redirect;
import com.shorty.app.entity.User;
import com.shorty.app.exception.BadRequestException;
import com.shorty.app.repository.UserRepository;
import com.shorty.app.request.RedirectDeletionRequest;
import com.shorty.app.request.UserCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    private final EmailSenderService emailSenderService;

    @Autowired
    public UserService(UserRepository userRepository, ConfirmationTokenService confirmationTokenService,
            EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSenderService = emailSenderService;
    }

    public Optional<User> createUser(UserCreationRequest userCreationRequest) {
        if (userRepository.existsByEmail(userCreationRequest.getEmail())) {
            throw new BadRequestException("Found an account linked to the email");
        }

        System.out.println("User Request " + userCreationRequest.toString());
        User newUser = new User(userCreationRequest.getEmail(), userCreationRequest.getPassword(),
                userCreationRequest.getName(), userCreationRequest.getRole(), new ArrayList<Redirect>());

        System.out.println("User" + newUser);
        User user = userRepository.save(newUser);
        final ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        sendConfirmationMail(user.getEmail(), confirmationToken.getConfirmationToken());
        return Optional.ofNullable(user);
    }

    public User loadUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Such email does not exist in our database!"));
        return user;
    }

    private void sendConfirmationMail(String userMail, String token) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(userMail);
        message.setSubject("Shorty App Confirmation Link");
        message.setFrom("<MAIL>");
        message.setText("Thank you for registering. Please click on the below link to activate your account."
                + "http://localhost:8080/users/confirm?token=" + token);
        emailSenderService.sendMail(message);
    }

    public void confirmUser(ConfirmationToken confirmationToken) {
        final User user = confirmationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);
        confirmationTokenService.deleteConfirmationToken(confirmationToken.getId());
    }

    public void addRedirectToUser(Redirect redirect, String email) {
        User user = loadUserByEmail(email);
        user.getRedirects().add(redirect);
        userRepository.save(user);
    }

    public void deleteRedirectFromUser(RedirectDeletionRequest redirectDeletionRequest, String email) {
        User user = loadUserByEmail(email);
        user.getRedirects().removeIf(redirect -> redirect.getAlias().equals(redirectDeletionRequest.getAlias()));
        userRepository.save(user);
    }
}
