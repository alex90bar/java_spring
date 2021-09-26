package com.example.MyBookShopApp.data;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

  private JdbcTemplate jdbcTemplate;

  @Autowired
  public AuthorService(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public List<Author> getAuthorsData() {
    List<Author> authors = jdbcTemplate
        .query("SELECT * FROM authors",
            (ResultSet rs, int rowNum) -> {
// ResultSet с результатами запроса и номер текущей строки на вход передаются
              Author author = new Author();
              author.setId(rs.getInt("id"));
              author.setAuthorName(rs.getString("author_name"));
              return author;
            });
    authors.sort(Comparator.comparing(Author::getAuthorName));
    return new ArrayList<>(authors);

  }
}
