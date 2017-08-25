package com.mycompany.jpaexercise3;

import db_entities.Semester;
import db_entities.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class Facade
{

    private EntityManagerFactory emf;

    public Facade(EntityManagerFactory emf)
    {
        this.emf = emf;
    }

    public List<Student> findAllStudents()
    {
        EntityManager em = emf.createEntityManager();

        return em.createQuery("select s from Student s").getResultList();
    }

    public List<Student> findAllStudentsNamedAnders()
    {
        EntityManager em = emf.createEntityManager();

        return em.createQuery("select s from Student s where s.firstname = 'Anders'").getResultList();
    }

    public void insertStudent(Student student)
    {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        em.persist(student);

        em.getTransaction().commit();
        em.close();
    }

    public void assignNewStudentToSemester(Student student, long semesterID)
    {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        Student found = em.find(student.getClass(), student.getId());
        Semester foundSem = em.find(Semester.class, semesterID);
        
        if (found != null && foundSem != null)
        {
            found.setCurrentsemesterId(foundSem);
        }

        em.getTransaction().commit();
        em.close();
    }
    
    public List<Student> findAllStudentsWithLastnameAnd()
    {
        EntityManager em = emf.createEntityManager();
        
        return em.createQuery("select s from Student s where s.lastname = 'And'").getResultList();
    }
    
    public int findNumberOfStudentsOnSemesterBySemesterName(String semesterName)
    {
        EntityManager em = emf.createEntityManager();
        
        List<Semester> semesters = em.createQuery("select s from Semester s where s.name = " + semesterName).getResultList();
        
        if (semesters.size() < 1)
            return 0;
        
        Semester sem = semesters.get(0);
        
        List<Student> studentsInSemester = em.createQuery("select s from Student s where s.currentsemesterId = " + sem.getId()).getResultList();
        
        return studentsInSemester.size();
    }
    
    public int numberOfStudentsAcrossAllSemesters()
    {
        return findAllStudents().size();
    }
}
