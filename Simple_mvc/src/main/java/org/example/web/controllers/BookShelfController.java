package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "books")
public class BookShelfController {

  private Logger logger = Logger.getLogger(BookShelfController.class);
  private BookService bookService;

  @Autowired
  public BookShelfController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/shelf")
  public String books(Model model){
    logger.info("got book shelf");
    model.addAttribute("book", new Book());
    model.addAttribute("bookList", bookService.getAllBooks());
    return "book_shelf";
  }

  @PostMapping("/shelf/author")
  public String sortByAuthor(@RequestParam(value = "authorToSort", required = false) String authorToSort, Model model){
    logger.info("sort by author: " + authorToSort);
    model.addAttribute("book", new Book());
    model.addAttribute("bookList", bookService.sortByAuthor(authorToSort));
    return "book_shelf";
  }

  @PostMapping("/shelf/title")
  public String sortByTitle(@RequestParam(value = "titleToSort", required = false) String titleToSort, Model model){
    logger.info("sort by title: " + titleToSort);
    model.addAttribute("book", new Book());
    model.addAttribute("bookList", bookService.sortByTitle(titleToSort));
    return "book_shelf";
  }

  @PostMapping("/shelf/size")
  public String sortBySize(@RequestParam(value = "sizeToSort", required = false) Integer sizeToSort, Model model){
    logger.info("sort by size: " + sizeToSort);
    model.addAttribute("book", new Book());
    model.addAttribute("bookList", bookService.sortBySize(sizeToSort));
    return "book_shelf";
  }

  @PostMapping("/save")
  public String saveBook(Book book){
    if (!book.getAuthor().isEmpty() && !book.getTitle().isEmpty() && book.getSize()!=null) {
      bookService.saveBook(book);
      logger.info("current repository size: " + bookService.getAllBooks().size());
    }
    return "redirect:/books/shelf";
  }

  @PostMapping("/remove")
  public String removeBook(@RequestParam(value = "bookIdToRemove", required = false) Integer bookIdToRemove){
    if(bookService.removeBookById(bookIdToRemove)){
      return "redirect:/books/shelf";
    } else {
      logger.info("removing false");
      return "redirect:/books/shelf";
    }
  }

  @PostMapping("/remove/author")
  public String removeBookByAuthor(@RequestParam(value = "authorToRemove", required = false) String authorToRemove){
    if(bookService.removeBookByAuthor(authorToRemove)){
      return "redirect:/books/shelf";
    } else {
      logger.info("removing false");
      return "redirect:/books/shelf";
    }
  }

  @PostMapping("/remove/title")
  public String removeBookByTitle(@RequestParam(value = "titleToRemove", required = false) String titleToRemove){
    if(bookService.removeBookByTitle(titleToRemove)){
      return "redirect:/books/shelf";
    } else {
      logger.info("removing false");
      return "redirect:/books/shelf";
    }
  }

  @PostMapping("/remove/size")
  public String removeBookBySize(@RequestParam(value = "sizeToRemove", required = false) Integer sizeToRemove){
    if(bookService.removeBookBySize(sizeToRemove)){
      return "redirect:/books/shelf";
    } else {
      logger.info("removing false");
      return "redirect:/books/shelf";
    }
  }


}
