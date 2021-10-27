package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.data.Book;
import com.example.MyBookShopApp.data.BookService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class BooksPopularController {

  private final BookService bookService;

  public BooksPopularController(BookService bookService) {
    this.bookService = bookService;
  }

  @ModelAttribute("booksList")
  public List<Book> bookList(){
    return bookService.getBooksData();
  }

  @GetMapping("/books/popular")
  public String booksPopularPage(){
    return "/books/popular";
  }

}
