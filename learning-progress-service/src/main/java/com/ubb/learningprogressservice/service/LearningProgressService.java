package com.ubb.learningprogressservice.service;

import com.ubb.learningprogressservice.model.LearningModule;
import com.ubb.learningprogressservice.model.Question;
import com.ubb.learningprogressservice.model.QuizAttempt;
import com.ubb.learningprogressservice.model.UserAnswer;
import com.ubb.learningprogressservice.repository.LearningModuleRepository;
import com.ubb.learningprogressservice.repository.QuestionRepository;
import com.ubb.learningprogressservice.repository.QuizAttemptRepository;
import com.ubb.learningprogressservice.repository.UserAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class LearningProgressService {
    private final LearningModuleRepository learningModuleRepository;
    private final QuestionRepository questionRepository;
    private final QuizAttemptRepository quizAttemptRepository;
    private final UserAnswerRepository userAnswerRepository;
    private final Random random = new Random();

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

    public void submitUserAnswersForQuiz(final Long userId, final String learningModuleName, final List<UserAnswer> userAnswers) {
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
    }

    public List<QuizAttempt> getQuizAttemptHistoryForUserAndLearningModule(final Long userId, final String learningModuleName) {
        final LearningModule learningModule = learningModuleRepository.findByName(learningModuleName);
        return quizAttemptRepository.findByLearningModuleAndUserId(learningModule, userId);
    }
}
