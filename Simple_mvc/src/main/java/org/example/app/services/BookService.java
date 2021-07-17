package org.example.app.services;

import java.util.ArrayList;
import java.util.List;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  private final ProjectRepository<Book> bookRepo;

  @Autowired
  public BookService(ProjectRepository<Book> bookRepo) {
    this.bookRepo = bookRepo;
  }

  public List<Book> getAllBooks() {
    return bookRepo.retreiveAll();
  }


  public void saveBook(Book book) {
    bookRepo.store(book);
  }

  public boolean removeBookById(Integer bookIdToRemove) {
    return bookRepo.removeItemById(bookIdToRemove);
  }

  public boolean removeBookByAuthor(String authorToRemove) {
    return bookRepo.removeItemByAuthor(authorToRemove);
  }

  public boolean removeBookByTitle(String titleToRemove) {
    return bookRepo.removeItemByTitle(titleToRemove);
  }

  public boolean removeBookBySize(Integer sizeToRemove) {
    return bookRepo.removeItemBySize(sizeToRemove);
  }

  public List<Book> sortByAuthor(String authorToSort) {
    ArrayList<Book> sorted = new ArrayList<>();
    for (Book book: getAllBooks()){
      if (book.getAuthor().equals(authorToSort)){
        sorted.add(book);
      }
    }
    return sorted;
  }

  public List<Book> sortByTitle(String titleToSort) {
    ArrayList<Book> sorted = new ArrayList<>();
    for (Book book: getAllBooks()){
      if (book.getTitle().equals(titleToSort)){
        sorted.add(book);
      }
    }
    return sorted;
  }

  public List<Book> sortBySize(Integer sizeToSort) {
    ArrayList<Book> sorted = new ArrayList<>();
    for (Book book: getAllBooks()){
      if (book.getSize().equals(sizeToSort)){
        sorted.add(book);
      }
    }
    return sorted;
  }
}
