package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.data.BookEntity;
import com.example.MyBookShopApp.data.BookRepository;
import com.example.MyBookShopApp.data.ResourceStorage;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/books")
public class BooksController {

  private final BookRepository bookRepository;
  private final ResourceStorage storage;

  @Autowired
  public BooksController(BookRepository bookRepository, ResourceStorage storage) {
    this.bookRepository = bookRepository;
    this.storage = storage;
  }

  @GetMapping("/{slug}")
  public String bookPage(@PathVariable("slug") String slug, Model model){
    BookEntity book = bookRepository.findBookEntityBySlug(slug);
    model.addAttribute("slugBook", book);
    return "/books/slug";
  }

  @PostMapping("/{slug}/img/save")
  public String saveNewBookImage(@RequestParam("file") MultipartFile file, @PathVariable("slug") String slug)
      throws IOException {
    String savePath = storage.saveNewBookImage(file, slug);
    BookEntity bookToUpdate = bookRepository.findBookEntityBySlug(slug);
    bookToUpdate.setImage(savePath);
    bookRepository.save(bookToUpdate);   //save new path to db here

    return ("redirect:/books/" + slug);
  }



}
