package myioc.test.classes;

import myioc.annotation.Autowired;
import myioc.annotation.Component;

@Component
public class Student {
    @Autowired(name="小明")
    private String name;

    public Student(){System.out.println("A student is created"); }
    public void studentSpeak(){
        System.out.println("My name is" + name);
    }
}
