package com.hotelbooking.hotelbooking.config;

import com.hotelbooking.hotelbooking.exception.MyExceptionHandler;
import com.hotelbooking.hotelbooking.model.AppUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;

@Component
public class RoleCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            AppUser user = (AppUser)(request.getSession().getAttribute("user"));
            String uri = request.getRequestURI();

            // Allow access to public pages regardless of user role
            if(uri.equalsIgnoreCase("/") ||uri.contains("logout")|| uri.contains("/404") || uri.contains("access")|| uri.contains("/landing") || uri.contains("/users/login") || uri.contains("/users/create-account")){
                return true;
            }
            System.err.println("session data -"+user.getEmail());
            System.err.println("------- URL "+uri);

            // Redirect to landing page if user is not logged in


            if(user == null){
                response.sendRedirect("/users/login");
                return false;
            }

            // Check user role and grant access accordingly
            String userRole = user.getRole().getName().toUpperCase();
            if(uri.contains("/admin") || uri.contains("/users/users") || uri.contains("/users/createFinance") || uri.contains("/user/edit")) {
                if(userRole.equalsIgnoreCase("ADMIN")){
                    System.err.println("admin");
                    return true;
                }else{
                    redirectToAccessDenied(response);
                    return false;
                }
            }
            if(uri.contains("/user") || uri.contains("/booking") || uri.contains("/profile/user")) {
                if(userRole.equalsIgnoreCase("USER")){
                    System.err.println("user");
                    return true;
                }else{
                    redirectToAccessDenied(response);
                    return false;
                }
            }
            if(uri.contains("/finance")) {
                if(userRole.equalsIgnoreCase("FINANCE_OFFICER")){
                    System.err.println("finance");
                    return true;
                }else{
                    redirectToAccessDenied(response);
                    return false;
                }
            }else{
                return true;
            }

        } catch (Exception e) {
            redirectToAccessDenied(response);
            return false;
        }
    }
    private void redirectToAccessDenied(HttpServletResponse response) throws IOException {
        response.sendRedirect("/pages/access-denied");
    }
}
