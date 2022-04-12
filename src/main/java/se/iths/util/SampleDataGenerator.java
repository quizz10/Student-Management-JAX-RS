package se.iths.util;

import se.iths.entity.Student;
import se.iths.entity.Subject;
import se.iths.entity.Teacher;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
public class SampleDataGenerator {

    @PersistenceContext
    EntityManager entityManager;

    @PostConstruct
    public void generateData() {
        Student student1 = new Student("Greger", "Artursson", "greger@gmail.com", "000000");
        Student student2 = new Student("Ulf", "Artursson", "ulf@gmail.com", "000001");
        Student student3 = new Student("Leif", "Artursson", "leif@gmail.com", "000002");
        Student student4 = new Student("Camilla", "Artursson", "camilla@gmail.com", "000003");
        Student student5 = new Student("Lily", "Artursson", "lily@gmail.com", "000004");

        Teacher teacher = new Teacher("Börje", "Augustsson");
        Subject subject1 = new Subject("Samhallskunskap");
        Subject subject2 = new Subject("NO");
        Subject subject3 = new Subject("Matte");
        Subject subject4 = new Subject("Svenska");
        Subject subject5 = new Subject("Engelska");
        student1.addSubject(subject1);
        student1.addSubject(subject2);
        student1.addSubject(subject3);
        student1.addSubject(subject4);
        student1.addSubject(subject5);

        teacher.addSubject(subject5);
        teacher.addSubject(subject4);
        teacher.addSubject(subject3);
        teacher.addSubject(subject2);
        teacher.addSubject(subject1);
        student2.addSubject(subject2);

        student3.addSubject(subject1);

        student4.addSubject(subject5);
        student5.addSubject(subject3);

        entityManager.persist(teacher);
        entityManager.persist(subject1);
        entityManager.persist(subject2);
        entityManager.persist(subject3);
        entityManager.persist(subject4);
        entityManager.persist(subject5);
        entityManager.persist(student1);
        entityManager.persist(student2);
        entityManager.persist(student3);
        entityManager.persist(student4);
        entityManager.persist(student5);
    }
}
