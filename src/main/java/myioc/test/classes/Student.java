package myioc.test.classes;

import myioc.annotation.Component;

@Component
public class Student {
    public Student(){
        System.out.println("A student is created");
    }
    public void studentSpeak(){
        System.out.println("Student is speaking");
    }
}
