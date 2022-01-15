package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.data.BookEntity;
import com.example.MyBookShopApp.data.BookRepository;
import com.example.MyBookShopApp.data.ResourceStorage;
import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

  @GetMapping("/download/{hash}")
  public ResponseEntity<ByteArrayResource> bookFile(@PathVariable("hash") String hash)
      throws IOException {

    Path path = storage.getBookFilePath(hash);
    Logger.getLogger(this.getClass().getSimpleName()).info("book file path: " + path);

    MediaType mediaType = storage.getBookFileMime(hash);
    Logger.getLogger(this.getClass().getSimpleName()).info("book file mime type: " + mediaType);

    byte[] data = storage.getBookFileByteArray(hash);
    Logger.getLogger(this.getClass().getSimpleName()).info("book file data lentgh: " + data.length);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + path.getFileName().toString())
        .contentType(mediaType)
        .contentLength(data.length)
        .body(new ByteArrayResource(data));
  }



}
