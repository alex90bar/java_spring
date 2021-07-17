package org.example.web.controllers;


import org.apache.log4j.Logger;
import org.example.app.services.LoginService;
import org.example.web.dto.LoginForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

  private Logger logger = Logger.getLogger(LoginController.class);
  private LoginService loginService;

  @Autowired
  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }


  @GetMapping
  public String login(Model model){
    logger.info("GET /login returns login_page.html");
    model.addAttribute("loginForm", new LoginForm());
    return "login_page";
  }

  @GetMapping("/register")
  public String register(Model model){
    logger.info("GET /login/register returns register_page.html");
    model.addAttribute("loginForm", new LoginForm());
    return "register_page";
  }

  @PostMapping("/auth")
  public String authenticate(LoginForm loginForm){
    if (loginService.authenticate(loginForm)) {
      logger.info("login OK redirect to book shelf");
      return "redirect:/books/shelf";
    } else {
      logger.info("login FAIL redirect back to login");
      return "redirect:/login";
    }
  }

  @PostMapping("/register")
  public String createUser(LoginForm loginForm){
    loginService.createUser(loginForm);
    logger.info("user created. Number of users: " + loginService.getAllUsers().size());
    return "redirect:/login";
  }

}
