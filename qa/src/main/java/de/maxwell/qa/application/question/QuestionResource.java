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

package de.maxwell.qa.application.question;

import de.maxwell.qa.domain.answer.AnswerNotFoundException;
import de.maxwell.qa.domain.question.Question;
import de.maxwell.qa.domain.question.QuestionNotFoundException;
import de.maxwell.qa.domain.question.QuestionService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("question")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuestionResource {

    private static final Logger LOG = LoggerFactory.getLogger(QuestionResource.class);

    @Inject
    MetricRegistry metricRegistry;

    @Inject
    QuestionService service;

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/{id}")
    @Counted(name = "get_question_total", description = "get one question counter")
    @Timed(name = "get_question_milliseconds", description = "Time to get one question", unit = MetricUnits.MILLISECONDS)
    public Response getQuestion(@PathParam("id") final Long questionId) {
        try {
            LOG.info("Find question with ID: {}", questionId);

            Question question = this.service.findQuestion(questionId);

            return Response.ok()
                    .entity(question)
                    .build();
        } catch (QuestionNotFoundException q) {
            LOG.info("Could not find question with ID: {}", questionId);
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Arguments have errors {}", n.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        } catch (Exception e) {
            metricRegistry.counter("get_question_500")
                    .inc();

            LOG.info("An error occured");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @POST
    @Counted(name = "create_question_total", description = "create one question counter")
    @Timed(name = "create_question_milliseconds", description = "Time to create one question", unit = MetricUnits.MILLISECONDS)
    public Response createQuestion(final QuestionNewDTO baseQuestion) {
        try {
            LOG.info("Create new question");

            String sub = jwt.getSubject();

            if (!sub.equals(baseQuestion.getUserID())) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .build();
            }
            Question question = this.service.createQuestion(baseQuestion.getUserID(), baseQuestion.getTitle(), baseQuestion.getDescription());

            LOG.info("New question with ID: {} created", question.getId());

            return Response
                    .status(Response.Status.CREATED)
                    .entity(question)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Arguments have errors {}", n.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        } catch (Exception e) {
            metricRegistry.counter("create_question_500")
                    .inc();

            LOG.info("An error occured");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GET
    @Counted(name = "list_questions_total", description = "list questions counter")
    @Timed(name = "list_questions_milliseconds", description = "Time to list questions", unit = MetricUnits.MILLISECONDS)
    public Response listQuestionsPaginated(@Size(min = 0, max = 50) @QueryParam("limit") final Integer limit, @Size(min = 0) @QueryParam("offset") final Integer offset) {
        try {
            List<Question> questions = this.service.findQuestions(limit, offset);
            LOG.info("Found {} questions", limit * offset);

            return Response.ok()
                    .entity(questions)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Argument was not correct");
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        } catch (Exception e) {
            metricRegistry.counter("list_questions_500")
                    .inc();

            LOG.info("An error occured");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PUT
    @Counted(name = "update_title_total", description = "update title counter")
    @Timed(name = "update_title_milliseconds", description = "Time to update title of a question", unit = MetricUnits.MILLISECONDS)
    public Response updateTitle(final QuestionUpdateTitleDTO newQuestion) {
        try {
            LOG.info("Update title of the question id {}", newQuestion.getId());
            Question question = this.service.findQuestion(newQuestion.getId());

            String sub = jwt.getSubject();

            if (!sub.equals(question.getUserID())) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .build();
            }

            Question updatedQuestion = this.service.updateTitle(newQuestion.getId(), newQuestion.getTitle());

            return Response.ok()
                    .entity(updatedQuestion)
                    .build();
        } catch (QuestionNotFoundException q) {
            LOG.info("Could not find question with ID: {}", newQuestion.getId());
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Arguments have errors {}", n.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        } catch (Exception e) {
            metricRegistry.counter("update_title_500")
                    .inc();

            LOG.info("An error occured");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PUT
    @Counted(name = "update_description_total", description = "update description counter")
    @Timed(name = "update_description_milliseconds", description = "Time to update description of a question", unit = MetricUnits.MILLISECONDS)
    public Response updateDescription(final QuestionUpdateDescriptionDTO newQuestion) {
        try {
            LOG.info("Update description of the question id {}", newQuestion.getId());
            Question question = this.service.findQuestion(newQuestion.getId());

            String sub = jwt.getSubject();

            if (!sub.equals(question.getUserID())) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .build();
            }

            Question updatedQuestion = this.service.updateDescription(newQuestion.getId(), newQuestion.getDescription());

            return Response.ok()
                    .entity(updatedQuestion)
                    .build();
        } catch (QuestionNotFoundException q) {
            LOG.info("Could not find question with ID: {}", newQuestion.getId());
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Arguments have errors {}", n.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        } catch (Exception e) {
            metricRegistry.counter("update_description_500")
                    .inc();

            LOG.info("An error occured");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PUT
    @Path("/{id}/view")
    @Counted(name = "increment_view_total", description = "increment view counter")
    @Timed(name = "increment_view_milliseconds", description = "Time to increment view of a question", unit = MetricUnits.MILLISECONDS)
    public Response incrementView(@PathParam("id") final Long questionId) {
        try {
            Long view = this.service.incrementView(questionId);

            LOG.info("Increment view of the question id {}", questionId);

            return Response.ok()
                    .entity(view)
                    .build();
        } catch (QuestionNotFoundException q) {
            LOG.info("Could not find question with ID: {}", questionId);
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Arguments have errors {}", n.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        } catch (Exception e) {
            metricRegistry.counter("increment_view_500")
                    .inc();

            LOG.info("An error occured");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @PUT
    @Path("/{id}/rating")
    @Counted(name = "upvote_rating_total", description = "upvote rating counter")
    @Timed(name = "upvote_rating_milliseconds", description = "Time to upvote the rating of a question", unit = MetricUnits.MILLISECONDS)
    public Response upvoteRating(@PathParam("id") final Long questionId) {
        try {
            Long view = this.service.upvoteRating(questionId);

            LOG.info("Increment rating of the question id {}", questionId);

            return Response.ok()
                    .entity(view)
                    .build();
        } catch (QuestionNotFoundException q) {
            LOG.info("Could not find question with ID: {}", questionId);
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Arguments have errors {}", n.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        } catch (Exception e) {
            metricRegistry.counter("upvote_rating_500")
                    .inc();

            LOG.info("An error occured");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @DELETE
    @Path("/{id}/rating")
    @Counted(name = "downvote_rating_total", description = "downvote rating counter")
    @Timed(name = "downvote_rating_milliseconds", description = "Time to downvote the rating of a question", unit = MetricUnits.MILLISECONDS)
    public Response downvoteRating(@PathParam("id") final Long questionId) {
        try {
            Long view = this.service.downvoteRating(questionId);

            LOG.info("Decrement rating of the question id {}", questionId);

            return Response.ok()
                    .entity(view)
                    .build();
        } catch (QuestionNotFoundException q) {
            LOG.info("Could not find question with ID: {}", questionId);
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Arguments have errors {}", n.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        } catch (Exception e) {
            metricRegistry.counter("downvote_rating_500")
                    .inc();

            LOG.info("An error occured");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GET
    @Path("/{id}/answer/{answerId}")
    @Counted(name = "set_correct_answer_total", description = "set correct answer counter")
    @Timed(name = "set_correct_answer_milliseconds", description = "Time to set correct answer of a question", unit = MetricUnits.MILLISECONDS)
    public Response setCorrectAnswer(@PathParam("id") final Long questionId, @PathParam("answerId") final Long answerId) {
        try {
            Question question = this.service.findQuestion(questionId);

            String sub = jwt.getSubject();

            if (!sub.equals(question.getUserID())) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .build();
            }

            LOG.info("Set correct answer of ID: {} for the question with ID: {}", questionId, answerId);

            Long correctAnswer = this.service.setCorrectAnswer(questionId, answerId);

            return Response.ok()
                    .entity(correctAnswer)
                    .build();
        } catch (QuestionNotFoundException q) {
            LOG.info("Could not find question with ID: {}", questionId);
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (AnswerNotFoundException q) {
            LOG.info("Could not find answer with ID: {}", answerId);
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Arguments have errors {}", n.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        } catch (Exception e) {
            metricRegistry.counter("set_correct_answer_500")
                    .inc();

            LOG.info("An error occured");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @GET
    @Path("/count")
    @Counted(name = "get_count_total", description = "get count counter")
    @Timed(name = "get_count_milliseconds", description = "Time to get count of a question", unit = MetricUnits.MILLISECONDS)
    public Response getCount(@QueryParam("id") final String userID) {
        try {

            LOG.info("Count number of question from user with ID: {}", userID);

            Long count = this.service.getCount(userID);

            LOG.info("user with ID: {} has {} questions", userID, count);

            return Response.ok()
                    .entity(count)
                    .build();
        } catch (QuestionNotFoundException q) {
            LOG.info("Could not find question");
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Arguments have errors {}", n.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        } catch (Exception e) {
            metricRegistry.counter("get_count_500")
                    .inc();

            LOG.info("An error occured");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Counted(name = "delete_question_total", description = "delete question counter")
    @Timed(name = "delete_question_milliseconds", description = "Time to delete a question", unit = MetricUnits.MILLISECONDS)
    public Response deleteQuestion(@PathParam("id") final Long questionId) {
        try {
            String sub = jwt.getSubject();

            Question question = this.service.findQuestion(questionId);

            if (!sub.equals(question.getUserID())) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .build();
            }

            LOG.info("Delete question with ID: {}", questionId);

            this.service.removeQuestion(questionId);

            return Response.ok()
                    .build();
        } catch (QuestionNotFoundException q) {
            LOG.info("Could not find question with ID: {}", questionId);
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Arguments have errors {}", n.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        } catch (Exception e) {
            metricRegistry.counter("delete_question_500")
                    .inc();

            LOG.info("An error occured");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }
}
