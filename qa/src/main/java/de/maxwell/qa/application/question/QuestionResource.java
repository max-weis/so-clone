package de.maxwell.qa.application.question;

import de.maxwell.qa.domain.question.Question;
import de.maxwell.qa.domain.question.QuestionNotFoundException;
import de.maxwell.qa.domain.question.QuestionRepository;
import io.quarkus.security.identity.SecurityIdentity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("question")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class QuestionResource {

    private static final Logger LOG = LoggerFactory.getLogger(QuestionResource.class);

    @Inject
    SecurityIdentity identity;

    @Inject
    QuestionRepository repository;

    @GET
    @Path("/{id}")
    public Response getQuestion(@PathParam("id") final Long questionId) {
        try {
            Question question = repository.findById(questionId);

            return Response.ok()
                    .entity(question)
                    .build();
        } catch (QuestionNotFoundException q) {
            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        }

    }

    @POST
    public Response createQuestion(final BaseQuestionDTO baseQuestion) {
        try {
            LOG.info(identity.getAttributes()
                    .toString());

            Question question = repository.createQuestion(baseQuestion.getUserID(), baseQuestion.getTitle(), baseQuestion.getDescription());

            return Response.ok()
                    .entity(question)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
}
