package com.example.MyBookShopApp.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

  //List<BookEntity> findBooksByAuthor_FirstName(String name);

  //@Query("from BookEntity")
  //List<BookEntity> customFindAllBooks();

}
