package io.github.ripetangerine.todolist.security.jwt;

import io.github.ripetangerine.todolist.security.SecurityConstants;
import io.github.ripetangerine.todolist.security.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * Created on Ağustos, 2020
 *
 * @author Faruk
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenManager jwtTokenManager;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String username = null;
        String authToken = null;
        String token = null;

        String path = request.getRequestURI();

        // 🔥 여기 추가
        if (path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/images/")
                || path.startsWith("/error")
                || path.startsWith("/layout/")
                || path.startsWith("/fragment/")) {
            chain.doFilter(request, response);
            return;
        }

        if (request.getCookies() != null) {
            token = Arrays.stream(request.getCookies())
                    .filter(c -> c.getName().equals("accessToken"))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }

        if (token == null) {
            chain.doFilter(request, response);
            return;
        }

        try {
            username = jwtTokenManager.getUsernameFromToken(token);
        }
        catch (Exception e) {
            log.error("Authentication Exception : {}", e.getMessage());
            chain.doFilter(request, response);
            return;
        }

        final SecurityContext securityContext = SecurityContextHolder.getContext();

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }

        final boolean canBeStartTokenValidation = Objects.nonNull(username) && Objects.isNull(securityContext.getAuthentication());

        if (!canBeStartTokenValidation) {
            chain.doFilter(request, response);
            return;
        }
        //

        UserDetails user = userDetailsService.loadUserByUsername(username);

        if (!jwtTokenManager.validateToken(token, user.getUsername())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken auth =
                new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                );

        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(auth);


//        final UserDetails user = userDetailsService.loadUserByUsername(username);
//        final boolean validToken = jwtTokenManager.validateToken(token, user.getUsername());
//
//        if (!validToken) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        securityContext.setAuthentication(authentication);

        log.info("Authentication successful. Logged in username : {} ", username);

//        chain.doFilter(request, response);
    }
}