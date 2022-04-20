package se.iths.rest;

import se.iths.entity.Teacher;
import se.iths.service.TeacherService;
import se.iths.util.JsonResponse;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.TransactionalException;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("teachers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TeacherRest {

    TeacherService teacherService;

    @Inject
    public TeacherRest(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Path("")
    @POST
    public Response addTeacher(Teacher newTeacher) {
        try {
            teacherService.addTeacher(newTeacher);
        } catch (ValidationException v) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity(new JsonResponse(400, "Bad Request", "You need to enter a first and a last name with at least 2 letters")).build());
        }
        return Response.ok().entity(new JsonResponse(200, "OK", "Teacher successfully added")).build();
    }

    @Path("")
    @GET
    public Response getAllTeachers() {
        if (teacherService.getAllTeachers().isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity(new JsonResponse(204, "No Content", "No teachers added yet")).build();
        }
        List<Teacher> teachers = teacherService.getAllTeachers();
        return Response.ok(teachers).build();
    }

    @Path("{id}")
    @GET
    public Response getTeacherById(@PathParam("id") Long id) {
        notFoundError(id);
        Teacher teacher = teacherService.findTeacherById(id);
        return Response.ok(teacher).build();
    }

    @Path("")
    @PUT
    public Response updateTeacher(Teacher teacher) {
        notFoundError(teacher.getId());
        try {
            teacherService.updateTeacher(teacher);
        } catch (TransactionalException t) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity
                    (new JsonResponse(400, "Bad Request", "You need to enter a first name and last name with minimum 2 letters")).build());
        }
        return Response.ok(teacher).build();
    }

    @Path("addsubjects/{id}")
    @PATCH
    public Response addSubject(@PathParam("id") Long id, @QueryParam("title") String title) {
        notFoundError(id);
        Teacher teacher = teacherService.findTeacherById(id);
        try {
            teacher = teacherService.addSubject(id, title);
        } catch (NoResultException n) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity
                    (new JsonResponse(404, "Not Found", "There is no subject with the title " + title)).build());
        }
        return Response.ok().entity(new JsonResponse(200, "OK", "Added " + teacher.getFirstName() + " " + teacher.getLastName() + " to " + title)).build();
    }

    @Path("{id}")
    @DELETE
    public Response removeTeacher(@PathParam("id") Long id) {
        String removedStudentMessage = "Removed teacher with id: " + id;
        notFoundError(id);
        teacherService.removeTeacher(id);
        return Response.ok(removedStudentMessage).type(MediaType.TEXT_PLAIN_TYPE).build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    private void notFoundError(Long id) {
        if (teacherService.findTeacherById(id) == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new JsonResponse(404, "Not Found", "There is no teacher with the id: " + id)).build());
        }
    }
}