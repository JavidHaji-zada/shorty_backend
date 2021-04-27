package com.shorty.app.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shorty.app.entity.Redirect;
import com.shorty.app.entity.Session;
import com.shorty.app.exception.BadRequestException;
import com.shorty.app.request.RedirectCreationRequest;
import com.shorty.app.request.RedirectDeletionRequest;
import com.shorty.app.service.RedirectService;
import com.shorty.app.service.SessionService;
import com.shorty.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

@RestController
public class RedirectController {

    private RedirectService redirectService;
    private SessionService sessionService;
    private UserService userService;

    @Autowired
    public RedirectController(RedirectService redirectService, SessionService sessionService, UserService userService) {
        this.redirectService = redirectService;
        this.sessionService = sessionService;
        this.userService = userService;
    }

    @GetMapping("/{alias}")
    public ResponseEntity<?> handleRedirect(@PathVariable String alias) {
        Redirect redirect = redirectService.getRedirect(alias);
        URI uri = null;
        try {
            uri = new URI(redirect.getUrl());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        redirect.setNumberOfClicks(redirect.getNumberOfClicks() + 1);
        redirectService.updateRedirect(redirect);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(uri);
        return new ResponseEntity<>(httpHeaders, HttpStatus.MOVED_PERMANENTLY);
    }

    @DeleteMapping("/redirects/{redirectID}")
    public ResponseEntity<?> deleteRedirect(@Valid @RequestBody RedirectDeletionRequest redirectDeletionRequest,
            @RequestHeader("sessionID") String sessionID) {
        Session session = sessionService.findBySessionID(sessionID)
                .orElseThrow(() -> new BadRequestException("Incorrect session id"));
        redirectService.deleteRedirect(redirectDeletionRequest);
        userService.deleteRedirectFromUser(redirectDeletionRequest, session.getUser().getEmail());
        ;
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/redirects")
    public ResponseEntity<?> createRedirect(@Valid @RequestBody RedirectCreationRequest redirectCreationRequest,
            @RequestHeader("sessionID") String sessionID) {
        if (!redirectCreationRequest.getAlias().trim().isEmpty() && sessionID.trim().isEmpty()) {
            throw new BadRequestException("Anonymous user cannot create custom url");
        }
        Redirect redirect;
        if (sessionID != null && !sessionID.trim().isEmpty()) {
            // add redirect to user redirects
            Session session = sessionService.findBySessionID(sessionID)
                    .orElseThrow(() -> new BadRequestException("Incorrect session id"));
            redirect = redirectService.createRedirect(redirectCreationRequest, sessionID).orElseThrow();
            userService.addRedirectToUser(redirect, session.getUser().getEmail());
        } else {
            System.out.println("Session is empty");
            redirect = redirectService.createRedirect(redirectCreationRequest, sessionID).orElseThrow();
        }
        ObjectNode response = new ObjectMapper().createObjectNode();
        response.put("url", redirect.getUrl());
        response.put("id", redirect.getId());
        response.put("alias", redirect.getAlias());
        response.put("numberOfClicks", redirect.getNumberOfClicks());
        return ResponseEntity.ok(response);
    }
}
