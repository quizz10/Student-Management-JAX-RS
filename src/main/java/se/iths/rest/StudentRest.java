package se.iths.rest;

import se.iths.entity.Student;
import se.iths.service.StudentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("students")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class StudentRest {

    @Inject
    StudentService studentService;

    @Path("")
    @POST
    public Response createStudent(Student newStudent) {

        if(incompleteStudent(newStudent)) {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Student info is incomplete").type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        for (Student existingStudent : studentService.getAllStudents()) {
            if(newStudent.getEmail().equals(existingStudent.getEmail())) {
                throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
                        .entity("There is another student with the email " + newStudent.getEmail()).type(MediaType.TEXT_PLAIN_TYPE).build());
            }
        }

        studentService.addStudent(newStudent);
        return Response.ok(newStudent).build();
    }


    @Path("")
    @GET
    public Response showAllStudents() {
        String emptyList = "There are no students added yet";
        if(studentService.getAllStudents().isEmpty()) {
            return Response.ok(emptyList).type(MediaType.TEXT_PLAIN_TYPE).build();
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
        if(studentService.findStudentByLastName(lastName).isEmpty()) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("There is no student with the last name " + lastName).type(MediaType.TEXT_PLAIN_TYPE).build());
        }
        return Response.ok(studentService.findStudentByLastName(lastName)).build();
    }

    @Path("")
    @PUT
    public Response updateStudent(Student student) {

        notFoundError(student.getId());
        studentService.updateStudent(student);
        return Response.ok(student).build();
    }

    @Path("{id}")
    @PATCH
    public Response updatePhoneNumber(@PathParam("id")Long id, Student student) {
        notFoundError(id);
        Student updateStudentPhoneNumber;
        if(onlyDigits(student.getPhoneNumber()) && student.getPhoneNumber().length() >= 5) {
            updateStudentPhoneNumber = studentService.updatePhoneNumber(id, student.getPhoneNumber());
        } else {
            throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST).entity("Phone number is invalid!").type(MediaType.TEXT_PLAIN_TYPE).build());
        }

        return Response.ok(updateStudentPhoneNumber).build();
        }

    @Path("{id}")
    @DELETE
    public Response removeStudent(@PathParam("id") Long id) {
        String removedStudentMessage = "Removed student with id: " + id;

        notFoundError(id);
        studentService.removeStudent(id);
        return Response.ok(removedStudentMessage).type(MediaType.TEXT_PLAIN_TYPE).build();
    }

    private void notFoundError(Long id) {
        if(studentService.findStudentById(id) == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .entity("There is no student with the ID " + id).type(MediaType.TEXT_PLAIN_TYPE).build());
        }
    }

    private static Boolean onlyDigits(String phoneNumber) {
        String onlyNumbersRegex = "[0-9]+";
        Pattern pattern = Pattern.compile(onlyNumbersRegex);

        if(phoneNumber == null) {
            return false;
        }

        Matcher matcher = pattern.matcher(phoneNumber);

        return matcher.matches();
    }

    private Boolean incompleteStudent(Student student) {
        if(student.getEmail() == null || student.getLastName() == null || student.getFirstName() == null) {
            return true;
        }
        return false;
    }

}
