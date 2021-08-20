package org.example.app.repository;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.example.app.service.IdProvider;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

  private final Logger logger = Logger.getLogger(BookRepository.class);
  private final List<Book> repo = new ArrayList<>();
  private ApplicationContext context;

  @Override
  public List<Book> retreiveAll() {
    return new ArrayList<>(repo);
  }

  @Override
  public void store(Book book) {
    book.setId(context.getBean(IdProvider.class).provideId(book));
    logger.info("store new book: " + book);
    repo.add(book);
  }

  @Override
  public boolean removeItemById(String bookIdToRemove) {
    for (Book book: retreiveAll()){
      if (book.getId().equals(bookIdToRemove)){
        logger.info("remove book completed: " + book);
        return repo.remove(book);
      }
    }
    return false;
  }


  public boolean removeItemByAuthor(String authorToRemove) {
    boolean removedSuccess = false;
    for (Book book: retreiveAll()){
      if (book.getAuthor().equals(authorToRemove)){
        logger.info("remove book completed: " + book);
        repo.remove(book);
        removedSuccess = true;
      }
    }
    return removedSuccess;
  }

  public boolean removeItemByTitle(String titleToRemove) {
    boolean removedSuccess = false;
    for (Book book: retreiveAll()){
      if (book.getTitle().equals(titleToRemove)){
        logger.info("remove book completed: " + book);
        repo.remove(book);
        removedSuccess = true;
      }
    }
    return removedSuccess;
  }

  public boolean removeItemBySize(Integer sizeToRemove) {
    boolean removedSuccess = false;
    for (Book book: retreiveAll()){
      if (book.getSize().equals(sizeToRemove)){
        logger.info("remove book completed: " + book);
        repo.remove(book);
        removedSuccess = true;
      }
    }
    return removedSuccess;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.context = applicationContext;
  }

  private void defaultInit() {
    logger.info("default INIT in book repo bean");
  }

  private void defaultDestroy() {
    logger.info("default DESTROY in book repo bean");
  }
}