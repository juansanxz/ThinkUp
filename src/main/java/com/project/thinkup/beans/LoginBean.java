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
	
    @Autowired
    UserRepository userRepository;

    private String username;
    private String password;

    // getters y setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        // Buscamos el usuario en la base de datos
        List<User> users = userRepository.findByMail(username);
        // Verificamos que el usuario exista y que la contraseña sea correcta
        if (users != null  && users.get(0).getPassword().equals(password)) {
            // Redirigimos al usuario a la página de inicio
            return "welcome.xhtml";
        } else {        
            // Mantenemos al usuario en la página de login
            FacesContext.getCurrentInstance().addMessage("@all", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Datos incorrectos", null));
            return "hOLA";
        }
    }
}
