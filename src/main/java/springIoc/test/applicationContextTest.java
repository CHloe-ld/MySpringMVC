package springIoc.test;

import springIoc.utils.ApplicationContext;
import springIoc.Configure;
import springIoc.test.classes.Student;
import springIoc.test.classes.Teacher;

import java.util.ArrayList;

public class applicationContextTest {
    public static void main(String[] args) {
//        ApplicationContext applicationContext = new ApplicationContext(Configure.class);
//        Teacher teacher = (Teacher) applicationContext.getBean(Teacher.class);
//        Student student = (Student) applicationContext.getBean(Student.class);
//        teacher.teacherSpeak();
//        student.studentSpeak();
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        System.out.println(arrayList);
    }
}
