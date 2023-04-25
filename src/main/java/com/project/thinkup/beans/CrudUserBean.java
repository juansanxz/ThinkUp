package com.project.thinkup.beans;


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
            userService.addUser(selectedUser);
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User Added", "New User");
            context.addMessage("messages", msg); 
        }
        else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("user Updated"));     
            
        }
    }     

    public void deleteUser() {
        this.users.remove(this.selectedUser);
        this.selectedUsers.remove(this.selectedUser);
        this.selectedUser = null;
        userService.deleteUser(selectedUser.getUserId());
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User Removed", "Error");
        context.addMessage("dt-Users", msg);
    }

    public String getDeleteButtonMessage() {
        if (hasSelectedUsers()) {
            int size = this.selectedUsers.size();
            return size > 1 ? size + " Users selected" : "1 User selected";
        }
        return "Delete";
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
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Users Removed", "Error");
        context.addMessage("dt-Users", msg);
    }
}