package com.mcubes.minoxidilbd.configuration.security.client;

import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException, IOException, ServletException {
        super.onAuthenticationFailure(request, response, exception);

        System.out.println("# Auth Ex - : " + exception);
        response.sendRedirect("/client/pages/account/login");
        /*
        if(exception.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
            response.sendRedirect("error1");
        } else if (LockedException.class.isAssignableFrom(exception.getClass())) {
            response.sendRedirect("error2");
        }
         */
    }
}