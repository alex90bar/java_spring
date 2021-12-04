package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.data.BookEntity;
import com.example.MyBookShopApp.data.BookService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class MainPageController {

  private final BookService bookService;
 // private final AuthorService authorService;

  @Autowired
  public MainPageController(BookService bookService) {
    this.bookService = bookService;
  }

  @ModelAttribute("recommendedBooks")
  public List<BookEntity> recommendedBooks(){
    return bookService.getBooksData();
  }

  @GetMapping("/")
  public String mainPage(){
    return "index";
  }
//
//  @GetMapping("/genres")
//  public String genresPage(){
//    return "genres/index";
//  }
//
//  @GetMapping("/authors")
//  public String authorsPage(Model model){
//    TreeMap<String, ArrayList<Author>> authorsSorted = new TreeMap<>();
//
//    List<Author> authorsList = authorService.getAuthorsData();
//
//    for (Author author : authorsList){
//      String letter = String.valueOf(author.getAuthorName().charAt(0));
//      if (authorsSorted.containsKey(letter)){
//        ArrayList<Author> temporaryList = authorsSorted.get(letter);
//        temporaryList.add(author);
//        temporaryList.sort(Comparator.comparing(Author::getAuthorName));
//        authorsSorted.put(letter, temporaryList);
//      } else {
//        ArrayList<Author> temporaryList = new ArrayList<>();
//        temporaryList.add(author);
//        authorsSorted.put(letter, temporaryList);
//      }
//    }
//
//    model.addAttribute("authorData", authorsSorted);
//    return "authors/index";
//  }

}
