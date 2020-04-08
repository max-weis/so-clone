package de.maxwell.qa.domain.question;

import de.maxwell.qa.domain.answer.AnswerRepository;
import de.maxwell.qa.infrastructure.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

@Service
public class QuestionService {

    private static final Logger LOG = LoggerFactory.getLogger(QuestionService.class);

    @Inject
    QuestionRepository questionRepository;

    @Inject
    AnswerRepository answerRepository;

    public Question findQuestion(final Long id) throws QuestionNotFoundException, NullPointerException {
        notNull(id, "id cannot be null");

        LOG.info("Find Question with ID: {}", id);

        return questionRepository.findById(id);
    }

    public Question createQuestion(final String userID, final String title, final String description) throws QuestionNotFoundException, NullPointerException {
        notNull(userID, "userID cannot be null");
        notNull(title, "title cannot be null");
        notNull(description, "description cannot be null");

        notEmpty(userID, "userID cannot be empty");
        notEmpty(title, "title cannot be empty");
        notEmpty(description, "description cannot be empty");

        LOG.info("Create new Question");

        return questionRepository.createQuestion(userID, title, description);
    }

    public Question updateTitle() {
        return null;
    }
}
