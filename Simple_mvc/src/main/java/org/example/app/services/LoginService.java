package org.example.app.services;


import java.util.List;
import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

  private final ProjectRepository<LoginForm> usersRepo;
  private Logger logger = Logger.getLogger(LoginService.class);

  @Autowired
  public LoginService(
      ProjectRepository<LoginForm> usersRepo) {
    this.usersRepo = usersRepo;
    LoginForm rootUser = new LoginForm("root", "123");
    this.usersRepo.store(rootUser);
  }

  public List<LoginForm> getAllUsers() {
    return usersRepo.retreiveAll();
  }

  public boolean authenticate(LoginForm loginForm) {
    logger.info("try auth with user-form: " + loginForm);
    for (LoginForm user : usersRepo.retreiveAll()){
      if (user.getUsername().equals(loginForm.getUsername()) && user.getPassword().equals(loginForm.getPassword())){
        return true;
      }
    }
    return false;
   // return loginForm.getUsername().equals("root") && loginForm.getPassword().equals("123");
  }

  public void createUser(LoginForm loginForm) {
    logger.info("creating new user");
    usersRepo.store(loginForm);
  }
}
