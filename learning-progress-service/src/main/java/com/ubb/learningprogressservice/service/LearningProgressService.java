package com.ubb.learningprogressservice.service;

import com.ubb.learningprogressservice.controller.response.SubmitQuizAttemptResponse;
import com.ubb.learningprogressservice.messaging.EventDispatcher;
import com.ubb.learningprogressservice.messaging.event.UserProgressLevelChangedEvent;
import com.ubb.learningprogressservice.model.*;
import com.ubb.learningprogressservice.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class LearningProgressService {
    private final LearningModuleRepository learningModuleRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final UserAnswerRepository userAnswerRepository;
    private final UserProgressRepository userProgressRepository;
    private final Random random = new Random();
    private final EventDispatcher eventDispatcher;

    public String addUserProgressForNewLearnerUser(final Long learnerUserId) {
        final UserProgress userProgress = new UserProgress(learnerUserId, ProgressLevel.BEGINNER);
        userProgressRepository.save(userProgress);
        return ProgressLevel.BEGINNER.toString();
    }

    public String getUserProgressLevel(final Long learnerUserId) {
        final UserProgress userProgress = userProgressRepository.findByUserId(learnerUserId);
        return userProgress.getLevel().toString();
    }

    public List<String> getLearningModuleNames() {
        return StreamSupport.stream(learningModuleRepository.findAll().spliterator(), false)
                .map(LearningModule::getName).toList();
    }

    public boolean checkAccessToLearningModule(final String learningModuleName, final Long learnerUserId) {
        final LearningModule learningModule = learningModuleRepository.findByName(learningModuleName);
        final ProgressLevel userProgressLevel = userProgressRepository.findByUserId(learnerUserId).getLevel();
        return userProgressLevel.isHigherThan(learningModule.getRequiredProgressLevel());
    }

    public List<Question> getRandomQuestionsForLearningModule(final String learningModuleName) {
        final List<Question> allQuestions = learningModuleRepository.findByName(learningModuleName).getQuestions();
        final int numberOfRandomQuestions = allQuestions.size() / 2 + 1;
        final List<Question> pickedQuestions = new ArrayList<>();
        for (int i = 0; i < numberOfRandomQuestions; i++) {
            final int randomIndex = random.nextInt(allQuestions.size());
            final Question pickedQuestion = allQuestions.get(randomIndex);
            pickedQuestions.add(pickedQuestion);
            allQuestions.remove(pickedQuestion);
        }
        return pickedQuestions;
    }

    public SubmitQuizAttemptResponse submitUserAnswersForQuiz(final Long userId, final String learningModuleName, final List<UserAnswer> userAnswers) {
        int numberOfCorrectAnswers = 0;
        for (UserAnswer userAnswer: userAnswers) {
            if (userAnswer.getGivenAnswerIndex() == userAnswer.getQuestion().getCorrectAnswerIndex()) {
                userAnswer.setCorrect(true);
                numberOfCorrectAnswers++;
            } else {
                userAnswer.setCorrect(false);
            }
            userAnswerRepository.save(userAnswer);
        }
        final int score = (numberOfCorrectAnswers * 100 )/ userAnswers.size();
        boolean quizPassed = score >= 50;

        final LearningModule learningModule = learningModuleRepository.findByName(learningModuleName);

        QuizAttempt quizAttempt = new QuizAttempt(userId, learningModule, userAnswers);
        quizAttempt.setScore(score);
        quizAttempt.setQuizPassed(quizPassed);

        quizAttemptRepository.save(quizAttempt);

        if(quizPassed) {
            checkIfUserProgressLevelShouldChange(userId, learningModuleName);
        }

        return new SubmitQuizAttemptResponse(quizPassed, score);
    }

    private void checkIfUserProgressLevelShouldChange(final Long userId, final String learningModuleName) {
        final UserProgress userProgress = userProgressRepository.findByUserId(userId);
        final LearningModule learningModule = learningModuleRepository.findByName(learningModuleName);
        if (userProgress.getLevel().isEqual(learningModule.getRequiredProgressLevel())) {
            //BUG HERE -> one more filter condition should be added -> the quiz attempts should belong to the current user
            final List<String> learningModuleNamesForPassedQuizAttempts = StreamSupport.stream(quizAttemptRepository.findAll().spliterator(), false)
                    .filter(quizAttempt -> quizAttempt.getUserId().equals(userId))
                    .filter(QuizAttempt::isQuizPassed)
                    .filter(quizAttempt -> quizAttempt.getLearningModule().getRequiredProgressLevel().isEqual(userProgress.getLevel()))
                    .map(quizAttempt -> quizAttempt.getLearningModule().getName()).toList();

            final List<String> learningModulesForCurrentProgressLevel = learningModuleRepository.findAllByRequiredProgressLevel(userProgress.getLevel())
                    .stream().map(LearningModule::getName).toList();

            if (learningModuleNamesForPassedQuizAttempts.containsAll(learningModulesForCurrentProgressLevel)) {
                if (StreamSupport.stream(learningModuleRepository.findAll().spliterator(), false)
                        .anyMatch(learningModule1 -> learningModule1.getRequiredProgressLevel().isHigherThan(userProgress.getLevel()))) {
                    //change the user progress level and send event
                    final UserProgress newUserProgress = new UserProgress(userId, userProgress.getLevel().getNextProgressLevel());
                    userProgressRepository.save(newUserProgress);
                    System.out.println("User progress level changed" + newUserProgress);
                    eventDispatcher.send(new UserProgressLevelChangedEvent(userId, newUserProgress.getLevel().toString()));
                }
            }
        }
    }

    public List<QuizAttempt> getQuizAttemptHistoryForUserAndLearningModule(final Long userId, final String learningModuleName) {
        final LearningModule learningModule = learningModuleRepository.findByName(learningModuleName);
        return quizAttemptRepository.findByLearningModuleAndUserId(learningModule, userId);
    }
}
