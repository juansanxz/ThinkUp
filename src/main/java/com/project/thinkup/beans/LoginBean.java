package com.project.thinkup.beans;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.project.thinkup.model.User;
import com.project.thinkup.repository.UserRepository;

@ManagedBean
@Component
@ApplicationScoped
public class LoginBean {
	User user;
	
    @Autowired
    UserRepository userRepository;

    public boolean login(String username, String password) {
        // Buscamos el usuario en la base de datos
        user = userRepository.findByMail(username);
        // Verificamos que el usuario exista y que la contraseña sea correcta
        if (user != null  && user.getPassword().equals(password)) {
            // Redirigimos al usuario a la página de inicio
            return true;
        } else {        
            // Mantenemos al usuario en la página de login
            FacesContext.getCurrentInstance().addMessage("@all", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datos incorrectos", null));
            return false;
        }
    }
    
    public User getUser() {
    	return user;
    }
}
