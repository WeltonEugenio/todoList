package br.com.weltonfaria.todolist.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.weltonfaria.todolist.user.IUsersRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

@Autowired
    private IUsersRepository usersRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var servletPath = request.getServletPath();
        
        if(servletPath.startsWith("/tasks/")) {
            String authorization = request.getHeader("Authorization");
            String userName = null;
            String password = null;

            // obtendo as informações do header Authorization do usuario e senha
            if (authorization == null || !authorization.toLowerCase().startsWith("basic ")) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
                return;
            }

            try {
                String authEncoded = authorization.substring(6).trim();
                byte[] authDecode = java.util.Base64.getDecoder().decode(authEncoded);
                String authString = new String(authDecode, StandardCharsets.UTF_8);
                String[] parts = authString.split(":", 2);
                if (parts.length != 2) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization format");
                    return;
                }
                userName = parts[0];
                password = parts[1];
            } catch (IllegalArgumentException ex) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid base64 in Authorization header");
                return;
            }
       
            // validar usuario
            var user = this.usersRepository.findByUsername(userName);
            if (user == null){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                return;
            } else {
                // validar a senha (opcional aqui — apenas log)
                var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
                if (passwordVerify.verified) {
                    request.setAttribute("idUser", user.getId());
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid password");
                    return;
                }   
            }

        }else{
            filterChain.doFilter(request, response);
        }
            
    }

}
