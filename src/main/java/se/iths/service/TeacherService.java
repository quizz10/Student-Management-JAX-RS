package se.iths.service;

import se.iths.entity.Subject;
import se.iths.entity.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class TeacherService {

    @PersistenceContext
    EntityManager entityManager;

    public Teacher addTeacher(Teacher newTeacher) {
        entityManager.persist(newTeacher);
        return newTeacher;
    }

    public Teacher addSubject(Long id, String title) {
        Teacher foundTeacher = findTeacherById(id);
        Subject foundSubject = (Subject) entityManager.createQuery
                        ("SELECT s from Subject s where s.title = :title")
                .setParameter("title", title).getSingleResult();
        foundTeacher.addSubject(foundSubject);
        return foundTeacher;
    }

    public void removeTeacher(Long id) {
        Teacher foundTeacher = findTeacherById(id);

        for (Subject subject : foundTeacher.getSubjects()) {
            subject.setTeacher(null);
        }
        entityManager.remove(foundTeacher);
    }

    public Teacher findTeacherById(Long id) {
        return entityManager.find(Teacher.class, id);
    }

    public void updateTeacher(Teacher teacher) {
        entityManager.merge(teacher);
    }

    public List<Teacher> getAllTeachers() {
        return entityManager.createQuery("Select t from Teacher t", Teacher.class).getResultList();
    }
}
