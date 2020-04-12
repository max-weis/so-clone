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

package de.maxwell.qa.application.profile;

import de.maxwell.qa.domain.profile.Profile;
import de.maxwell.qa.domain.profile.ProfileNotFoundException;
import de.maxwell.qa.domain.profile.ProfileService;
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

@Path("profile")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfileResource {
    private static final Logger LOG = LoggerFactory.getLogger(ProfileResource.class);

    @Inject
    ProfileService service;

    @Inject
    JsonWebToken jwt;

    @GET
    @Path("/{id}")
    public Response findProfile(@PathParam("id") final Long id) {
        try {
            notNull(id, "id cannot be null");

            LOG.info("Find profile with id: {}", id);

            Profile profile = this.service.findProfile(id);

            return Response.ok()
                    .entity(profile)
                    .build();
        } catch (ProfileNotFoundException p) {
            LOG.info("Could not find profile with id: {}", id);

            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Wrong user input");

            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @GET
    public Response listProfile(@QueryParam("limit") final Integer limit, @QueryParam("offset") final Integer offset) {
        try {
            notNull(limit, "limit cannot be null");
            notNull(offset, "offset cannot be null");

            LOG.info("Find profiles");

            List<Profile> profiles = this.service.listProfiles(limit, offset);

            return Response.ok()
                    .entity(profiles)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Wrong user input");

            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @POST
    public Response createProfile(final ProfileNewDTO newProfile) {
        try {
            notNull(newProfile, "new profile cannot be null");

            boolean check = checkJWT(jwt, newProfile.getUserID());
            if (!check) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .build();
            }

            Profile profile = this.service.createProfile(newProfile.getUserID(), newProfile.getFirstName(), newProfile.getLastName());

            return Response
                    .status(Response.Status.CREATED)
                    .entity(profile)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Wrong user input");

            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @PUT
    public Response updateFirstName(final ProfileUpdateFirstNameDTO updateFirstName) {
        try {
            notNull(updateFirstName, "updateFirstName cannot be null");

            boolean check = checkJWT(jwt, updateFirstName.getUserID());
            if (!check) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .build();
            }

            Profile profile = this.service.updateFirstName(updateFirstName.getId(), updateFirstName.getFirstName());

            return Response.ok()
                    .entity(profile)
                    .build();
        } catch (ProfileNotFoundException p) {
            LOG.info("Could not find profile with id: {}", updateFirstName.getId());

            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Wrong user input");

            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @PUT
    public Response updateLastName(final ProfileUpdateLastNameDTO updateLastName) {
        try {
            notNull(updateLastName, "updateLastName cannot be null");

            boolean check = checkJWT(jwt, updateLastName.getUserID());
            if (!check) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .build();
            }

            Profile profile = this.service.updateLastName(updateLastName.getId(), updateLastName.getLastName());

            return Response.ok()
                    .entity(profile)
                    .build();
        } catch (ProfileNotFoundException p) {
            LOG.info("Could not find profile with id: {}", updateLastName.getId());

            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Wrong user input");

            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @PUT
    public Response updateDescription(final ProfileUpdateDescriptionDTO updateDescription) {
        try {
            notNull(updateDescription, "updateDescription cannot be null");

            boolean check = checkJWT(jwt, updateDescription.getUserID());
            if (!check) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .build();
            }

            Profile profile = this.service.updateDescription(updateDescription.getId(), updateDescription.getDescription());

            return Response.ok()
                    .entity(profile)
                    .build();
        } catch (ProfileNotFoundException p) {
            LOG.info("Could not find profile with id: {}", updateDescription.getId());

            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Wrong user input");

            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @PUT
    public Response updateReputation(final ProfileUpdateReputationDTO updateReputation) {
        try {
            notNull(updateReputation, "updateReputation cannot be null");

            boolean check = checkJWT(jwt, updateReputation.getUserID());
            if (!check) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .build();
            }

            Long reputation = this.service.updateReputation(updateReputation.getId(), updateReputation.getReputation());

            return Response.ok()
                    .entity(reputation)
                    .build();
        } catch (ProfileNotFoundException p) {
            LOG.info("Could not find profile with id: {}", updateReputation.getId());

            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Wrong user input");

            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response removeProfile(@PathParam("id") final Long id) {
        try {
            notNull(id, "id cannot be null");

            Profile profile = this.service.findProfile(id);

            boolean check = checkJWT(jwt, profile.getUserID());
            if (!check) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .build();
            }

            this.service.removeProfile(id);

            return Response.ok()
                    .build();
        } catch (ProfileNotFoundException p) {
            LOG.info("Could not find profile with id: {}", id);

            return Response.status(Response.Status.NOT_FOUND)
                    .build();
        } catch (NullPointerException n) {
            LOG.info("Wrong user input");

            return Response.status(Response.Status.BAD_REQUEST)
                    .build();
        }
    }
}
