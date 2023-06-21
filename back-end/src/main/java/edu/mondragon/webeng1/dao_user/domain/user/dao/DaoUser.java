package edu.mondragon.webeng1.dao_user.domain.user.dao;
import java.util.ArrayList;

import edu.mondragon.webeng1.dao_user.domain.user.model.User;

public interface DaoUser {
    public void insertUser(User user);
    public User loadUser(String username,String password);
    public User loadUser(int userId);
    public ArrayList<User> loadUsers();
}
