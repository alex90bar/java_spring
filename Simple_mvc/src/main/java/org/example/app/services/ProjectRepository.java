package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {

  List<T> retreiveAll();

  void store(T book);

  boolean removeItemById(Integer bookIdToRemove);

  boolean removeItemBySize(Integer sizeToRemove);

  boolean removeItemByAuthor(String authorToRemove);

  boolean removeItemByTitle(String titleToRemove);
}
