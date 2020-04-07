package main.de.maxwell.qa.comment.domain;

import de.maxwell.qa.domain.comment.Comment;
import de.maxwell.qa.domain.comment.CommentNotFoundException;
import de.maxwell.qa.domain.comment.CommentRepository;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import main.de.maxwell.qa.DatabaseResource;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@QuarkusTest
@QuarkusTestResource(DatabaseResource.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommentRepositoryIT {

    @Inject
    CommentRepository commentRepository;

    @BeforeAll
    public void setUp() {
        commentRepository.createComment("1", "test1");
        commentRepository.createComment("1", "test3");
        commentRepository.createComment("1", "test3");
        commentRepository.createComment("1", "test4");
        commentRepository.createComment("1", "test5");
        commentRepository.createComment("1", "test6");
        commentRepository.createComment("1", "test7");
    }

    @Test
    public void testFindById() {
        Comment comment = commentRepository.findById(1L);

        assertThat(comment.getUserID()).isEqualTo("1");
        assertThat(comment.getDescription()).isEqualTo("test1");
    }

    @Test
    public void testFindByIdNotFound() {
        assertThatThrownBy(() -> commentRepository.findById(99L)).isInstanceOf(CommentNotFoundException.class)
                .hasMessageContaining("Could not find comment with id 99");
    }

    @Test
    public void testListAllPaginated() {
        List<Comment> comments = commentRepository.listAllPaginated(5, 0);

        assertThat(comments.size()).isEqualTo(5);
    }

    @Test
    public void testUpdateDescription() {
        Comment comment = commentRepository.updateDescription(2L, "new Description");

        assertThat(comment.getDescription()).isEqualTo("new Description");
    }

    @Test
    public void testUpdateDescriptionNotFound() {
        assertThatThrownBy(() -> commentRepository.updateDescription(99L, "new Description")).isInstanceOf(CommentNotFoundException.class)
                .hasMessageContaining("Could not find comment with id 99");
    }


    @Test
    public void testUpdateRatingPositive() {
        Long rating = commentRepository.updateRating(4L, 1);

        assertThat(rating).isEqualTo(1L);
    }

    @Test
    public void testUpdateRatingNegative() {
        Long rating = commentRepository.updateRating(5L, -1);

        assertThat(rating).isEqualTo(-1L);
    }

    @Test
    @Order(1)
    public void testDelete() {
        commentRepository.removeComment(7L);
        assertThatThrownBy(() -> commentRepository.findById(7L)).isInstanceOf(CommentNotFoundException.class)
                .hasMessageContaining("Could not find comment with id 7");
    }

    @Test
    public void testDeleteNotFound() {
        assertThatThrownBy(() -> commentRepository.removeComment(99L)).isInstanceOf(CommentNotFoundException.class)
                .hasMessageContaining("Could not find comment with id 99");
    }
}
