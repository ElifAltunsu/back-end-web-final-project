package edu.mondragon.webeng1.dao_user.domain.user.dao;

import java.util.ArrayList;

import edu.mondragon.webeng1.dao_user.domain.user.model.User;

public class UserFacade {
	DaoUser daoUser = null;
	
	public UserFacade(){
		daoUser = new DaoUserMySQL();
	}
	public User loadUser(String username, String password){
		return daoUser.loadUser(username, password);
	}
	public User loadUser(int userId){
		return daoUser.loadUser(userId);
	}
	public ArrayList<User> loadUsers(){
		return daoUser.loadUsers();
	}

}
