package se.iths.rest;

import se.iths.entity.Teacher;
import se.iths.service.TeacherService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
    public Response createTeacher(Teacher newTeacher) {
        teacherService.addTeacher(newTeacher);
        return Response.ok(newTeacher).build();
    }

    @Path("addsubjects/{id}")
    @PATCH
    public Response addSubject(@PathParam("id")Long id, @QueryParam("title")String title) {
        Teacher addSubject;
        addSubject = teacherService.addSubject(id, title);

        return Response.ok(addSubject).build();
    }


}
