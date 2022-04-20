package se.iths.rest;

import se.iths.entity.Student;
import se.iths.service.StudentService;
import se.iths.util.JsonResponse;

import javax.inject.Inject;
import javax.transaction.TransactionalException;
import javax.validation.ValidationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class StudentRest {

    StudentService studentService;

    @Inject
    public StudentRest(StudentService studentService) {
        this.studentService = studentService;
    }


    @Path("")
    @POST
    public Response addStudent(Student newStudent) {

        try {
            studentService.addStudent(newStudent);
        } catch (TransactionalException t) {
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT).entity(new JsonResponse(409, "Conflict", "Email already exists")).build());

        } catch (ValidationException v) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(new JsonResponse(400, "Bad Request", "You need to enter First name, last name and email")).build());
        }
        return Response.ok().entity(new JsonResponse(200, "OK", "Student successfully added")).build();
    }


    @Path("")
    @GET
    public Response getAllStudents() {
        if (studentService.getAllStudents().isEmpty()) {
            return Response.status(Response.Status.NO_CONTENT).entity(new JsonResponse(204, "No Content", "No students added yet")).build();
        }
        return Response.ok(studentService.getAllStudents()).build();
    }

    @Path("{id}")
    @GET
    public Response findStudentById(@PathParam("id") Long id) {
        notFoundError(id);
        return Response.ok(studentService.findStudentById(id)).build();
    }


    @Path("find")
    @GET
    public Response findStudentByLastName(@QueryParam("lastname") String lastName) {
        if (studentService.findStudentByLastName(lastName).isEmpty()) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity(new JsonResponse(404, "Not found", "Did not find a student with the last name " + lastName)).build());
        }
        return Response.ok(studentService.findStudentByLastName(lastName)).build();
    }

    @Path("")
    @PUT
    public Response updateStudent(Student student) {
        notFoundError(student.getId());
        try {
            studentService.updateStudent(student);
        } catch (TransactionalException t) {
            throw new WebApplicationException(Response.status(Response.Status.CONFLICT).entity(new JsonResponse(409, "Conflict", "Email already exists")).build());

        } catch (ValidationException v) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(new JsonResponse(400, "Bad Request", "You need to enter First name, last name and email")).build());
        }
        return Response.ok(student).build();
    }

    @Path("{id}")
    @PATCH
    public Response updatePhoneNumber(@PathParam("id") Long id, Student student) {
        notFoundError(id);
        Student updateStudentPhoneNumber;
        if (onlyDigits(student.getPhoneNumber()) && student.getPhoneNumber().length() >= 5) {
            updateStudentPhoneNumber = studentService.updatePhoneNumber(id, student.getPhoneNumber());
        } else {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity(new JsonResponse(400, "Bad Request", "Phone number" + student.getPhoneNumber() + " is invalid")).build());
        }
        return Response.ok(updateStudentPhoneNumber).build();
    }


    @Path("{id}")
    @DELETE
    public Response removeStudent(@PathParam("id") Long id) {
        notFoundError(id);
        studentService.removeStudent(id);
        return Response.ok().entity(new JsonResponse(200, "OK", "Successfully deleted student with id " + id)).build();
    }

    @Path("addsubjects/{id}")
    @PATCH
    public Response addSubject(@PathParam("id") Long id, @QueryParam("title") String title) {
        notFoundError(id);
        Student addSubject;
        addSubject = studentService.addSubject(id, title);
        return Response.ok(addSubject).build();
    }

    @Produces(MediaType.APPLICATION_JSON)
    private void notFoundError(Long id) {

        if (studentService.findStudentById(id) == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).entity(new JsonResponse(404, "Not Found", "There is no student with the id: " + id)).build());
        }
    }

    private static Boolean onlyDigits(String phoneNumber) {
        String onlyNumbersRegex = "[0-9]+";
        Pattern pattern = Pattern.compile(onlyNumbersRegex);

        if (phoneNumber == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }

}