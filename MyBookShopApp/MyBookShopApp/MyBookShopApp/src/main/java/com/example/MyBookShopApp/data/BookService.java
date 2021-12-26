package com.example.MyBookShopApp.data;

import io.swagger.models.auth.In;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

  //private JdbcTemplate jdbcTemplate;

  private final BookRepository bookRepository;

  @Autowired
  public BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  //  @Autowired
//  public BookService(JdbcTemplate jdbcTemplate) {
//    this.jdbcTemplate = jdbcTemplate;
//  }

  public List<BookEntity> getBooksData() {
    return bookRepository.findAll();
  }

    // NEW BOOK SERVICE METHODS

  public List<BookEntity> getBooksByAuthor(String authorName) {
    return bookRepository.findBookEntitiesByAuthorNameContaining(authorName);
  }

  public List<BookEntity> getBooksByTitle(String title){
    return bookRepository.findBookEntitiesByTitleContaining(title);
  }

  public List<BookEntity> getBooksWithPriceBetween(Integer min, Integer max){
    return bookRepository.findBookEntitiesByPriceOldBetween(min, max);
  }

  public List<BookEntity> getBooksWithPrice(Integer price){
    return bookRepository.findBookEntitiesByPriceOldIs(price);
  }

  public List<BookEntity> getBooksWithMaxDiscount(){
    return bookRepository.getBooksWithMaxDiscount();
  }

  public List<BookEntity> getBestsellers(){
    return bookRepository.getBestsellers();
  }

  public Page<BookEntity> getPageOfRecommendedBooks(Integer offset, Integer limit){
    Pageable nextPage = PageRequest.of(offset, limit);
    return bookRepository.findAll(nextPage);
  }

  public Page<BookEntity> getPageOfNewBooks(Integer offset, Integer limit){
    Pageable nextPage = PageRequest.of(offset, limit);
    return bookRepository.findAllByOrderByPubDateDesc(nextPage);
  }

  public Page<BookEntity> getPageOfPopularBooks(Integer offset, Integer limit){
    Pageable nextPage = PageRequest.of(offset, limit);
    return bookRepository.findAll(nextPage);
  }

  public Page<BookEntity> getPageOfSearchResultBooks(String searchWord, Integer offset, Integer limit){
    Pageable nextPage = PageRequest.of(offset, limit);
    return bookRepository.findBookEntityByTitleContaining(searchWord, nextPage);
  }



//    List<Book> books = jdbcTemplate
//        .query("SELECT * FROM books",
//            (ResultSet rs, int rowNum) -> {
//// ResultSet с результатами запроса и номер текущей строки на вход передаются
//              Book book = new Book();
//              book.setId(rs.getInt("id"));
//              book.setAuthor(getAuthorByAuthorId(rs.getInt("author_id")));
//              book.setTitle(rs.getString("title"));
//              book.setPriceOld(rs.getString("price_old"));
//              book.setPrice(rs.getString("price"));
//              return book;
//            });
//    return new ArrayList<>(books);

  }

//  private String getAuthorByAuthorId(int author_id) {
//    List<Author> authors = jdbcTemplate
//        .query("SELECT * FROM authors WHERE authors.id = " + author_id,
//            (ResultSet rs, int rowNum) -> {
//          Author author = new Author();
//          author.setId(rs.getInt("id"));
//          author.setFirstName(rs.getString("first_name"));
//          author.setLastName(rs.getString("last_name"));
//          return author;
//            });
//    return authors.get(0).toString();
//  }

//  public List<Book> getBooksData() {
//    List<Book> books = jdbcTemplate
//        .query("SELECT * FROM books b JOIN authors a ON b.id = a.id",
//            (ResultSet rs, int rowNum) -> {
//// ResultSet с результатами запроса и номер текущей строки на вход передаются
//              Book book = new Book();
//              book.setId(rs.getInt("id"));
//              book.setAuthor(rs.getString("last_name") + " " + rs.getString("first_name"));
//              book.setTitle(rs.getString("title"));
//              book.setPriceOld(rs.getString("price_old"));
//              book.setPrice(rs.getString("price"));
//              return book;
//            });
//    return new ArrayList<>(books);
//
//  }

