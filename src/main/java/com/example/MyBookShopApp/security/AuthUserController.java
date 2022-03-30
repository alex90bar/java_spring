package com.example.MyBookShopApp.security;

import com.example.MyBookShopApp.data.SmsCode;
import com.example.MyBookShopApp.errs.EmptySearchException;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthUserController {

  private final BookStoreUserRegister userRegister;
  private final SmsService smsService;

  @Autowired
  public AuthUserController(BookStoreUserRegister userRegister,
      SmsService smsService) {
    this.userRegister = userRegister;
    this.smsService = smsService;
  }

  @GetMapping("/signin")
  public String handleSignIn(){
    return "signin";
  }

  @GetMapping("/signup")
  public String handleSignUp(Model model){
    model.addAttribute("regForm", new RegistrationForm());
    return "signup";
  }

  @PostMapping("/requestContactConfirmation")
  @ResponseBody
  public ContactConfirmationResponse handleRequestContactConfirmation(@RequestBody
      ContactConfirmationPayload payload) {
    ContactConfirmationResponse response = new ContactConfirmationResponse();
    response.setResult("true");

    if (payload.getContact().contains("@")){
      return response;  //for email
    } else {
      String smsCodeString = smsService.sendSecretCodeSms(payload.getContact());
      smsService.saveNewCode(new SmsCode(smsCodeString, 60));  //expires in 1 min
      return response;
    }

  }

  @PostMapping("/approveContact")
  @ResponseBody
  public ContactConfirmationResponse handleApproveContact(@RequestBody
      ContactConfirmationPayload payload) {
    ContactConfirmationResponse response = new ContactConfirmationResponse();

    if (smsService.verifyCode(payload.getCode())){
      response.setResult("true");
      return response;
    } else {
      if (payload.getContact().contains("@")){
        response.setResult("true");
        return response;
      } else {
        return new ContactConfirmationResponse();
      }
    }
//    response.setResult("true");
//    return response;
  }

  @PostMapping("/reg")
  public String handleUserregistration(RegistrationForm registrationForm,
      Model model){
    userRegister.registerNewUser(registrationForm);
    model.addAttribute("regOk", true);
    return "signin";
  }


  @PostMapping("/login")
  @ResponseBody
  public ContactConfirmationResponse handleLogin(@RequestBody
      ContactConfirmationPayload payload, HttpServletResponse httpServletResponse) {
//    return userRegister.login(payload);

    ContactConfirmationResponse loginResponse = userRegister.jwtLogin(payload);
    Cookie cookie = new Cookie("token", loginResponse.getResult());
    httpServletResponse.addCookie(cookie);
    return loginResponse;
  }

  @PostMapping("/login-by-phone-number")
  @ResponseBody
  public ContactConfirmationResponse handleLoginByPhoneNumber(@RequestBody
      ContactConfirmationPayload payload, HttpServletResponse httpServletResponse) {

    if (smsService.verifyCode(payload.getCode())) {

      ContactConfirmationResponse loginResponse = userRegister.jwtLoginByPhoneNumber(payload);
      Cookie cookie = new Cookie("token", loginResponse.getResult());
      httpServletResponse.addCookie(cookie);
      return loginResponse;
    } else {
      return null;
    }
  }


  @GetMapping("/my")
  public String handleMy(){
    return "my";
  }

  @GetMapping("/profile")
  public String handleProfile(Model model){
    model.addAttribute("curUsr", userRegister.getCurrentUser());
    return "profile";
  }


//  @GetMapping("/logout")
//  public String handleLogout(HttpServletRequest request){
//    HttpSession session = request.getSession();
//    SecurityContextHolder.clearContext();
//    if (session != null){
//      session.invalidate();
//    }
//    for (Cookie cookie : request.getCookies()){
//      cookie.setMaxAge(0);
//    }
//    return "redirect:/";
//  }
}
