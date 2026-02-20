package com.example.friendfinder.config.filters;

import com.example.friendfinder.model.Role;
import com.example.friendfinder.repo.RoleRepo;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.example.friendfinder.config.JwtHandler;
@Component
public class AuthFilter extends OncePerRequestFilter {
    private final JwtHandler jwtHandler;
    private final RoleRepo roleRepo;

    @Autowired
    public AuthFilter(JwtHandler jwtHandler, RoleRepo roleRepo) {
        this.roleRepo = roleRepo;
        this.jwtHandler = jwtHandler;
    }

    @Override

    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        try {
            String header = request.getHeader("Authorization");

            if (header == null || !header.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            String token = header.substring(7);
            var userDto = jwtHandler.validateJwtToken(token);

            if (userDto != null &&
                    SecurityContextHolder.getContext().getAuthentication() == null) {

                // ⚠ DB hit هنا
                List<Role> dbRoles = roleRepo.findAllById(userDto.getRolesIds());

                var authorities = dbRoles.stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                        .toList();

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDto,
                                null,
                                authorities
                        );

                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }



    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();

        System.out.println("🔍 Checking if should filter: {}"+path);

        // قائمة المسارات التي لا تحتاج فلترة
        boolean shouldSkip = path.startsWith("/auth/") ||
                path.equals("/auth") ||
                path.startsWith("/assets/") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/configuration") ||
                path.startsWith("/webjars") ||
                path.equals("/favicon.ico") ||
                path.equals("/error");

        System.out.println("🔍 Should skip filter: {}"+ shouldSkip);
        return shouldSkip;
    }
}

