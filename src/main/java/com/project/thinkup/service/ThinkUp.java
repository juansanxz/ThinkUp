package com.project.thinkup.service;

import java.util.HashMap;

import javax.annotation.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario o contraseña es erroneo", "No se");
			context.addMessage("somekey", msg);
			return null;
		}
	}
	
	public String getUserName() {
		return currentUser.getMail();
	}
}
