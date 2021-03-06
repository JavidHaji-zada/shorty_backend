package com.shorty.app.service;

import com.shorty.app.entity.Redirect;
import com.shorty.app.entity.Session;
import com.shorty.app.entity.User;
import com.shorty.app.exception.BadRequestException;
import com.shorty.app.exception.NotFoundException;
import com.shorty.app.repository.RedirectRepository;
import com.shorty.app.request.RedirectCreationRequest;
import com.shorty.app.request.RedirectDeletionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RedirectService {

    private RedirectRepository redirectRepository;

    @Autowired
    public RedirectService(RedirectRepository redirectRepository) {
        this.redirectRepository = redirectRepository;
    }

    public Optional<Redirect> createRedirect(RedirectCreationRequest redirectCreationRequest, Session session) {
        if (redirectRepository.existsByAlias(redirectCreationRequest.getAlias())) {
            System.out.println("Alias already exists");
            throw new BadRequestException("Alias already exists");
        }
        String alias = redirectCreationRequest.getAlias();
        if (redirectCreationRequest.getAlias().isEmpty()) {
            alias = Redirect.generateRandomAlias();
            while (redirectRepository.existsByAlias(alias)) {
                alias = Redirect.generateRandomAlias();
            }
        }
        Redirect redirect;
        if (session != null) {
            redirect = new Redirect(alias, redirectCreationRequest.getUrl(), 0, session.getUser().getId());
        } else {
            redirect = new Redirect(alias, redirectCreationRequest.getUrl(), 0, null);
        }
        Redirect postSaveRedirect = redirectRepository.save(redirect);
        System.out.println("Redirect" + postSaveRedirect);

        return Optional.ofNullable(postSaveRedirect);
    }

    public List<Redirect> getRedirects(User user) {
        System.out.println("Find redirects by user " + user.getId());
        if (user.getRole().equals("ADMIN")) {
            System.out.println("User role is admin");
            return redirectRepository.findAll();
        } else {
            System.out.println("User role is user");
            return redirectRepository.findByUserID(user.getId());
        }
    }

    public void deleteRedirect(String alias) {
        if (!redirectRepository.existsByAlias(alias)) {
            throw new BadRequestException("Alias does not exist in our database");
        }
        redirectRepository.deleteByAlias(alias);
    }

    public Redirect getRedirect(String alias) {
        Redirect redirect = redirectRepository.findByAlias(alias)
                .orElseThrow(() -> new NotFoundException("Hey we don't have that alias ! Try making it"));
        return redirect;
    }

    public void updateRedirect(Redirect redirect) {
        redirectRepository.save((redirect));
    }
}
