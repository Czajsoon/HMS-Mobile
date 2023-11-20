package hotel.management.system.be.configuration.security;

import hotel.management.system.be.application.exception.BadRequestException;
import hotel.management.system.be.configuration.security.dto.AccountPrincipal;
import hotel.management.system.be.configuration.security.infrastructure.AccountDetailsServicePort;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The type Jwt request filter.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter implements SecurityFilterChain {

    private final AccountDetailsServicePort userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        final String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            String jwtToken = requestTokenHeader.substring(7);
            try {
                String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    AccountPrincipal accountPrincipal = userDetailsService.loadUserByUsername(username);
                    if (jwtTokenUtil.validateToken(jwtToken, accountPrincipal)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                accountPrincipal, null, accountPrincipal.getAuthorities());
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Pozyskanie tokena nie powiodło się.");
            } catch (ExpiredJwtException e) {
                throw new BadRequestException("Token wygasł.");
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        return false;
    }

    @Override
    public List<Filter> getFilters() {
        return List.of(this);
    }
}
