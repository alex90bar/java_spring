package com.example.MyBookShopApp.data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

//  private JdbcTemplate jdbcTemplate;

  private final AuthorRepository authorRepository;

  @Autowired
  public AuthorService(AuthorRepository authorRepository) {
    this.authorRepository = authorRepository;
  }

  //  @Autowired
//  public AuthorService(JdbcTemplate jdbcTemplate) {
//    this.jdbcTemplate = jdbcTemplate;
//  }

//  public List<Author> getAuthorsData() {
//    List<Author> authors = jdbcTemplate
//        .query("SELECT * FROM authors",
//            (ResultSet rs, int rowNum) -> {
//// ResultSet с результатами запроса и номер текущей строки на вход передаются
//              Author author = new Author();
//              author.setId(rs.getInt("id"));
//              author.setAuthorName(rs.getString("author_name"));
//              return author;
//            });
//    authors.sort(Comparator.comparing(Author::getAuthorName));
//
//    return new ArrayList<>(authors);
//
//  }

  public Map<String, List<AuthorEntity>> getAuthorsMap() {

    List<AuthorEntity> authors = authorRepository.findAll();

//    List<Author> authors = jdbcTemplate
//        .query("SELECT * FROM authors",
//            (ResultSet rs, int rowNum) -> {
//// ResultSet с результатами запроса и номер текущей строки на вход передаются
//              Author author = new Author();
//              author.setId(rs.getInt("id"));
//              author.setFirstName(rs.getString("first_name"));
//              author.setLastName(rs.getString("last_name"));
//              return author;
//            });

    return authors.stream().collect(Collectors.groupingBy((AuthorEntity a) -> {
      return a.getName().substring(0,1);
    }));
  }
}
