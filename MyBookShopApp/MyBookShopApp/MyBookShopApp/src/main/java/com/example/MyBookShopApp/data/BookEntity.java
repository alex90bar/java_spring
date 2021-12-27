package com.example.MyBookShopApp.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "book")
@ApiModel(description = "entity representing a book")
public class BookEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @ApiModelProperty("id generated by db automatically")
  private Integer id;

  @Column(name = "pub_date", columnDefinition = "TIMESTAMP NOT NULL")
  @ApiModelProperty("date of book publication")
  private LocalDateTime pubDate;

  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  @JsonIgnore
  private AuthorEntity author;

  @Column(name = "is_bestseller", columnDefinition = "SMALLINT NOT NULL")
  @ApiModelProperty("if is_bestseller=1 so the book is considered to be bestseller, "
      + "if 0 the book is not a bestseller")
  private short isBestseller;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  @ApiModelProperty("mnemonical identity sequence of characters")
  private String slug;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  @ApiModelProperty("book title")
  private String title;

  @Column(columnDefinition = "VARCHAR(255)")
  @ApiModelProperty("image url")
  private String image;

  @Column(columnDefinition = "TEXT")
  @ApiModelProperty("book description text")
  private String description;

  @Column(name = "price") //, columnDefinition = "INT NOT NULL")
  @JsonProperty("price")
  @ApiModelProperty("book price without discount")
  private int priceOld;

  @Column(name = "discount") //, columnDefinition = "SMALLINT NOT NULL DEFAULT 0")
  @JsonProperty("discount")
  @ApiModelProperty("discount value for book")
  private Double price;

  @Column(name = "b_popularity")
  private int bPopularity;

  @Column(name = "c_popularity")
  private int cPopularity;

  @Column(name = "k_popularity")
  private int kPopularity;

  public int getbPopularity() {
    return bPopularity;
  }

  public void setbPopularity(int bPopularity) {
    this.bPopularity = bPopularity;
  }

  public int getcPopularity() {
    return cPopularity;
  }

  public void setcPopularity(int cPopularity) {
    this.cPopularity = cPopularity;
  }

  public int getkPopularity() {
    return kPopularity;
  }

  public void setkPopularity(int kPopularity) {
    this.kPopularity = kPopularity;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalDateTime getPubDate() {
    return pubDate;
  }

  public void setPubDate(LocalDateTime pubDate) {
    this.pubDate = pubDate;
  }

  public AuthorEntity getAuthor() {
    return author;
  }

  public void setAuthor(AuthorEntity author) {
    this.author = author;
  }

  public short getIsBestseller() {
    return isBestseller;
  }

  public void setIsBestseller(short isBestseller) {
    this.isBestseller = isBestseller;
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getPriceOld() {
    return priceOld;
  }

  public void setPriceOld(int priceOld) {
    this.priceOld = priceOld;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }
}
