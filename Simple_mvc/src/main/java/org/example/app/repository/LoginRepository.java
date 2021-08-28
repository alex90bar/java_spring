package org.example.app.repository;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Repository;

@Repository
public class LoginRepository implements ProjectRepository<LoginForm> {

  private final Logger logger = Logger.getLogger(LoginRepository.class);
  private final List<LoginForm> repo = new ArrayList<>();

  @Override
  public List<LoginForm> retreiveAll() {
    return new ArrayList<>(repo);
  }

  @Override
  public void store(LoginForm loginForm) {
    logger.info("create new user: " + loginForm);
    repo.add(loginForm);
  }

  @Override
  public boolean removeItemById(Integer userIdToRemove) {
    return false;
  }

  @Override
  public boolean removeItemBySize(Integer sizeToRemove) {
    return false;
  }

  @Override
  public boolean removeItemByAuthor(String authorToRemove) {
    return false;
  }

  @Override
  public boolean removeItemByTitle(String titleToRemove) {
    return false;
  }
}
