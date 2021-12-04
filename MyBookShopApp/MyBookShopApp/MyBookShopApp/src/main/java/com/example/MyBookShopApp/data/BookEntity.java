package com.example.MyBookShopApp.data;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class BookEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(columnDefinition = "TIMESTAMP NOT NULL")
  private LocalDateTime pubDate;

  @Column(columnDefinition = "SMALLINT NOT NULL")
  private short isBestseller;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String slug;

  @Column(columnDefinition = "VARCHAR(255) NOT NULL")
  private String title;

  @Column(columnDefinition = "VARCHAR(255)")
  private String image;

  @Column(columnDefinition = "TEXT")
  private String description;

  @Column(columnDefinition = "INT NOT NULL")
  private int price;

  @Column(columnDefinition = "SMALLINT NOT NULL  DEFAULT 0")
  private short discount;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public LocalDateTime getPubDate() {
    return pubDate;
  }

  public void setPubDate(LocalDateTime pubDate) {
    this.pubDate = pubDate;
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

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public short getDiscount() {
    return discount;
  }

  public void setDiscount(short discount) {
    this.discount = discount;
  }
}
