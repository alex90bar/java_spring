package com.example.MyBookShopApp.security;

import static org.junit.jupiter.api.Assertions.*;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class BookStoreUserRegisterTest {

  private final BookStoreUserRegister bookStoreUserRegister;
  private final PasswordEncoder passwordEncoder;
  private RegistrationForm registrationForm;

  @MockBean
  private BookstoreUserRepository bookstoreUserRepositoryMock;

  @Autowired
  public BookStoreUserRegisterTest(
      BookStoreUserRegister bookStoreUserRegister,
      PasswordEncoder passwordEncoder) {
    this.bookStoreUserRegister = bookStoreUserRegister;
    this.passwordEncoder = passwordEncoder;
  }

  @BeforeEach
  void setUp() {
    registrationForm = new RegistrationForm();
    registrationForm.setEmail("test@mail.org");
    registrationForm.setName("Tester");
    registrationForm.setPass("qddqd");
    registrationForm.setPhone("79031234567");
  }

  @AfterEach
  void tearDown() {
    registrationForm = null;
  }

  @Test
  void registerNewUser() {
    BookstoreUser user = bookStoreUserRegister.registerNewUser(registrationForm);
    assertNotNull(user);
    assertTrue(passwordEncoder.matches(registrationForm.getPass(), user.getPassword()));
    assertTrue(CoreMatchers.is(user.getPhone()).matches(registrationForm.getPhone()));
    assertTrue(CoreMatchers.is(user.getName()).matches(registrationForm.getName()));
    assertTrue(CoreMatchers.is(user.getEmail()).matches(registrationForm.getEmail()));

    Mockito.verify(bookstoreUserRepositoryMock, Mockito.times(1))
        .save(Mockito.any(BookstoreUser.class));
  }

  @Test
  void registerNewUserFail(){
    Mockito.doReturn(new BookstoreUser())
        .when(bookstoreUserRepositoryMock)
        .findBookstoreUserByEmail(registrationForm.getEmail());

    BookstoreUser user = bookStoreUserRegister.registerNewUser(registrationForm);
    assertNull(user);
  }
}