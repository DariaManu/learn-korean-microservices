package com.ubb.learningprogressservice.service;

import com.ubb.learningprogressservice.model.Question;
import com.ubb.learningprogressservice.model.QuizAttempt;
import com.ubb.learningprogressservice.repository.LearningModuleRepository;
import com.ubb.learningprogressservice.repository.QuestionRepository;
import com.ubb.learningprogressservice.repository.QuizAttemptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LearningProgressService {
    private final LearningModuleRepository learningModuleRepository;
    private final QuestionRepository questionRepository;
    private final QuizAttemptRepository quizAttemptRepository;

    public List<Question> getRandomQuestionsForLearningModule(final String learningModuleName) {
        System.out.println(learningModuleRepository.findByName(learningModuleName));
        return learningModuleRepository.findByName(learningModuleName).getQuestions();
    }

    public void submitUserAnswersForQuiz() {

    }

    public List<QuizAttempt> getQuizAttemptHistoryForUserAndLearningModule(final Long userId, final String learningModuleName) {
        return null;
    }
}
