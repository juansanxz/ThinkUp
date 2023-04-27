package com.project.thinkup.beans;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.project.thinkup.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.project.thinkup.model.User;


@ManagedBean
@Component
@ApplicationScoped

public class CrudUserBean {

    private List<User> users;
    private User selectedUser;
    private List<User> selectedUsers;
    private List<User> filteredUsers;
	
    @Autowired
    UserService userService;

    @PostConstruct
    public void init() {
        this.users = userService.getAllUsers();

    }

    public void refresh() {
        this.users = userService.getAllUsers();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setFilteredUsers(List<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    public List<User> getFilteredUsers() {
        return filteredUsers;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public User getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    public List<User> getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    public void openNew() {
        this.selectedUser = new User();
    }

    public void saveUser() {
        if (this.selectedUser.getUserId() == null) {
            if(!userService.userExist(selectedUser.getMail())){
                userService.addUser(selectedUser);
                refresh();
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario creado con exito","Crear usuario");
                context.addMessage("anotherkey", msg);
            }else{
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "El usuario ya existe!","Usuario ya existe");
                context.addMessage("anotherkey", msg);
            }
        }
        else {
            if(userService.updateUser(selectedUser) != null){
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario actualizado con exito!","Usuario modificado con exito");
                context.addMessage("anotherkey", msg);

            }else{
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar","Error al actualizar");
                context.addMessage("anotherkey", msg);
            }

        }
    }     

    public void deleteUser() {
        userService.deleteUser(selectedUser.getUserId());
        this.selectedUser = null;
        refresh();
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario borrado con exito!", "Usuario borrado con exito!");
        context.addMessage("anotherkey", msg);
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedUsers()) {
            int size = this.selectedUsers.size();
            return size > 1 ? size + " Usuarios seleccionados" : "1 usuario seleccionado";
        }
        return "Eliminar";
    }

    public boolean hasSelectedUsers() {
        return this.selectedUsers != null && !this.selectedUsers.isEmpty();
    }

    public void deleteSelectedUsers() {
        this.users.removeAll(this.selectedUsers);
        for(User user: this.selectedUsers){
            userService.deleteUser(user.getUserId());
        }
        this.selectedUsers = null;
        refresh();
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuarios borrados con exito!", "Usuario borrado con exito!");
        context.addMessage("anotherkey", msg);
    }

    



}