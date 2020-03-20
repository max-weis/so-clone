package main.de.maxwell.qa.question.domain;

import de.maxwell.qa.domain.question.QuestionNotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class QuestionNotFoundExcpetionTest {

    @Test
    public void testException() {
        QuestionNotFoundException exception = new QuestionNotFoundException(1L);

        assertThat(exception.getMessage()).isEqualTo("Could not find question with id 1");
    }
}
