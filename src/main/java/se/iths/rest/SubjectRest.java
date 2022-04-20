package se.iths.rest;

import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.service.SubjectService;
import se.iths.util.JsonResponse;

import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.transaction.TransactionalException;
import javax.validation.ValidationException;
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
        try {
            subjectService.addSubject(subject);
        } catch (TransactionalException t) {
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT)
                    .entity(new JsonResponse(409, "Conflict", "A subject with that name already exists")).build());
        } catch (ValidationException v) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                    .entity(new JsonResponse(400, "Bad Request", "A subjects has to be at least 2 letters")).build());
        }
        return Response.ok(subject).build();
    }

    @Path("{id}")
    @GET
    public Response getSubjectById(@PathParam("id") Long id) {
        notFoundError(id);
        Subject foundSubject = subjectService.findSubjectById(id);
        return Response.ok(foundSubject).build();
    }

    @Path("")
    @GET
    public Response getAllSubjects() {
        if (subjectService.getAllSubjects().isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity(new JsonResponse(204, "No Content", "No subjects added yet")).build();
        }
        return Response.ok(subjectService.getAllSubjects()).build();
    }


    @Path("{id}")
    @PATCH
    public Response updateSubject(@PathParam("id") Long id, Subject subject) {
        notFoundError(id);
        Subject changeSubjectName;
        try {
            changeSubjectName = subjectService.updateSubject(id, subject.getTitle());
        } catch (TransactionalException t) {
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT).entity
                    (new JsonResponse(409, "Conflict", "Subject " + subject.getTitle() + " already exists")).build());
        }
        return Response.ok(changeSubjectName).build();
    }

    @Path("removestudent/{id}")
    @DELETE
    public Response removeStudentFromSubject(@PathParam("id")Long id, @QueryParam("email") String email) {
        notFoundError(id);
        try {
            Student student = subjectService.removeStudent(id, email);
        } catch (NoResultException n) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new JsonResponse(404, "Not Found", "There is no student with the email: " + email)).build());
        }
        return Response.ok().entity(new JsonResponse(200, "OK", "Student successuflly removed from subject with id: " + id)).build();
    }

    @Path("removeteacher/{id}")
    @DELETE
    public Response removeTeacherFromSubject(@PathParam("id")Long id) {
        notFoundError(id);
        subjectService.removeTeacher(id);
        return Response.ok().entity(new JsonResponse(200, "OK", "Teacher successfully removed from subject with id: " + id)).build();
    }


    @Path("{id}")
    @DELETE
    public Response removeSubject(@PathParam("id") Long id) {
        notFoundError(id);
        subjectService.removeSubject(id);
        return Response.ok().entity(new JsonResponse(200, "OK", "Subject with the id: " + id + " successuflly deleted")).build();
    }

    private void notFoundError(Long id) {
        if (subjectService.findSubjectById(id) == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity(new JsonResponse(404, "Not Found", "There is no subject with the id: " + id)).build());
        }
    }
}