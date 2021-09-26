package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.data.Author;
import com.example.MyBookShopApp.data.AuthorService;
import com.example.MyBookShopApp.data.BookService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bookshop")
public class MainPageController {

  private final BookService bookService;
  private final AuthorService authorService;

  @Autowired
  public MainPageController(BookService bookService, AuthorService authorService) {
    this.bookService = bookService;
    this.authorService = authorService;
  }

  @GetMapping("/main")
  public String mainPage(Model model){
    model.addAttribute("bookData", bookService.getBooksData());
    model.addAttribute("searchPlaceholder", "new search placeholder");
    return "index";
  }

  @GetMapping("/genres")
  public String genresPage(){
    return "genres/index";
  }

  @GetMapping("/authors")
  public String authorsPage(Model model){
    TreeMap<String, ArrayList<Author>> authorsSorted = new TreeMap<>();

    List<Author> authorsList = authorService.getAuthorsData();

    for (Author author : authorsList){
      String letter = String.valueOf(author.getAuthorName().charAt(0));
      if (authorsSorted.containsKey(letter)){
        ArrayList<Author> temporaryList = authorsSorted.get(letter);
        temporaryList.add(author);
        temporaryList.sort(Comparator.comparing(Author::getAuthorName));
        authorsSorted.put(letter, temporaryList);
      } else {
        ArrayList<Author> temporaryList = new ArrayList<>();
        temporaryList.add(author);
        authorsSorted.put(letter, temporaryList);
      }
    }

    model.addAttribute("authorData", authorsSorted);
    return "authors/index";
  }

}
