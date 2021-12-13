package com.example.MyBookShopApp.controller;

import com.example.MyBookShopApp.data.BookEntity;
import com.example.MyBookShopApp.data.BookService;
import com.example.MyBookShopApp.data.BooksPageDto;
import com.example.MyBookShopApp.data.SearchWordDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainPageController {

  private final BookService bookService;
 // private final AuthorService authorService;

  @Autowired
  public MainPageController(BookService bookService) {
    this.bookService = bookService;
  }

  @ModelAttribute("recommendedBooks")
  public List<BookEntity> recommendedBooks(){
    return bookService.getPageOfRecommendedBooks(0, 6).getContent();
  }

  @ModelAttribute("searchWordDto")
  public SearchWordDto searchWordDto(){
    return new SearchWordDto();
  }

  @ModelAttribute("searchResults")
  public List<BookEntity> searchResults(){
    return new ArrayList<>();
  }

  @GetMapping("/books/recommended")
  @ResponseBody
  public BooksPageDto getBooksPage(@RequestParam("offset") Integer offset,
      @RequestParam("limit") Integer limit){
    return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
  }

  @GetMapping(value = {"/search", "/search/{searchWord}"})
  public String getSearchResults(@PathVariable(value = "searchWord", required = false)
      SearchWordDto searchWordDto, Model model){
    model.addAttribute("searchWordDto", searchWordDto);
    model.addAttribute("searchResults",
        bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), 0,5).getContent());
    return "/search/index";
  }

  @GetMapping("/search/page/{searchWord}")
  @ResponseBody
  public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset,
      @RequestParam("limit") Integer limit,
      @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto){
    return new BooksPageDto(bookService
        .getPageOfSearchResultBooks(searchWordDto.getExample(),offset,limit).getContent());
  }

  @GetMapping("/")
  public String mainPage(){
    return "index";
  }
//
//  @GetMapping("/genres")
//  public String genresPage(){
//    return "genres/index";
//  }
//
//  @GetMapping("/authors")
//  public String authorsPage(Model model){
//    TreeMap<String, ArrayList<Author>> authorsSorted = new TreeMap<>();
//
//    List<Author> authorsList = authorService.getAuthorsData();
//
//    for (Author author : authorsList){
//      String letter = String.valueOf(author.getAuthorName().charAt(0));
//      if (authorsSorted.containsKey(letter)){
//        ArrayList<Author> temporaryList = authorsSorted.get(letter);
//        temporaryList.add(author);
//        temporaryList.sort(Comparator.comparing(Author::getAuthorName));
//        authorsSorted.put(letter, temporaryList);
//      } else {
//        ArrayList<Author> temporaryList = new ArrayList<>();
//        temporaryList.add(author);
//        authorsSorted.put(letter, temporaryList);
//      }
//    }
//
//    model.addAttribute("authorData", authorsSorted);
//    return "authors/index";
//  }

}
