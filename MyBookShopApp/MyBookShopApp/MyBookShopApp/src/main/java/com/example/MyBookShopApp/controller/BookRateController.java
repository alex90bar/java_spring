package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.data.BookRepository;
import com.example.MyBookShopApp.data.RatingEntity;
import com.example.MyBookShopApp.data.RatingRepository;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookRateController {

  private final RatingRepository ratingRepository;

  @Autowired
  public BookRateController(RatingRepository ratingRepository) {
    this.ratingRepository = ratingRepository;
  }

  public void increaseRating(RatingEntity ratingEntity, int rating){
    switch (rating){
      case 1: ratingEntity.setOneStar(ratingEntity.getOneStar() + 1);
        break;
      case 2: ratingEntity.setTwoStar(ratingEntity.getTwoStar() + 1);
        break;
      case 3: ratingEntity.setThreeStar(ratingEntity.getThreeStar() + 1);
        break;
      case 4: ratingEntity.setFourStar(ratingEntity.getFourStar() + 1);
        break;
      case 5: ratingEntity.setFiveStar(ratingEntity.getFiveStar() + 1);
        break;
    }
  }

  @PostMapping("/rateBook")
  public String rateBook(@RequestParam("bookId") int bookId,
      @RequestParam("value") int value, HttpServletRequest request){
    RatingEntity ratingEntity = ratingRepository.findRatingEntityByBookId(bookId);
    if (ratingEntity != null){

      increaseRating(ratingEntity, value);
      ratingRepository.save(ratingEntity);

    } else {

      ratingEntity = new RatingEntity(0,0,0,0,0);
      ratingEntity.setBookId(bookId);
      increaseRating(ratingEntity, value);
      ratingRepository.save(ratingEntity);
    }
   // return "redirect:/";
    //return ("redirect:" + request.getHeader("referer"));
    return "/books/slug";
  }
}
