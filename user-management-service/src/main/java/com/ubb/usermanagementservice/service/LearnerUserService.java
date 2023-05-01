package com.ubb.usermanagementservice.service;

import com.ubb.usermanagementservice.controller.request.LoginRequest;
import com.ubb.usermanagementservice.controller.request.RegisterRequest;
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
public class LearnerUserService {
    private final LearnerUserRepository learnerUserRepository;

    private final PasswordEncoder passwordEncoder;

    public Long loginLearnerUser(final LoginRequest loginRequest) throws LearnerUserNotFoundException, LearnerUserIncorrectPasswordException {
        LearnerUser existingUser = learnerUserRepository.findByEmail(loginRequest.getEmail());
        if (existingUser == null) {
            throw new LearnerUserNotFoundException("User does not exist");
        }

        if (!passwordEncoder.matches(loginRequest.getPassword(), existingUser.getPassword())) {
            throw new LearnerUserIncorrectPasswordException("Incorrect password");
        }

        return existingUser.getLearnerUserId();
    }

    public Long registerLearnerUser(final RegisterRequest registerRequest) throws LearnerUserEmailTakenException, LearnerUserUsernameTakenException {
        LearnerUser userWithEmail = learnerUserRepository.findByEmail(registerRequest.getEmail());
        if (userWithEmail != null) {
            throw new LearnerUserEmailTakenException("This email is already in use");
        }

        LearnerUser userWithUsername = learnerUserRepository.findByUsername(registerRequest.getUsername());
        if (userWithUsername != null) {
            throw new LearnerUserUsernameTakenException("This username is taken");
        }

        LearnerUser newUser = new LearnerUser(registerRequest.getEmail(), passwordEncoder.encode(registerRequest.getPassword()), registerRequest.getUsername());
        return learnerUserRepository.save(newUser).getLearnerUserId();
    }
}
