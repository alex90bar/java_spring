package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.errs.EmptySearchException;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandlerController {

  @ExceptionHandler(EmptySearchException.class)
  public String handleEmptySearchException(EmptySearchException e, RedirectAttributes redirectAttributes){
    Logger.getLogger(this.getClass().getSimpleName()).warning(e.getLocalizedMessage());
    redirectAttributes.addFlashAttribute("searchError", e);
    return "redirect:/";
  }


}
