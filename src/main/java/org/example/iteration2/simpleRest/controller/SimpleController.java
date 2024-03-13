package org.example.iteration2.simpleRest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SimpleController {
    @GetMapping("/")
    public String findAuthorById(){
        return "StudyRestTest";
    }
}
