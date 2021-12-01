package myioc.test;

import myioc.ApplicationContext;
import myioc.Configure;
import myioc.test.classes.Student;
import myioc.test.classes.Teacher;

public class applicationContextTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext(Configure.class);
        Teacher teacher = (Teacher) applicationContext.getBean(Teacher.class);
        Student student = (Student) applicationContext.getBean(Student.class);
        teacher.teacherSpeak();
        student.studentSpeak();
    }
}
