package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.data.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BooksRecentController {

  private final BookService bookService;

  public BooksRecentController(BookService bookService) {
    this.bookService = bookService;
  }

//  @ModelAttribute("booksList")
//  public List<Book> bookList(){
//    return bookService.getBooksData();
//  }

  @GetMapping("/books/recent")
  public String booksRecentPage(){
    return "/books/recent";
  }

}
