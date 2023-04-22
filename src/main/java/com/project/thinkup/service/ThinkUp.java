package com.project.thinkup.service;

import java.util.HashMap;

import javax.annotation.ManagedBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.project.thinkup.beans.LoginBean;
import com.project.thinkup.model.User;

@ManagedBean
@Component
public class ThinkUp {
	
	private HashMap<String, User> users;
	@Autowired
	LoginBean loginBean;
	User currentUser;
	
	public ThinkUp () {
		users = new HashMap<String, User>();
	}
	
	public String login (String username, String password) {
		if (loginBean.login(username, password)) {
			currentUser = loginBean.getUser();
			return "welcome.xhtml";
		} else {
			return "hOLA";
		}
	}
	
	public String getUserName() {
		return currentUser.getMail();
	}
}
