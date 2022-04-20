package se.iths.service;

import se.iths.entity.Student;
import se.iths.entity.Subject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class SubjectService {

    @PersistenceContext
    EntityManager entityManager;

    public void addSubject(Subject subject) {
        entityManager.persist(subject);
    }

    public void removeSubject(Long id) {
        Subject foundSubject = findSubjectById(id);

        for (Student student : foundSubject.getStudents()) {
            student.removeSubject(foundSubject);
        }
        foundSubject.getTeacher().removeSubject(foundSubject);
        entityManager.remove(foundSubject);
    }

    public Student removeStudent(Long id, String email) {
        Subject subject = findSubjectById(id);
        Student student = (Student) entityManager.createQuery("SELECT s from Student s where s.email = :email")
                .setParameter("email", email).getSingleResult();
        subject.removeStudent(student);
        return student;
    }

    public void removeTeacher(Long id) {
        Subject subject = findSubjectById(id);
        subject.setTeacher(null);
    }

    public Subject findSubjectById(Long id) {
        return entityManager.find(Subject.class, id);
    }

    public List<Subject> getAllSubjects() {
        return entityManager.createQuery("SELECT s from Subject s", Subject.class).getResultList();
    }

    public Subject updateSubject(Long id, String title) {
        Subject changeSubjectName = findSubjectById(id);
        changeSubjectName.setTitle(title);
        return changeSubjectName;
    }
}
