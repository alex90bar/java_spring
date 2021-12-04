package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.data.AuthorEntity;
import com.example.MyBookShopApp.data.AuthorService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AuthorsController {

  private final AuthorService authorService;

  @Autowired
  public AuthorsController(AuthorService authorService) {
    this.authorService = authorService;
  }

  @ModelAttribute("authorsMap")
  public Map<String, List<AuthorEntity>> authorsMap(){
    return authorService.getAuthorsMap();
  }

  @GetMapping("/authors")
  public String authorsPage(){
    return "/authors/index";
  }

}
