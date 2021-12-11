package org.example.app.repository;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.example.app.service.IdProvider;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

  private final Logger logger = Logger.getLogger(BookRepository.class);
  //private final List<Book> repo = new ArrayList<>();
  private ApplicationContext context;

  private final NamedParameterJdbcTemplate jdbcTemplate;

  @Autowired
  public BookRepository(
      NamedParameterJdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<Book> retreiveAll() {
    List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
      Book book = new Book();
      book.setId(rs.getInt("id"));
      book.setAuthor(rs.getString("author"));
      book.setTitle(rs.getString("title"));
      book.setSize(rs.getInt("size"));
      return book;
        });
    return new ArrayList<>(books);
  }

  @Override
  public void store(Book book) {
    //book.setId(context.getBean(IdProvider.class).provideId(book));
    // repo.add(book);
    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
    parameterSource.addValue("author", book.getAuthor());
    parameterSource.addValue("title", book.getTitle());
    parameterSource.addValue("size", book.getSize());
    jdbcTemplate.update("INSERT INTO books(author,title,size) VALUES (:author, :title, :size)", parameterSource);
    logger.info("store new book: " + book);
  }

  @Override
  public boolean removeItemById(Integer bookIdToRemove) {
    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
    parameterSource.addValue("id", bookIdToRemove);
    jdbcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);
        logger.info("remove book completed");
        return true;
  }


  public boolean removeItemByAuthor(String bookAuthorToRemove) {
    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
    parameterSource.addValue("author", bookAuthorToRemove);
    jdbcTemplate.update("DELETE FROM books WHERE author = :author", parameterSource);
    logger.info("remove book completed");
    return true;
  }

  public boolean removeItemByTitle(String bookTitleToRemove) {
    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
    parameterSource.addValue("title", bookTitleToRemove);
    jdbcTemplate.update("DELETE FROM books WHERE title = :title", parameterSource);
    logger.info("remove book completed");
    return true;
  }

  @Override
  public List<String> getAllFiles() {
    ArrayList<String> listFiles = new ArrayList<>();

    String rootPath = System.getProperty("catalina.home");
    File dir = new File(rootPath + File.separator + "external_uploads");

    File[] files = dir.listFiles();

    for (File file : files){
      listFiles.add(file.getName());
    }
    return listFiles;
  }

  public boolean removeItemBySize(Integer bookSizeToRemove) {
    MapSqlParameterSource parameterSource = new MapSqlParameterSource();
    parameterSource.addValue("size", bookSizeToRemove);
    jdbcTemplate.update("DELETE FROM books WHERE size = :size", parameterSource);
    logger.info("remove book completed");
    return true;
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