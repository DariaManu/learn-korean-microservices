package com.ubb.learningprogressservice.service;

import com.ubb.learningprogressservice.controller.response.SubmitQuizAttemptResponse;
import com.ubb.learningprogressservice.messaging.EventDispatcher;
import com.ubb.learningprogressservice.model.*;
import com.ubb.learningprogressservice.repository.LearningModuleRepository;
import com.ubb.learningprogressservice.repository.QuizAttemptRepository;
import com.ubb.learningprogressservice.repository.UserAnswerRepository;
import com.ubb.learningprogressservice.repository.UserProgressRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class LearningProgressServiceTest {
    @Mock
    private UserProgressRepository userProgressRepositoryMock;

    @Mock
    private LearningModuleRepository learningModuleRepositoryMock;

    @Mock
    private UserAnswerRepository userAnswerRepositoryMock;

    @Mock
    private QuizAttemptRepository quizAttemptRepositoryMock;

    @Mock
    private EventDispatcher eventDispatcherMock;

    @InjectMocks
    private LearningProgressService learningProgressService;

    private static List<LearningModule> learningModules;
    private static List<UserProgress> userProgresses;

    @BeforeEach
    public void setUp() {
        learningModules = List.of(
                new LearningModule(
                        1L,
                        "Module1",
                        ProgressLevel.BEGINNER,
                        new ArrayList<>(Arrays.asList(
                                new Question(1L, "question1",
                                        List.of(new QuestionAnswer(1L, "answer1"),
                                                new QuestionAnswer(2L, "answer2")),
                                        0
                                ),
                                new Question(2L, "question2",
                                        List.of(
                                                new QuestionAnswer(3L, "answer3"),
                                                new QuestionAnswer(4L, "answer4")
                                        ),
                                        0
                                ),
                                new Question(3L, "question3",
                                        List.of(
                                                new QuestionAnswer(5L, "answer5"),
                                                new QuestionAnswer(6L, "answer6")
                                        ),
                                        0
                                )
                        ))
                ),
                new LearningModule(
                        2L,
                        "Module2",
                        ProgressLevel.BEGINNER,
                        new ArrayList<>(Arrays.asList(
                                new Question(4L, "question4",
                                        List.of(new QuestionAnswer(7L,"answer7"),
                                                new QuestionAnswer(8L, "answer8")),
                                        0
                                ),
                                new Question(5L, "question5",
                                        List.of(
                                                new QuestionAnswer(9L, "answer9"),
                                                new QuestionAnswer(10L, "answer10")
                                        ),
                                        0
                                ),
                                new Question(6L, "question6",
                                        List.of(
                                                new QuestionAnswer(11L, "answer11"),
                                                new QuestionAnswer(12L, "answer12")
                                        ),
                                        0
                                )
                        ))
                ),
                new LearningModule(
                        3L,
                        "Module3",
                        ProgressLevel.INTERMEDIATE,
                        new ArrayList<>(Arrays.asList(
                                new Question(7L, "question7",
                                        List.of(new QuestionAnswer(13L,"answer13"),
                                                new QuestionAnswer(14L, "answer14")),
                                        0
                                ),
                                new Question(8L, "question8",
                                        List.of(
                                                new QuestionAnswer(15L, "answer15"),
                                                new QuestionAnswer(16L, "answer16")
                                        ),
                                        0
                                ),
                                new Question(9L, "question9",
                                        List.of(
                                                new QuestionAnswer(17L, "answer17"),
                                                new QuestionAnswer(18L, "answer18")
                                        ),
                                        0
                                )
                        ))
                )
        );

        userProgresses = List.of(
                new UserProgress(1L, ProgressLevel.BEGINNER),
                new UserProgress(2L, ProgressLevel.BEGINNER),
                new UserProgress(3L, ProgressLevel.INTERMEDIATE)
        );
    }

    @Test
    public void givenProgressLevelAccessToLearningModuleIsGranted() {
        //given
        Mockito.when(learningModuleRepositoryMock.findByName("Module1")).thenReturn(learningModules.get(0));
        Mockito.when(userProgressRepositoryMock.findByUserId(1L)).thenReturn(userProgresses.get(0));
        Mockito.when(userProgressRepositoryMock.findByUserId(3L)).thenReturn(userProgresses.get(2));

        //when
        boolean accessOfFirstUser = learningProgressService.checkAccessToLearningModule("Module1", 1L);
        boolean accessOfSecondUser = learningProgressService.checkAccessToLearningModule("Module1", 3L);

        //then
        assertTrue(accessOfFirstUser);
        assertTrue(accessOfSecondUser);
    }

    @Test
    public void givenProgressLevelAccessToLearningModuleIsNotGranted() {
        //given
        Mockito.when(learningModuleRepositoryMock.findByName("Module3")).thenReturn(learningModules.get(2));
        Mockito.when(userProgressRepositoryMock.findByUserId(1L)).thenReturn(userProgresses.get(0));

        //when
        boolean accessOfUser = learningProgressService.checkAccessToLearningModule("Module3", 1L);

        //then
        assertFalse(accessOfUser);
    }

    @Test
    public void returnsCorrectNumberOfRandomlyChosenQuestionsForQuiz() {
        //given
        Mockito.when(learningModuleRepositoryMock.findByName("Module1")).thenReturn(learningModules.get(0));

        //when
        List<Question> questions = learningProgressService.getRandomQuestionsForLearningModule("Module1");

        //then
        assertEquals(questions.size(), 2);
    }

    @Test
    public void givenQuizAttemptWithAllQuestionsCorrectQuizIsPassed() {
        //given
        Mockito.when(userAnswerRepositoryMock.save(any())).thenReturn(null);
        Mockito.when(learningModuleRepositoryMock.findByName("Module1")).thenReturn(learningModules.get(0));
        Mockito.when(quizAttemptRepositoryMock.save(any())).thenReturn(null);
        Mockito.when(userProgressRepositoryMock.findByUserId(1L)).thenReturn(userProgresses.get(0));
        Mockito.when(quizAttemptRepositoryMock.findAll()).thenReturn(List.of());
        Mockito.when(learningModuleRepositoryMock.findAllByRequiredProgressLevel(ProgressLevel.BEGINNER)).thenReturn(learningModules.subList(0, 2));
        List<Question> questions = learningModules.get(0).getQuestions().subList(0, 2);
        List<UserAnswer> userAnswers = List.of(
                new UserAnswer(questions.get(0), 0),
                new UserAnswer(questions.get(1), 0)
        );

        //when
        final SubmitQuizAttemptResponse response = learningProgressService.submitUserAnswersForQuiz(1L, "Module1", userAnswers);

        //then
        assertTrue(response.isQuizPassed());
        assertEquals(response.getScore(), 100);
    }

    @Test
    public void givenQuizAttemptWithAllQuestionsIncorrectQuizIsFailed() {
        //given
        Mockito.when(userAnswerRepositoryMock.save(any())).thenReturn(null);
        Mockito.when(learningModuleRepositoryMock.findByName("Module1")).thenReturn(learningModules.get(0));
        Mockito.when(quizAttemptRepositoryMock.save(any())).thenReturn(null);
        List<Question> questions = learningModules.get(0).getQuestions().subList(0, 2);
        List<UserAnswer> userAnswers = List.of(
                new UserAnswer(questions.get(0), 1),
                new UserAnswer(questions.get(1), 1)
        );

        //when
        final SubmitQuizAttemptResponse response = learningProgressService.submitUserAnswersForQuiz(1L, "Module1", userAnswers);

        //then
        assertFalse(response.isQuizPassed());
        assertEquals(response.getScore(), 0);
    }

    @Test
    public void givenPassedQuizAttemptUserProgressLevelIsUpdated() {
        //given
        List<Question> module1Questions = learningModules.get(0).getQuestions();
        List<Question> module2Questions = learningModules.get(1).getQuestions();
        List<QuizAttempt> quizAttempts = List.of(
                new QuizAttempt(1L, 3L, learningModules.get(0), List.of(
                        new UserAnswer(module1Questions.get(0), 0),
                        new UserAnswer(module1Questions.get(1), 0)
                ), 100, true),
                new QuizAttempt(2L, 3L, learningModules.get(1), List.of(
                        new UserAnswer(module2Questions.get(0), 0),
                        new UserAnswer(module2Questions.get(1), 0)
                ), 100, true),
                new QuizAttempt(3L, 1L, learningModules.get(0), List.of(
                        new UserAnswer(module1Questions.get(0), 0),
                        new UserAnswer(module1Questions.get(1), 0)
                ), 100, true),
                new QuizAttempt(4L, 1L, learningModules.get(1), List.of(
                        new UserAnswer(module2Questions.get(0), 0),
                        new UserAnswer(module2Questions.get(1), 0)
                ), 100, true)
        );

        Mockito.when(userAnswerRepositoryMock.save(any())).thenReturn(null);
        Mockito.when(learningModuleRepositoryMock.findByName("Module2")).thenReturn(learningModules.get(1));
        Mockito.when(quizAttemptRepositoryMock.save(any())).thenReturn(null);
        Mockito.when(userProgressRepositoryMock.findByUserId(1L)).thenReturn(userProgresses.get(0));
        Mockito.when(quizAttemptRepositoryMock.findAll()).thenReturn(quizAttempts);
        Mockito.when(learningModuleRepositoryMock.findAllByRequiredProgressLevel(ProgressLevel.BEGINNER)).thenReturn(learningModules.subList(0, 2));
        Mockito.when(learningModuleRepositoryMock.findAll()).thenReturn(learningModules);
        Mockito.when(userProgressRepositoryMock.save(any())).thenReturn(null);
        Mockito.doNothing().when(eventDispatcherMock).send(any());

        List<UserAnswer> userAnswers = List.of(
                new UserAnswer(module2Questions.get(0), 0),
                new UserAnswer(module2Questions.get(1), 0)
        );

        //when

        final SubmitQuizAttemptResponse response = learningProgressService.submitUserAnswersForQuiz(1L, "Module2", userAnswers);

        //then
        Mockito.verify(eventDispatcherMock, Mockito.times(1)).send(any());
    }
}