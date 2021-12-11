package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.data.AuthorEntity;
import com.example.MyBookShopApp.data.AuthorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(description = "authors data")
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

  @ApiOperation("method to get map of authors")
  @GetMapping("/api/authors")
  @ResponseBody // аннотация возвращает ответ напрямую данными, а не через шаблон Thymeleaf
  public Map<String, List<AuthorEntity>> authors(){
    return authorService.getAuthorsMap();
  }

}
