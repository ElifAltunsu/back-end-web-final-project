package edu.mondragon.webeng1.dao_user.controller;

import java.io.IOException;
import java.util.Optional;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import edu.mondragon.webeng1.dao_user.domain.user.dao.UserFacade;
import edu.mondragon.webeng1.dao_user.domain.user.model.User;

@WebServlet(name = "LoginController", urlPatterns = { "/login" })
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginController() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get parameters from the request
        String action = Optional.ofNullable(request.getParameter("action")).orElse("logout");
        String username = Optional.ofNullable(request.getParameter("username")).orElse("");
        String password = Optional.ofNullable(request.getParameter("password")).orElse("");
        HttpSession session = request.getSession(true);

        System.out.println("Username: " + username);
        System.out.println("Password: " + password
                + " (This should never be done in real projects! Printing passwords in logs is a bad practice.)");

        switch (action) {
            case "login" -> login(session, username, password);
            default -> logout(session);
        }
        response.sendRedirect("index.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private void login(HttpSession session, String username, String password) {
        User user;

        // Check if the user exists in the database
        UserFacade uf = new UserFacade();
        user = uf.loadUser(username, password);

        // Save login result in session
        if (user != null) {
            session.setAttribute("user", user);
            session.setAttribute("message", "Successfully logged!");
        } else {
            session.removeAttribute("user");
            session.setAttribute("wrongUsername", username);
            session.setAttribute("error", "Wrong username or password.");
        }
    }

    private void logout(HttpSession session) {
        session.removeAttribute("user");
        session.setAttribute("message", "You logged out.");
    }

}
