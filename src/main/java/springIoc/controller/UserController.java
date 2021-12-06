package springIoc.controller;

import springIoc.annotation.Controller;
import springIoc.annotation.RequestMapping;
import springIoc.view.View;

@Controller
@RequestMapping("/myMvc/user")
public class UserController {
    @RequestMapping("/home")
    public View name(){
        System.out.println(55555);
        View view = new View("/WEB-INF/file.jsp");
        return view;
    }

    @RequestMapping("/add")
    public View add(){
        View view = new View("/WEB-INF/test.html");
        return view;
    }
}
