package org.example.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.apache.log4j.Logger;
import org.example.app.service.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookAuthorToFilter;
import org.example.web.dto.BookAuthorToRemove;
import org.example.web.dto.BookIdToRemove;
import org.example.web.dto.BookSizeToFilter;
import org.example.web.dto.BookSizeToRemove;
import org.example.web.dto.BookTitleToFilter;
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
    model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
    model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
    model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
    model.addAttribute("bookList", bookService.getAllBooks());
    model.addAttribute("listFiles", bookService.getAllFiles());
    return "book_shelf";
  }

  @PostMapping("/shelf/author")
  public String sortByAuthor(@Valid BookAuthorToFilter bookAuthorToFilter, BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("book", new Book());
      model.addAttribute("bookIdToRemove", new BookIdToRemove());
      model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
      model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
      model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
      model.addAttribute("bookAuthorToFilter", bookAuthorToFilter);
      model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
      model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
      model.addAttribute("bookList", bookService.getAllBooks());
      model.addAttribute("listFiles", bookService.getAllFiles());
      return "book_shelf";
    } else {
      logger.info("filter by author: " + bookAuthorToFilter);
      model.addAttribute("book", new Book());
      model.addAttribute("bookIdToRemove", new BookIdToRemove());
      model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
      model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
      model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
      model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
      model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
      model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
      model.addAttribute("listFiles", bookService.getAllFiles());
      model.addAttribute("bookList", bookService.filterByAuthor(bookAuthorToFilter.getAuthor()));
      return "book_shelf";
    }
  }

  @PostMapping("/shelf/title")
  public String sortByTitle(@Valid BookTitleToFilter bookTitleToFilter, BindingResult bindingResult,
          Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("book", new Book());
      model.addAttribute("bookIdToRemove", new BookIdToRemove());
      model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
      model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
      model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
      model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
      model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
      model.addAttribute("bookTitleToFilter", bookTitleToFilter);
      model.addAttribute("bookList", bookService.getAllBooks());
      model.addAttribute("listFiles", bookService.getAllFiles());
      return "book_shelf";
    } else {
      logger.info("filter by size: " + bookTitleToFilter);
      model.addAttribute("book", new Book());
      model.addAttribute("bookIdToRemove", new BookIdToRemove());
      model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
      model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
      model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
      model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
      model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
      model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
      model.addAttribute("listFiles", bookService.getAllFiles());
      model.addAttribute("bookList", bookService.filterByTitle(bookTitleToFilter.getTitle()));
      return "book_shelf";
    }
  }

  @PostMapping("/shelf/size")
  public String sortBySize(@Valid BookSizeToFilter bookSizeToFilter, BindingResult bindingResult,
      Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("book", new Book());
      model.addAttribute("bookIdToRemove", new BookIdToRemove());
      model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
      model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
      model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
      model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
      model.addAttribute("bookSizeToFilter", bookSizeToFilter);
      model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
      model.addAttribute("listFiles", bookService.getAllFiles());
      model.addAttribute("bookList", bookService.getAllBooks());
      return "book_shelf";
    } else {
      logger.info("filter by size: " + bookSizeToFilter);
      model.addAttribute("book", new Book());
      model.addAttribute("bookIdToRemove", new BookIdToRemove());
      model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
      model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
      model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
      model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
      model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
      model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
      model.addAttribute("listFiles", bookService.getAllFiles());
      model.addAttribute("bookList", bookService.filterBySize(bookSizeToFilter.getSize()));
      return "book_shelf";
    }
  }

  @PostMapping("/save")
  public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("book", book);
      model.addAttribute("bookIdToRemove", new BookIdToRemove());
      model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
      model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
      model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
      model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
      model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
      model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
      model.addAttribute("listFiles", bookService.getAllFiles());
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
      model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
      model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
      model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
      model.addAttribute("listFiles", bookService.getAllFiles());
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
      model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
      model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
      model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
      model.addAttribute("listFiles", bookService.getAllFiles());
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
      model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
      model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
      model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
      model.addAttribute("listFiles", bookService.getAllFiles());
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
      model.addAttribute("bookAuthorToFilter", new BookAuthorToFilter());
      model.addAttribute("bookSizeToFilter", new BookSizeToFilter());
      model.addAttribute("bookTitleToFilter", new BookTitleToFilter());
      model.addAttribute("listFiles", bookService.getAllFiles());
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

    try(BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
      stream.write(bytes);
    }
//    stream.close();

    logger.info("new file saved at: " + serverFile.getAbsolutePath());

    return "redirect:/books/shelf";
  }

  @GetMapping("/downloadFile")
  public void downloadFile(@Valid String file, HttpServletResponse response) throws Exception{

      String rootPath = System.getProperty("catalina.home");
      File dir = new File(rootPath + File.separator + "external_uploads");

      Path fileToDownload = Paths.get(String.valueOf(dir), file);

      if (Files.exists(fileToDownload)) {
        response.setHeader("Content-disposition", "attachment;filename=" + file);
        response.setContentType("application/octet-stream");
        try {
          Files.copy(fileToDownload, response.getOutputStream());
          response.getOutputStream().flush();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }


  }

  @ExceptionHandler(FileNotFoundException.class)
  public String handleError(Model model, FileNotFoundException exception) {
    model.addAttribute("errorMessage", "Filename is empty! Choose file. " + exception.getMessage());
    return "errors/500";
  }


}
