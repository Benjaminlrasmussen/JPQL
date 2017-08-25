package com.mycompany.jpaexercise3;

import db_entities.Student;
import java.util.HashMap;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Initializer
{

    public static void main(String[] args)
    {
        /////// Setup //////////////////
        Persistence.generateSchema("jpaPU", new HashMap());
        ////////////////////////////////

        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("jpaPU");
        Facade facade = new Facade(emFactory);

        for (Student s : facade.findAllStudents())
        {
            System.out.println(s.getFirstname());
        }

        System.out.println("All students named Anders: " + facade.findAllStudentsNamedAnders().size());
        
        Student stu = new Student();
        stu.setFirstname("Anders");
        facade.insertStudent(stu);
        
        System.out.println("All students named Anders now: " + facade.findAllStudentsNamedAnders().size());

        Student andStudent = facade.findAllStudentsWithLastnameAnd().get(0);
        System.out.println("Current semester: " + andStudent.getCurrentsemesterId().getId());
        facade.assignNewStudentToSemester(andStudent, 3l);
        System.out.println("Semester is now: " + facade.findAllStudentsWithLastnameAnd().get(0).getCurrentsemesterId().getId());
        
        // I think this pretty much show the functionality without delving further //
    }
}
