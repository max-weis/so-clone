/*
 * MIT License
 *
 * Copyright (c) 2020 Max Weis
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
        commentRepository.createComment("1", 1L, null, "test1");
        commentRepository.createComment("1", 1L, null, "test3");
        commentRepository.createComment("1", 1L, null, "test3");
        commentRepository.createComment("1", 1L, null, "test4");
        commentRepository.createComment("1", 1L, null, "test5");
        commentRepository.createComment("1", 1L, null, "test6");
        commentRepository.createComment("1", 1L, null, "test7");
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
    public void testListAllPaginatedByQuestionID() {
        List<Comment> comments = commentRepository.listAllPaginatedByQuestionID(1L, 5, 0);

        assertThat(comments.size()).isEqualTo(5);
    }

    @Test
    public void testListAllPaginatedByAnswerID() {
        List<Comment> comments = commentRepository.listAllPaginatedByAnswerID(1L, 5, 0);

        assertThat(comments.size()).isEqualTo(0);
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
