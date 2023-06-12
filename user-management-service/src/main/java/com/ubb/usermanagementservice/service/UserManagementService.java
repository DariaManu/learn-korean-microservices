package com.ubb.usermanagementservice.service;

import com.ubb.usermanagementservice.controller.request.LoginRequest;
import com.ubb.usermanagementservice.controller.request.SignUpRequest;
import com.ubb.usermanagementservice.controller.response.UserRegisteredResponse;
import com.ubb.usermanagementservice.model.LearnerUser;
import com.ubb.usermanagementservice.model.exception.LearnerUserEmailTakenException;
import com.ubb.usermanagementservice.model.exception.LearnerUserIncorrectPasswordException;
import com.ubb.usermanagementservice.model.exception.LearnerUserNotFoundException;
import com.ubb.usermanagementservice.model.exception.LearnerUserUsernameTakenException;
import com.ubb.usermanagementservice.repository.LearnerUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserManagementService {
    private final LearnerUserRepository learnerUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final HttpRequestsHandler httpRequestsHandler;

    public UserRegisteredResponse loginLearnerUser(final LoginRequest loginRequest) throws LearnerUserNotFoundException, LearnerUserIncorrectPasswordException {
        LearnerUser existingUser = learnerUserRepository.findByEmail(loginRequest.getEmail());
        if (existingUser == null) {
            throw new LearnerUserNotFoundException("User does not exist");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), existingUser.getPassword())) {
            throw new LearnerUserIncorrectPasswordException("Incorrect password");
        }

        final Long learnerUserId = existingUser.getLearnerUserId();
        final String username = existingUser.getUsername();
        final String userProgressLevel = httpRequestsHandler.sendRequestToGetLearnerUserProgressLevel(learnerUserId);
        return new UserRegisteredResponse(learnerUserId, username, userProgressLevel);
    }

    public UserRegisteredResponse signUpLearnerUser(final SignUpRequest signUpRequest) throws LearnerUserEmailTakenException, LearnerUserUsernameTakenException {
        LearnerUser userWithEmail = learnerUserRepository.findByEmail(signUpRequest.getEmail());
        if (userWithEmail != null) {
            throw new LearnerUserEmailTakenException("This email is already in use");
        }

        LearnerUser userWithUsername = learnerUserRepository.findByUsername(signUpRequest.getUsername());
        if (userWithUsername != null) {
            throw new LearnerUserUsernameTakenException("This username is taken");
        }

        LearnerUser newUser = new LearnerUser(signUpRequest.getEmail(), passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getUsername());
        final Long learnerUserId = learnerUserRepository.save(newUser).getLearnerUserId();
        final String username = newUser.getUsername();
        final String userProgressLevel = httpRequestsHandler.sendRequestForNewUserProgress(learnerUserId);
        return new UserRegisteredResponse(learnerUserId, username, userProgressLevel);
    }
}
