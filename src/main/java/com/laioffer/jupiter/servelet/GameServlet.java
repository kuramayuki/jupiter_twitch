package com.laioffer.jupiter.servelet;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laioffer.jupiter.entity.Game;
import com.laioffer.jupiter.external.TwitchClient;
import com.laioffer.jupiter.external.TwitchException;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

@WebServlet(name = "GameServlet", urlPatterns = {"/game"})
public class GameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get gameName from request URL.
        String gameName = request.getParameter("game_name");
        TwitchClient client = new TwitchClient();

        // Let the client know the returned data is in JSON format.
        response.setContentType("application/json;charset=UTF-8");
        try {
            // Return the dedicated game information if gameName is provided in the request URL, otherwise return the top x games.
            if (gameName != null) {
                Game game = client.searchGame(gameName);
                System.out.println("xuexue" + game);
                response.getWriter().print(new ObjectMapper().writeValueAsString(game));
            } else {
                response.getWriter().print(new ObjectMapper().writeValueAsString(client.topGames(0)));
            }
        } catch (TwitchException e) {
            throw new ServletException(e);
        }

    }

//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
////        JSONObject jsonRequest = new JSONObject(IOUtils.toString(request.getReader()));
////        String name = jsonRequest.getString("name");
////        String developer = jsonRequest.getString("developer");
////        String releaseTime = jsonRequest.getString("release_time");
////        String website = jsonRequest.getString("website");
////        float price = jsonRequest.getFloat("price");
////
////        System.out.println("Name is: " + name);
////        System.out.println("Developer is: " + developer);
////        System.out.println("Release time is: " + releaseTime);
////        System.out.println("Website is: " + website);
////        System.out.println("Price is: " + price);
////
////        response.setContentType("application/json");
////        JSONObject jsonResponse = new JSONObject();
////        jsonResponse.put("status", "ok");
////        response.getWriter().print(jsonResponse);
//    }
}
