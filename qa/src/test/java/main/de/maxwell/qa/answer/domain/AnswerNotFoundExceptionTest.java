package main.de.maxwell.qa.answer.domain;

import de.maxwell.qa.domain.answer.AnswerNotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AnswerNotFoundExceptionTest {
    @Test
    public void testException() {
        AnswerNotFoundException exception = new AnswerNotFoundException(1L);

        assertThat(exception.getMessage()).isEqualTo("Could not find answer with id 1");
    }
}
