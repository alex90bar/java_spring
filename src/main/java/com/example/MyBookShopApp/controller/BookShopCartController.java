package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.data.BookEntity;
import com.example.MyBookShopApp.data.BookRepository;
import com.example.MyBookShopApp.data.PaymentService;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/books")
public class BookShopCartController {

  @ModelAttribute(name = "bookCart")
  public List<BookEntity> bookCart(){
    return new ArrayList<>();
  }

  private final BookRepository bookRepository;
  private final PaymentService paymentService;

  @Autowired
  public BookShopCartController(BookRepository bookRepository,
      PaymentService paymentService) {
    this.bookRepository = bookRepository;
    this.paymentService = paymentService;
  }

  @GetMapping("/cart")
  public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
      Model model){
    if (cartContents == null || cartContents.equals("")){
      model.addAttribute("isCartEmpty", true);
    } else {
      model.addAttribute("isCartEmpty", false);
      cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
      cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() -1) : cartContents;
      String[] cookieSlugs = cartContents.split("/");
      List<BookEntity> booksFromCookieSlugs = bookRepository.findBookEntitiesBySlugIn(cookieSlugs);
      model.addAttribute("bookCart", booksFromCookieSlugs);

    }
    return "cart";
  }


  @PostMapping("/changeBookStatus/cart/remove/{slug}")
  public String handleRemoveBookFromCartRequest(@PathVariable("slug") String slug,
  @CookieValue(name = "cartContents", required = false) String cartContents,
  HttpServletResponse response, Model model){
    if (cartContents != null && !cartContents.equals("")){
      ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
      cookieBooks.remove(slug);
      Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
      cookie.setPath("/books");
      response.addCookie(cookie);
      model.addAttribute("isCartEmpty", false);
    } else {
      model.addAttribute("isCartEmpty", true);
    }
    return "redirect:/books/cart";
  }

  @PostMapping("/changeBookStatus/{slug}")
  public String handleChangeBookStatus(@PathVariable("slug") String slug,
      @CookieValue(name = "cartContents", required = false) String cartContents,
      HttpServletResponse response, Model model){

    if (cartContents == null || cartContents.equals("")){
      Cookie cookie = new Cookie("cartContents", slug);
      cookie.setPath("/books");
      response.addCookie(cookie);
      model.addAttribute("isCartEmpty", false);
    } else if (!cartContents.contains(slug)){
      StringJoiner stringJoiner = new StringJoiner("/");
      stringJoiner.add(cartContents).add(slug);
      Cookie cookie = new Cookie("cartContents", stringJoiner.toString());
      cookie.setPath("/books");
      response.addCookie(cookie);
      model.addAttribute("isCartEmpty", false);
    }
    return "redirect:/books/" + slug;
  }

  @GetMapping("/pay")
  public RedirectView handlePay(@CookieValue(value = "cartContents", required = false) String cartContents)
      throws NoSuchAlgorithmException {
    cartContents = cartContents.startsWith("/") ? cartContents.substring(1) : cartContents;
    cartContents = cartContents.endsWith("/") ? cartContents.substring(0, cartContents.length() -1) : cartContents;
    String[] cookieSlugs = cartContents.split("/");
    List<BookEntity> booksFromCookieSlugs = bookRepository.findBookEntitiesBySlugIn(cookieSlugs);
    String paymentUrl = paymentService.getPaymentUrl(booksFromCookieSlugs);
    return new RedirectView(paymentUrl);
  }



  }



