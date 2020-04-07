package main.de.maxwell.qa.comment.domain;

import de.maxwell.qa.domain.comment.CommentNotFoundException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommentNotFoundExceptionTest {
    @Test
    public void testException() {
        CommentNotFoundException exception = new CommentNotFoundException(1L);

        assertThat(exception.getMessage()).isEqualTo("Could not find comment with id 1");
    }
}
