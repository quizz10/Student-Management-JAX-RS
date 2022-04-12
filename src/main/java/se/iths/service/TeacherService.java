package se.iths.service;

import se.iths.entity.Subject;
import se.iths.entity.Teacher;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Transactional
public class TeacherService {

    @PersistenceContext
    EntityManager entityManager;

    public Teacher addTeacher(Teacher teacher) {
        entityManager.persist(teacher);
        return teacher;
    }

    public Teacher addSubject(Long id, String title) {
        Teacher foundTeacher = findTeacherById(id);
        Subject foundSubject = (Subject) entityManager.createQuery
                ("SELECT s from Subject s where s.title = :title")
                .setParameter("title", title).getSingleResult();
        foundTeacher.addSubject(foundSubject);
        return foundTeacher;
    }

    public Teacher findTeacherById(Long id) {
        return entityManager.find(Teacher.class, id);
    }
}
