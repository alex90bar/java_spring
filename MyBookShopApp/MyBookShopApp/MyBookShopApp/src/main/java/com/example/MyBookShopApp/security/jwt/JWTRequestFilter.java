package com.example.MyBookShopApp.security.jwt;

import com.example.MyBookShopApp.security.AuthUserController;
import com.example.MyBookShopApp.security.BookstoreUserDetails;
import com.example.MyBookShopApp.security.BookstoreUserDetailsService;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.logging.Logger;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

  private final BookstoreUserDetailsService bookstoreUserDetailsService;
  private final JWTUtil jwtUtil;

  public JWTRequestFilter(
      BookstoreUserDetailsService bookstoreUserDetailsService,
      JWTUtil jwtUtil) {
    this.bookstoreUserDetailsService = bookstoreUserDetailsService;
    this.jwtUtil = jwtUtil;
  }


  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {

    String token = null;
    String username = null;
    Cookie[] cookies = httpServletRequest.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals("token")) {
          token = cookie.getValue();

          //Обработка security-исключения истечения срока действия Jwt-токена
          try {
            username = jwtUtil.extractUsername(token);
          } catch (ExpiredJwtException ex) {
            Logger.getLogger(this.getClass().getSimpleName())
                .warning("Unfortunately expired JWT Token..." + ex.getLocalizedMessage());
            HttpSession session = httpServletRequest.getSession();
            SecurityContextHolder.clearContext();
            if (session != null) {
              session.invalidate();
            }
            cookie.setValue("");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            httpServletResponse.addCookie(cookie);
          }

        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
          BookstoreUserDetails userDetails =
              (BookstoreUserDetails) bookstoreUserDetailsService.loadUserByUsername(username);
          if (jwtUtil.validateToken(token, userDetails)) {
            UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
          }
        }
      }
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
