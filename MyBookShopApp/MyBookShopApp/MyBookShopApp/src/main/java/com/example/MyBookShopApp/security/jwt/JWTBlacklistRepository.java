package com.example.MyBookShopApp.security.jwt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JWTBlacklistRepository extends JpaRepository<JWTBlackListEntity, Integer> {

  JWTBlackListEntity findJWTBlackListEntityByToken(String token);

}
