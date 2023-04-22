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
import com.project.thinkup.service.UserService;

@ManagedBean
@Component
@ApplicationScoped
public class LoginBean {
	
    @Autowired
    UserService userService;

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
        User users = userService.getUserByEmail(username);
        // Verificamos que el usuario exista y que la contraseña sea correcta
        if (users != null  && users.getPassword().equals(password)) {
            // Redirigimos al usuario a la página de inicio
            return "welcome.xhtml";
        } else {        
            // Mantenemos al usuario en la página de login
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario o contraseña es erroneo", "No se");
            context.addMessage("somekey", msg);
            return null;
        }
    }

}
