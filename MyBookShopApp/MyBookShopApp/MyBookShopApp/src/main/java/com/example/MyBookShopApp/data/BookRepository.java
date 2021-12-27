package com.example.MyBookShopApp.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {

  //List<BookEntity> findBooksByAuthor_FirstName(String name);

  //@Query("from BookEntity")
  //List<BookEntity> customFindAllBooks();

  //NEW BOOK REST REPOSITORY COMMANDS
  List<BookEntity> findBookEntitiesByAuthorNameContaining(String authorName);

  List<BookEntity> findBookEntitiesByTitleContaining(String bookTitle);

  List<BookEntity> findBookEntitiesByPriceOldBetween(Integer min, Integer max);

  List<BookEntity> findBookEntitiesByPriceOldIs(Integer price);

  Page<BookEntity> findAllByOrderByPubDateDesc(Pageable nextPage);

  Page<BookEntity> findBookEntitiesByPubDateBetweenOrderByPubDateDesc(LocalDateTime from, LocalDateTime to, Pageable nextPage);

  @Query("from BookEntity where isBestseller=1")
  List<BookEntity> getBestsellers();

  @Query(value = "SELECT * FROM book WHERE discount = (SELECT MAX(discount) FROM book)", nativeQuery = true)
  List<BookEntity> getBooksWithMaxDiscount();

  Page<BookEntity> findBookEntityByTitleContaining(String bookTitle, Pageable nextPage);

}
