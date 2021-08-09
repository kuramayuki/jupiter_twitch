package com.laioffer.jupiter.servelet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laioffer.jupiter.db.MySQLConnection;
import com.laioffer.jupiter.entity.LoginRequestBody;
import com.laioffer.jupiter.entity.LoginResponseBody;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        LoginRequestBody body = mapper.readValue(request.getReader(), LoginRequestBody.class);
        LoginRequestBody body = ServletUtil.readRequestBody(LoginRequestBody.class, request);
        if (body == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        MySQLConnection connection = new MySQLConnection();
        String userId = body.getUserId();
        String password = ServletUtil.encryptPassword(body.getUserId(), body.getPassword());
        String userName = connection.verifyLogin(userId, password);
        connection.close();

        if (userName.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            //create session
            HttpSession session = request.getSession();
            session.setAttribute("user_id", body.getUserId());
            session.setAttribute("user_name", userName);
            session.setMaxInactiveInterval(600);

            LoginResponseBody loginResponseBody = new LoginResponseBody(body.getUserId(), userName);
            response.setContentType("application/json;charset=UTF-8");
            ObjectMapper mapper = new ObjectMapper();
            response.getWriter().print(new ObjectMapper().writeValueAsString(loginResponseBody));
        }
    }
}
