package se.iths.rest;

import se.iths.entity.Subject;
import se.iths.service.SubjectService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("subjects")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SubjectRest {

    SubjectService subjectService;

    @Inject
    public SubjectRest(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Path("")
    @POST
    public Response addSubject(Subject subject) {
       Subject subjectResult = subjectService.addSubject(subject);
        return Response.ok(subjectResult).build();
    }

    @Path("{id}")
    @GET
    public Response getSubjectById(@PathParam("id") Long id) {
        Subject foundSubject = subjectService.findSubjectById(id);
        return Response.ok(foundSubject).build();
    }

    @Path("")
    @GET
    public Response getAllSubjects() {
        return Response.ok(subjectService.getAllSubjects()).build();
    }
}
