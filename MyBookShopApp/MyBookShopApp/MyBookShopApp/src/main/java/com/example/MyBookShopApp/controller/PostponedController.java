package com.example.MyBookShopApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PostponedController {

  @GetMapping("/postponed")
  public String postponedPage(){
    return "/postponed";
  }

}
