package com.laioffer.jupiter.servelet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Destroy the session since the user is logged out.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        //below has be handled by tomcat
//        Cookie cookie = new Cookie("JSESSIONID", null);
//        cookie.setPath("/");
//        cookie.setMaxAge(0);
//        response.addCookie(cookie);
    }
}