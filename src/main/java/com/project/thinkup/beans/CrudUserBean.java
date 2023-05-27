package com.project.thinkup.beans;


import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import com.project.thinkup.service.UserService;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.project.thinkup.model.User;

/**
 * Class that handles CRUD (Create, Read, Update, Delete) operations for users in a database.
 * This class uses the DAO (Data Access Object) design pattern to access the database data
 */

@ManagedBean
@Component
@ApplicationScoped

public class CrudUserBean {

    private List<User> users;
    private User selectedUser;
    private List<User> selectedUsers;
    private List<User> filteredUsers;
    private static final String ANOTHERKEY = "anotherkey";
    private static final String SUCCESS = "Usuario borrado con exito!";
	
    @Autowired
    UserService userService;

    /**
     * Method that gets users from the database after dependency injection
     */
    @PostConstruct
    public void init() {
        this.users = userService.getAllUsers();

    }

    /**
     * Method that obtains the users of the database, used in the update button so that the changes in the DB are shown to the user.
     */
    public void refresh() {
        this.users = userService.getAllUsers();
    }

    /**
     * Method that returns the list of users currently stored in this object.
     * @return A List containing User objects representing the users in the DB.
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * Setter method for the filteredUsers property of this object.
     * @param filteredUsers A List containing User objects representing the users to be filtered.
     */
    public void setFilteredUsers(List<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    /**
     * Getter method for the filteredUsers property of this object.
     * @return A List  containing User objects representing the currently filtered users.
     */
    public List<User> getFilteredUsers() {
        return filteredUsers;
    }

    /**
     * Setter method for the users property of this object.
     * @param users A List containing User objects representing the current users.
     */
    public void setUsers(List<User> users) {
        this.users = users;
    }

    /**
     * Getter method for the selectedUser property of this object.
     * @return A User object representing the selectedUser.
     */
    public User getSelectedUser() {
        return selectedUser;
    }

    /**
     * Setter method for the selectedUser property of this object.
     * @param selectedUser A User object representing the current selectedUser.
     */
    public void setSelectedUser(User selectedUser) {
        this.selectedUser = selectedUser;
    }

    /**
     * Getter method for the selectedUsers property of this object.
     * @return A List containing User objects representing the selectedUsers.
     */
    public List<User> getSelectedUsers() {
        return selectedUsers;
    }

    /**
     * Setter method for the selectedUsers property of this object.
     * @param selectedUsers A List  containing User object representing the current selectedUsers.
     */
    public void setSelectedUsers(List<User> selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

    /**
     * Method that creates a new User object and sets it as the selectedUser property of this object.
     * Called when using the "New" button on the table.
     */
    public void openNew() {
        this.selectedUser = new User();
    }

    /**
     * Method that saves the currently selected User object to the database. 
     * If the user already exists in the database, it updates the existing user. Otherwise, it creates a new user.
     * If the operation is successful, a success message is displayed to the user via the FacesContext object.
     * If the operation fails, an error message is displayed.
     */
    public void saveUser() {
        if (this.selectedUser.getUserId() == null) {
            if(!userService.userExist(selectedUser.getMail())){
                userService.addUser(selectedUser);
                refresh();
                RequestContext.getCurrentInstance().execute("PF('manageuserDialog').hide()");
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario creado con exito","Crear usuario");
                context.addMessage(ANOTHERKEY, msg);               
            }else{
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "El usuario ya existe!","Usuario ya existe");
                context.addMessage(ANOTHERKEY, msg);
            }
        }
        else {
            if(userService.updateUser(selectedUser) != null){
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Usuario actualizado con exito!","Usuario modificado con exito");
                context.addMessage(ANOTHERKEY, msg);

            }else{
                FacesContext context = FacesContext.getCurrentInstance();
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al actualizar","Error al actualizar");
                context.addMessage(ANOTHERKEY, msg);
            }

        }
    }     

    /**
     * Method that deletes the currently selected User object from the database.
     * If the operation is successful, a success message is displayed to the user via the FacesContext object.
     */
    public void deleteUser() {
        try{
            userService.deleteUser(selectedUser.getUserId());
            this.selectedUser = null;
            refresh();
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, SUCCESS, SUCCESS);
            context.addMessage(ANOTHERKEY, msg);

        }catch(Exception e){
            String message = e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
            context.addMessage(ANOTHERKEY, msg);


        }

    }

    /**
     * Method that returns a string to display on the UI button used to delete selected users.
     * The method checks whether there are any selected users using the hasSelectedUsers()
     * @return A string that indicates the number of selected users to delete or just "Eliminar" if there are none.
     */
    public String getDeleteButtonMessage() {
        if (hasSelectedUsers()) {
            int size = this.selectedUsers.size();
            return size > 1 ? size + " Usuarios seleccionados" : "1 usuario seleccionado";
        }
        return "Eliminar";
    }

    /**
    * Method that returns whether there are any selected User objects in the selectedUsers list.
    * @return A boolean value indicating whether there are any selected users.
    */
    public boolean hasSelectedUsers() {
        return this.selectedUsers != null && !this.selectedUsers.isEmpty();
    }

    /**
    * Method that deletes all selected User objects from the users list and the database.
    * It does this by first removing the selected users from the users list using the removeAll() method, 
    * and then calling the deleteUser() method from the userService.
    * It also clears the selectedUsers list and displays a success message to the user.
    * Refreshes the user interface to display the updated list of users,
    * If the operation is successful, a success message is displayed to the user via the FacesContext object.
    */
    public void deleteSelectedUsers() {
        try{
            this.users.removeAll(this.selectedUsers);
            for(User user: this.selectedUsers){
                userService.deleteUser(user.getUserId());
            }
            this.selectedUsers = null;
            refresh();
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuarios borrados con exito!", SUCCESS);
            context.addMessage(ANOTHERKEY, msg);

        }catch(Exception e){
            String message = e.getMessage();
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
            context.addMessage(ANOTHERKEY, msg);
        }
    }
}