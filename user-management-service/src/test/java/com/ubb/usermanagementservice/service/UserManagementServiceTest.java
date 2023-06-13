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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class UserManagementServiceTest {
    @Mock
    private LearnerUserRepository learnerUserRepositoryMock;

    @Mock
    private HttpRequestsHandler httpRequestsHandlerMock;

    @Mock
    private PasswordEncoder passwordEncoderMock;

    private static PasswordEncoder actualPasswordEncoder;
    private static LearnerUser existingLearnerUserWithEncodedPassword;
    private static LearnerUser existingLeanerUser;

    @InjectMocks
    private UserManagementService userManagementService;

    @BeforeAll
    public static void setUp() {
        actualPasswordEncoder = new BCryptPasswordEncoder();
        existingLeanerUser = new LearnerUser(1L, "email", "password", "username");
        existingLearnerUserWithEncodedPassword = new LearnerUser(1L, "email",
                actualPasswordEncoder.encode("password"), "username");
    }

    @Test
    public void givenCorrectCredentialsLoginIsSuccessful() throws LearnerUserNotFoundException, LearnerUserIncorrectPasswordException {
        //given
        LoginRequest request = new LoginRequest("email", "password");
        Mockito.when(learnerUserRepositoryMock.findByEmail(request.getEmail())).thenReturn(existingLearnerUserWithEncodedPassword);
        Mockito.when(httpRequestsHandlerMock.sendRequestToGetLearnerUserProgressLevel(1L)).thenReturn("BEGINNER");
        Mockito.when(passwordEncoderMock.matches(request.getPassword(), existingLearnerUserWithEncodedPassword.getPassword()))
                .thenReturn(actualPasswordEncoder.matches(request.getPassword(), existingLearnerUserWithEncodedPassword.getPassword()));

        //when
        UserRegisteredResponse response = userManagementService.loginLearnerUser(request);

        //then
        assertEquals(response.getLearnerUserId(), 1L);
        assertEquals(response.getUsername(), "username");
        assertEquals(response.getUserProgressLevel(), "BEGINNER");
    }

    @Test
    public void givenIncorrectEmailLoginThrowsException() {
        //given
        LoginRequest request = new LoginRequest("wrong-email", "password");
        Mockito.when(learnerUserRepositoryMock.findByEmail(request.getEmail())).thenReturn(null);

        //when
        //then
        LearnerUserNotFoundException exception = assertThrows(LearnerUserNotFoundException.class, () -> {
            userManagementService.loginLearnerUser(request);
        });
        assertEquals(exception.getMessage(), "User does not exist");
    }

    @Test
    public void givenIncorrectPasswordLoginThrowsException() {
        //given
        LoginRequest request = new LoginRequest("email", "incorrect-password");
        Mockito.when(learnerUserRepositoryMock.findByEmail(request.getEmail())).thenReturn(existingLearnerUserWithEncodedPassword);
        Mockito.when(passwordEncoderMock.matches(request.getPassword(), existingLearnerUserWithEncodedPassword.getPassword()))
                .thenReturn(actualPasswordEncoder.matches(request.getPassword(), existingLearnerUserWithEncodedPassword.getPassword()));

        //when
        //then
        LearnerUserIncorrectPasswordException exception = assertThrows(LearnerUserIncorrectPasswordException.class, () -> {
            userManagementService.loginLearnerUser(request);
        });
        assertEquals(exception.getMessage(), "Incorrect password");
    }

    @Test
    public void givenUniqueNewUserSignUpIsSuccessful() throws LearnerUserEmailTakenException, LearnerUserUsernameTakenException {
        //given
        SignUpRequest request = new SignUpRequest("other-email", "other-password", "other-username");
        Mockito.when(learnerUserRepositoryMock.findByEmail(request.getEmail())).thenReturn(null);
        Mockito.when(learnerUserRepositoryMock.findByUsername(request.getUsername())).thenReturn(null);
        Mockito.when(learnerUserRepositoryMock.save(any()))
                        .thenReturn(new LearnerUser(2L, request.getEmail(), request.getPassword(), request.getUsername()));
        Mockito.when(passwordEncoderMock.encode(request.getPassword()))
                .thenReturn(actualPasswordEncoder.encode(request.getPassword()));
        Mockito.when(httpRequestsHandlerMock.sendRequestForNewUserProgress(2L)).thenReturn("BEGINNER");

        //when
        UserRegisteredResponse response = userManagementService.signUpLearnerUser(request);

        //then
        assertEquals(response.getUsername(), "other-username");
        assertEquals(response.getUserProgressLevel(), "BEGINNER");
    }

    @Test
    public void givenUsedEmailSignUpThrowsException() {
        //given
        SignUpRequest request = new SignUpRequest("email", "other-password", "other-username");
        Mockito.when(learnerUserRepositoryMock.findByEmail(request.getEmail())).thenReturn(existingLearnerUserWithEncodedPassword);

        //when
        //then
        LearnerUserEmailTakenException exception = assertThrows(LearnerUserEmailTakenException.class, () -> {
            userManagementService.signUpLearnerUser(request);
        });
        assertEquals(exception.getMessage(), "This email is already in use");
    }

    @Test
    public void givenUserUsernameSignUpThrowsException() {
        //given
        SignUpRequest request = new SignUpRequest("other-email", "other-password", "username");
        Mockito.when(learnerUserRepositoryMock.findByEmail(request.getEmail())).thenReturn(null);
        Mockito.when(learnerUserRepositoryMock.findByUsername(request.getUsername())).thenReturn(existingLearnerUserWithEncodedPassword);

        //when
        //then
        LearnerUserUsernameTakenException exception = assertThrows(LearnerUserUsernameTakenException.class, () -> {
            userManagementService.signUpLearnerUser(request);
        });
        assertEquals(exception.getMessage(), "This username is taken");
    }
}