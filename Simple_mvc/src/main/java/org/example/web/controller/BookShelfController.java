package org.example.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.example.app.service.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookAuthorToRemove;
import org.example.web.dto.BookIdToRemove;
import org.example.web.dto.BookSizeToRemove;
import org.example.web.dto.BookTitleToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "books")
@Scope("singleton")
public class BookShelfController {

  private Logger logger = Logger.getLogger(BookShelfController.class);
  private BookService bookService;

  @Autowired
  public BookShelfController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping("/shelf")
  public String books(Model model) {
    logger.info(this.toString());
    model.addAttribute("book", new Book());
    model.addAttribute("bookIdToRemove", new BookIdToRemove());
    model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
    model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
    model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
    model.addAttribute("bookAuthorToFilter", new BookAuthorToRemove());
    model.addAttribute("bookList", bookService.getAllBooks());
    return "book_shelf";
  }

  @PostMapping("/shelf/author")
  public String sortByAuthor(@Valid BookAuthorToRemove bookAuthorToFilter, BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("book", new Book());
      model.addAttribute("bookIdToRemove", new BookIdToRemove());
      model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
      model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
      model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
      model.addAttribute("bookAuthorToFilter", bookAuthorToFilter);
      model.addAttribute("bookList", bookService.getAllBooks());
      return "book_shelf";
    } else {
      logger.info("filter by author: " + bookAuthorToFilter);
      model.addAttribute("book", new Book());
      model.addAttribute("bookIdToRemove", new BookIdToRemove());
      model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
      model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
      model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
      model.addAttribute("bookAuthorToFilter", new BookAuthorToRemove());
      model.addAttribute("bookList", bookService.filterByAuthor(bookAuthorToFilter.getAuthor()));
      return "book_shelf";
    }
  }

  @PostMapping("/shelf/title")
  public String sortByTitle(
      @RequestParam(value = "titleToSort", required = false) String titleToSort, Model model) {
    logger.info("sort by title: " + titleToSort);
    model.addAttribute("book", new Book());
    model.addAttribute("bookList", bookService.sortByTitle(titleToSort));
    return "book_shelf";
  }

  @PostMapping("/shelf/size")
  public String sortBySize(@RequestParam(value = "sizeToSort", required = false) Integer sizeToSort,
      Model model) {
    logger.info("sort by size: " + sizeToSort);
    model.addAttribute("book", new Book());
    model.addAttribute("bookList", bookService.sortBySize(sizeToSort));
    return "book_shelf";
  }

  @PostMapping("/save")
  public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("book", book);
      model.addAttribute("bookIdToRemove", new BookIdToRemove());
      model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
      model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
      model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
      model.addAttribute("bookAuthorToFilter", new BookAuthorToRemove());
      model.addAttribute("bookList", bookService.getAllBooks());
      return "book_shelf";
    } else {
      bookService.saveBook(book);
      logger.info("current repository size: " + bookService.getAllBooks().size());
      return "redirect:/books/shelf";
    }
    //if (!book.getAuthor().isEmpty() && !book.getTitle().isEmpty() && book.getSize()!=null)
  }

  @PostMapping("/remove")
  public String removeBook(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      logger.info("removing failed");
      model.addAttribute("book", new Book());
      model.addAttribute("bookIdToRemove", bookIdToRemove);
      model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
      model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
      model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
      model.addAttribute("bookAuthorToFilter", new BookAuthorToRemove());
      model.addAttribute("bookList", bookService.getAllBooks());
      return "book_shelf";
    } else {
      logger.info("correct removing");
      bookService.removeBookById(bookIdToRemove.getId());
      return "redirect:/books/shelf";
    }
  }

  @PostMapping("/remove/author")
  public String removeBookByAuthor(@Valid BookAuthorToRemove bookAuthorToRemove,
      BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      logger.info("removing failed");
      model.addAttribute("book", new Book());
      model.addAttribute("bookIdToRemove", new BookIdToRemove());
      model.addAttribute("bookAuthorToRemove", bookAuthorToRemove);
      model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
      model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
      model.addAttribute("bookAuthorToFilter", new BookAuthorToRemove());
      model.addAttribute("bookList", bookService.getAllBooks());
      return "book_shelf";
    } else {
      logger.info("correct removing");
      bookService.removeBookByAuthor(bookAuthorToRemove.getAuthor());
      return "redirect:/books/shelf";
    }
  }

  @PostMapping("/remove/title")
  public String removeBookByTitle(@Valid BookTitleToRemove bookTitleToRemove,
      BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      logger.info("removing failed");
      model.addAttribute("book", new Book());
      model.addAttribute("bookIdToRemove", new BookIdToRemove());
      model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
      model.addAttribute("bookTitleToRemove", bookTitleToRemove);
      model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
      model.addAttribute("bookAuthorToFilter", new BookAuthorToRemove());
      model.addAttribute("bookList", bookService.getAllBooks());
      return "book_shelf";
    } else {
      logger.info("correct removing");
      bookService.removeBookByTitle(bookTitleToRemove.getTitle());
      return "redirect:/books/shelf";
    }
  }

  @PostMapping("/remove/size")
  public String removeBookBySize(@Valid BookSizeToRemove bookSizeToRemove,
      BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      logger.info("removing failed");
      model.addAttribute("book", new Book());
      model.addAttribute("bookIdToRemove", new BookIdToRemove());
      model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
      model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
      model.addAttribute("bookSizeToRemove", bookSizeToRemove);
      model.addAttribute("bookAuthorToFilter", new BookAuthorToRemove());
      model.addAttribute("bookList", bookService.getAllBooks());
      return "book_shelf";
    } else {
      logger.info("correct removing");
      bookService.removeBookBySize(bookSizeToRemove.getSize());
      return "redirect:/books/shelf";
    }
  }

  @PostMapping("/uploadFile")
  public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {

    String name = file.getOriginalFilename();
    byte[] bytes = file.getBytes();

    // create dir
    String rootPath = System.getProperty("catalina.home");
    File dir = new File(rootPath + File.separator + "external_uploads");
    if (!dir.exists()) {
      dir.mkdirs();
    }

    // create file
    File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
    stream.write(bytes);
    stream.close();

    logger.info("new file saved at: " + serverFile.getAbsolutePath());

    return "redirect:/books/shelf";
  }

  @ExceptionHandler(FileNotFoundException.class)
  public String handleError(Model model, FileNotFoundException exception) {
    model.addAttribute("errorMessage", "Filename is empty! Choose file. " + exception.getMessage());
    return "errors/500";
  }


}
