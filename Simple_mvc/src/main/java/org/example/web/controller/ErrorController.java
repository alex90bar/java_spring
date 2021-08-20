package org.example.web.controller;

import org.springframework.stereotype.Controller;

// аннотация @ControllerAdvice - отлов исключений по контроллерам

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

  @GetMapping("/404")
  public String notFoundError() {
    return "errors/404";
  }


}
