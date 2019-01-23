package pl.edu.kopalniakodu.todoapp.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/")
    public String testingView() {
        return "index";
    }

}
