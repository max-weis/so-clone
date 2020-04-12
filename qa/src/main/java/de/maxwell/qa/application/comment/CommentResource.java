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

package de.maxwell.qa.application.comment;

import de.maxwell.qa.domain.comment.Comment;
import de.maxwell.qa.domain.comment.CommentNotFoundException;
import de.maxwell.qa.domain.comment.CommentService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
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

import static de.maxwell.qa.infrastructure.helper.JWTCheck.checkJWT;
import static org.apache.commons.lang3.Validate.notNull;

@Path("comment")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {
    private static final Logger LOG = LoggerFactory.getLogger(CommentResource.class);

    @Inject
    CommentService service;

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/{id}")
    public Response getComment(@PathParam("id") final Long id) {
        try {
            notNull(id, "id cannot be null");

            Comment comment = this.service.findComment(id);

            LOG.info("Found comment with id: {}", comment.getId());

            return Response.ok()
                    .entity(comment)
                    .build();
        } catch (CommentNotFoundException c) {
            LOG.info("Comment not found");
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException c) {
            LOG.info("Wrong user input");
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @GET
    public Response listCommentsByQuestionID(@QueryParam("questionID") final Long questionID, @QueryParam("limit") final Integer limit, @QueryParam("offset") final Integer offset) {
        try {
            notNull(questionID, "questionID cannot be null");
            notNull(limit, "limit cannot be null");
            notNull(offset, "offset cannot be null");

            List<Comment> comments = this.service.listCommentsPaginatedByQuestionID(questionID, limit, offset);

            return Response.ok()
                    .entity(comments)
                    .build();
        } catch (NullPointerException c) {
            LOG.info("Wrong user input");
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @GET
    public Response listCommentsByAnswerID(@QueryParam("answerID") final Long answerID, @QueryParam("limit") final Integer limit, @QueryParam("offset") final Integer offset) {
        try {
            notNull(answerID, "questionID cannot be null");
            notNull(limit, "limit cannot be null");
            notNull(offset, "offset cannot be null");

            List<Comment> comments = this.service.listCommentsPaginatedByAnswerID(answerID, limit, offset);

            return Response.ok()
                    .entity(comments)
                    .build();
        } catch (NullPointerException c) {
            LOG.info("Wrong user input");
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @POST
    public Response createComment(final CommentNewDTO newComment) {
        try {
            notNull(newComment, "new comment cannot be null");

            boolean check = checkJWT(jwt, newComment.getUserID());
            if (!check) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .build();
            }

            Comment comment = this.service.createComment(newComment.getUserID(), newComment.getQuestionID(), newComment.getAnswerID(), newComment.getDescription());

            return Response.ok()
                    .entity(comment)
                    .build();
        } catch (CommentNotFoundException c) {
            LOG.info("Comment not found");
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException c) {
            LOG.info("Wrong user input");
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @PUT
    public Response updateDescription(final CommentUpdateDescriptionDTO newDescription) {
        try {
            notNull(newDescription, "new description cannot be null");

            boolean check = checkJWT(jwt, newDescription.getUserID());
            if (!check) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .build();
            }

            this.service.updateDescription(newDescription.getId(), newDescription.getDescription());

            return Response.ok()
                    .build();
        } catch (CommentNotFoundException c) {
            LOG.info("Comment not found");
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException c) {
            LOG.info("Wrong user input");
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response incrementRating(@PathParam("id") final Long id) {
        try {
            notNull(id, "id cannot be null");

            Comment comment = this.service.findComment(id);

            boolean check = checkJWT(jwt, comment.getUserID());
            if (!check) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .build();
            }

            this.service.removeComment(id);

            return Response.ok()
                    .build();
        } catch (CommentNotFoundException c) {
            LOG.info("Comment not found");
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException c) {
            LOG.info("Wrong user input");
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response decrementRating(@PathParam("id") final Long id) {
        try {
            notNull(id, "id cannot be null");
            return Response.ok()
                    .build();
        } catch (CommentNotFoundException c) {
            LOG.info("Comment not found");
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException c) {
            LOG.info("Wrong user input");
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removeComment(@PathParam("id") final Long id) {
        try {
            notNull(id, "id cannot be null");

            Comment comment = this.service.findComment(id);
            boolean check = checkJWT(jwt, comment.getUserID());
            if (!check) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .build();
            }

            return Response.ok()
                    .build();
        } catch (CommentNotFoundException c) {
            LOG.info("Comment not found");
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException c) {
            LOG.info("Wrong user input");
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
}
