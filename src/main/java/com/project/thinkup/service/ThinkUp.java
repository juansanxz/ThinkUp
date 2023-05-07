package com.project.thinkup.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.data.domain.Page;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import com.project.thinkup.beans.LoginBean;
import com.project.thinkup.model.User;
import com.project.thinkup.model.Idea;
import com.project.thinkup.model.KeyWord;

@ManagedBean
@Component
@ApplicationScope
public class ThinkUp {

	// Preguntar si la aplicación la pueden estar usando al tiempo varias personas,
	// si si, creo que toca quitar los contenedores de users y de ideas
	// y que todo se haga a través de consultas a la DB, sino si se pueden dejar
	// para disminuir la cantidad de consultas a la DB
	private ArrayList<KeyWord> currentKeyWords;
	private ArrayList<String> stringKeyWords;

	@Autowired
	LoginBean loginBean;

	User currentUser;
	@Autowired
	IdeaService myIdeaService;
	@Autowired
	UserService myUserService;
	@Autowired
	KeyWordService myKeyWordService;
	private int currentIdeaPage;
	private Idea currentIdea;
	private boolean inOrder;
	private String columnOrder;
	private String orderBy;
	private boolean onProfile;

	public ThinkUp() {
		currentKeyWords = new ArrayList<KeyWord>();
		stringKeyWords = new ArrayList<String>();
		currentIdeaPage = -1;
		inOrder = false;
		onProfile = false;
	}

	@PostConstruct
	private void getFirstIdea() {
		changeIdea("next");
	}

	public String login(String username, String password) {
		if (loginBean.login(username, password)) {
			currentUser = loginBean.getUser();
			return "main.xhtml?faces-redirect=true";
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario o contraseña es erroneo",
					"No se");
			context.addMessage("somekey", msg);
			return null;
		}
	}

	public void editIdeaStatus(String newStatus) {
		currentIdea.setStatus(newStatus);
		myIdeaService.updateIdea(currentIdea);
		// currentIdeaPage -= 1;
		// changeIdea("next");
	}

	// Cambia de idea dependiendo si es a la izquierda o a la derecha, y depende si
	// se hace desde el perfil o desde la pagina principal
	public void changeIdea(String way) {
		changeNumberOfPage(way);
		try {
			// Page<Idea> ideaPage = myIdeaService.getAllIdeasPageable(currentIdeaPage);
			Page<Idea> ideaPage;
			if (inOrder == true) {
				ideaPage = getIdeasInOrder();
			} else {
				ideaPage = getIdeasDisordered();
			}

			List<Idea> allIdeas = ideaPage.getContent();
			currentIdea = allIdeas.get(0);
		} catch (Exception e) {
			if (way.equals("next")) {
				currentIdeaPage -= 1;
			} else if (way.equals("back")) {
				currentIdeaPage += 1;
			}
		}
	}

	// Si el usuario desea reiniciar el orden por el que lo estaba haciendo
	public void resetOrder() {
		if (currentIdeaPage != -1) {
			currentIdeaPage = -1;
		}
		inOrder = false;
		changeIdea("next");
	}

	// Cuando el cliente desee ordenar las ideas manda la columna por la cual desea
	// y si es asc o desc
	public void orderIdeasBy(String column, String order) {
		if (currentIdeaPage != -1) {
			currentIdeaPage = -1;
		}
		inOrder = true;
		columnOrder = column;
		orderBy = order;
		changeIdea("next");
		RequestContext.getCurrentInstance().execute("PF('popUpOrdenar').hide()");
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Las ideas fueron ordenadas por el criterio seleccionado", "Orden");
		context.addMessage("anotherkey", msg);
	}

	private Page<Idea> getIdeasDisordered() {
		if(onProfile){
			return myIdeaService.getIdeasPageableByUser(currentIdeaPage, currentUser);
		} else{
			return myIdeaService.getAllIdeasPageable(currentIdeaPage);
		}
		
	}

	private Page<Idea> getIdeasInOrder() {
		if(onProfile){
			return myIdeaService.getIdeasOrderedByUser(columnOrder, orderBy, currentIdeaPage, currentUser);
		} else{
			return myIdeaService.getAllIdeasOrdered(columnOrder, orderBy, currentIdeaPage);
		}
	}

	// Cambia el numero de la página dependiendo si fue next o back
	private void changeNumberOfPage(String way) {
		if (way.equals("next")) {
			currentIdeaPage += 1;
		} else if (way.equals("back")) {
			currentIdeaPage -= 1;
		}
	}

	// Se debe usar cada vez que el usuario agregue una keyword a la idea
	private void addKeyWord(String keyWord) {
		// Mirar si ya existe la keyword para de una vez agregarlo al arreglo y no
		// meterlo a la DB
		KeyWord addKeyWord = myKeyWordService.getKeyWord(keyWord);

		// Si no existe la keyword entonces se crea una y sa agrega a la DB
		if (addKeyWord == null) {
			addKeyWord = myKeyWordService.addKeyWord(new KeyWord(keyWord));
		}

		// Se agrega al arreglo de las keywords actuales
		currentKeyWords.add(addKeyWord);
	}

	// Solo se le manda la descripción porque cuando el usuario vaya creando las
	// ideas las va agregando en stringKeyWords
	public void publishAnIdea(String title, String description) {
		// Antes de ponerse a agregar cosas valida que la información no esté vacía
		if (title.isBlank() || description.isBlank() || stringKeyWords.isEmpty()) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falta información",
					"No se");
			context.addMessage("somekey", msg);
		} else {
			// Agregar las keyword a la base de datos
			for (String keyWord : stringKeyWords) {
				addKeyWord(keyWord);
			}

			// Creación de ideas
			Idea ideaToAdd = new Idea(title, description, currentKeyWords);
			ideaToAdd.setUser(currentUser);

			// Agregando idea a la base de datos
			myIdeaService.addIdea(ideaToAdd);

			// Agregando idea al usuario que la creó
			currentUser.addIdea(ideaToAdd);

			// Actualizando el registro del usuario en la base de datos para que la idea
			// dirija al usuario
			loginBean.updateUser();

			// Vaciar el arreglo de keywords actual para que así la próxima idea lo inicie
			// limpio
			currentKeyWords.clear();
			stringKeyWords.clear();
		}
	}

	public void addStringKeyWord(String stringKeyWord) {
		if (stringKeyWord.isBlank()) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Está vacía la KeyWord",
					"No se");
			context.addMessage("addKeyWord", msg);
		} else {
			stringKeyWords.add(stringKeyWord);
		}
	}

	// Cierra sesion
	public void logOut() {
		currentUser = null;
		stringKeyWords.clear();
		currentKeyWords.clear();
		resetOrder();
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.invalidateSession();
		try {
			externalContext.redirect("index.html");
		} catch (Exception e) {
		}
		setOnProfile(false);

		// return null;
	}

	// Para redireccionar a recurso que muestra el perfil, o el main dependiendo en que página está
	public void redirection() throws IOException{
		if(!onProfile) {
			setOnProfile(true);
			FacesContext.getCurrentInstance().getExternalContext().redirect("profile.xhtml");
		} else {
			setOnProfile(false);
			FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml");
		}
		
	}

	public String getUserName() {
		return currentUser.getMail();
	}

	public User getCurrentUser() {
		return currentUser;
	}



	public Idea getCurrentIdea() {
		return currentIdea;
	}

	public boolean isAdminCurrentUser() {
		return currentUser.isAdmin();
	}

	//Determina si tiene o no ideas el usuario actual, para saber si mostrar o no  
	//el panelgrid que se encarga de contenerlas
	public boolean userHasIdeas(){
		if(currentUser.getIdeas().size()==0){
			return false;
		}
		return true;
	}

	
	//Cuando se da click a boton de perfil, para cambiar atributo onProfile a true
	//Cuando se da click a boton de logo, para cambiar atributo onProfile a false
	public void setOnProfile(boolean onProfile) {
		this.onProfile = onProfile;
		resetOrder();
	}
	
	public boolean isOnProfile() {
		return onProfile;
	}

	public String getCurrentIdeaTitle() {
		return currentIdea.getTitle();
	}

	public ArrayList<String> getStringKeyWords() {
		return stringKeyWords;
	}

	public String getStringKeyWordsNice() {
		String result = "";
		for (int i = 0; i < stringKeyWords.size(); i++) {
			if (i != stringKeyWords.size() - 1) {
				result += stringKeyWords.get(i) + ", ";
			} else {
				result += stringKeyWords.get(i);
			}
		}
		return result;
	}

	public int getCurrentIdeaPage() {
		return currentIdeaPage;
	}

	public int getCurrentIdeaPageToShow() {
		return currentIdeaPage + 1;
	}

	public void setCurrentIdeaPage(int currentIdeaPage) {
		this.currentIdeaPage = currentIdeaPage;
	}

	public int getAmountOfIdeas() {
		if(onProfile) { 
			return myIdeaService.getIdeasByUser(currentUser).size();
		} else {
			return myIdeaService.getAllIdeas().size();
		}
		
	}

}
