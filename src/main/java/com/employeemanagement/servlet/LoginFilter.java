package com.employeemanagement.servlet;

import com.employeemanagement.model.User;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {
        "/dashboard",
        "/employees",
        "/departments",
        "/attendance",
        "/my-attendance"
})
public class LoginFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        HttpServletResponse httpResponse =
                (HttpServletResponse) response;

        HttpSession session =
                httpRequest.getSession(false);

        if (session == null ||
                session.getAttribute("loggedInUser") == null) {

            httpResponse.sendRedirect(
                    httpRequest.getContextPath()
                            + "/login.html"
            );

            return;
        }

        User user =
                (User) session.getAttribute("loggedInUser");

        String role = user.getRole();

        String requestURI =
                httpRequest.getRequestURI();

        boolean adminPage =
                requestURI.endsWith("/employees")
                || requestURI.endsWith("/departments")
                || requestURI.endsWith("/attendance");

        if (adminPage &&
                !"ADMIN".equalsIgnoreCase(role)) {

            httpResponse.sendRedirect(
                    httpRequest.getContextPath()
                            + "/dashboard"
            );

            return;
        }

        chain.doFilter(request, response);
    }
}