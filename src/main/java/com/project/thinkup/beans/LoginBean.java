package com.project.thinkup.beans;


import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;

import com.project.thinkup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.project.thinkup.model.User;

@ManagedBean
@Component
@ApplicationScoped
public class LoginBean {
	User user;
	
    @Autowired
    UserService userService;

    public boolean login(String username, String password) {
        // Buscamos el usuario en la base de datos
        user = userService.getUserByEmail(username);
        boolean userLogged = false;
        // Verificamos que el usuario exista y que la contraseña sea correcta
        if (user != null  && user.getPassword().equals(password)) {
            // Redirigimos al usuario a la página de inicio
            userLogged = true;
        }
        return userLogged;
    }
    
    public User updateUser () {
        return userService.updateUser(user);
    }

    public User getUser() {
    	return user;
    }
}
