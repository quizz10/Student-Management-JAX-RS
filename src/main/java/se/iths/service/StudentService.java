package se.iths.service;

import se.iths.entity.Student;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class StudentService {

    @PersistenceContext
    EntityManager entityManager;

    public void addStudent(Student student) {
        entityManager.persist(student);
    }

    public void updateStudent(Student student) {
        entityManager.merge(student);
    }

    public List<Student> getAllStudents() {
       return entityManager.createQuery("SELECT s from Student s", Student.class).getResultList();
    }

    public void removeStudent(Long id) {
        entityManager.remove(findStudentById(id));
    }

    public Student findStudentById(Long id) {
        return entityManager.find(Student.class, id);
    }

    public List<Student> findStudentByLastName(String lastName) {
        return entityManager.createQuery("SELECT s from Student s where s.lastName = :lastName")
                .setParameter("lastName", lastName).getResultList();
    }

    public Student updatePhoneNumber(Long id, String phoneNumber) {
        Student foundStudent = findStudentById(id);

        foundStudent.setPhoneNumber(phoneNumber);
        return foundStudent;
    }
}
