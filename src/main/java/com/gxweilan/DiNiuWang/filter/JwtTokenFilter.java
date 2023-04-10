//package com.gxweilan.DiNiuWang.filter;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import cn.hutool.jwt.Claims;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.MalformedJwtException;
//import io.jsonwebtoken.UnsupportedJwtException;
//import org.springframework.web.filter.OncePerRequestFilter;
//
///**
// * @author 墨杨清竹
// * @version 1.0
// * @date 2023/2/10 9:10
// */
//public class JwtTokenFilter extends OncePerRequestFilter {
//
//    private final String HEADER = "Authorization";
//    private final String PREFIX = "Bearer ";
//    private final String SECRET = "mySecretKey";
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
//            throws ServletException, IOException {
//        try {
//            if (checkJWT(request, response)) {
//                Claims claims = validateToken(request);
//                if (claims.get("authorities") != null) {
//                    setUpSpringAuthentication(claims);
//                } else {
//                    SecurityContextHolder.clearContext();
//                }
//            }
//            chain.doFilter(request, response);
//        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException e) {
//            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
//            return;
//        }
//    }
//
//    private Claims validateToken(HttpServletRequest request) {
//        String jwtToken = request.getHeader(HEADER).replace(PREFIX, "");
//        return Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(jwtToken).getBody();
//    }
//
//    /**
//     * Authentication method in Spring flow
//     *
//     * @param claims
//     */
//    private void setUpSpringAuthentication(Claims claims) {
//        @SuppressWarnings("unchecked")
//        List<String> authorities = (List<String>) claims.get("authorities");
//
//        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(claims.getSubject(), null,
//                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
//        SecurityContextHolder.getContext().setAuthentication(auth);
//    }
//
//    private boolean checkJWT(HttpServletRequest request, HttpServletResponse res) {
//        String authenticationHeader = request.getHeader(HEADER);
//        if (authenticationHeader == null || !authenticationHeader.startsWith(PREFIX)) {
//            return false;
//        }
//        return true;
//    }
//
//}
