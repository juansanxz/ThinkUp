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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.ApplicationScope;

import com.project.thinkup.beans.LoginBean;
import com.project.thinkup.model.User;
import com.project.thinkup.model.Comment;
import com.project.thinkup.model.Idea;
import com.project.thinkup.model.KeyWord;
import com.project.thinkup.model.Like;

@ManagedBean
@Component
@ApplicationScope
public class ThinkUp {
	private ArrayList<KeyWord> currentKeyWords;
	private ArrayList<String> stringKeyWords;
	private ArrayList<String> keywordsFilter;

	@Autowired
	LoginBean loginBean;

	User currentUser;
	@Autowired
	IdeaService myIdeaService;
	@Autowired
	UserService myUserService;
	@Autowired
	KeyWordService myKeyWordService;
	@Autowired
	LikeService myLikeService;
	@Autowired
	CommentService myCommentService;
	private int currentIdeaPage;
	private Idea currentIdea;
	private boolean inOrder;
	private boolean filterStatus;
	private boolean filterKeyword;
	private String columnOrder;
	private String orderBy;
	private boolean currentIdeaLike;
	private boolean onProfile;
	private String[] filter;
	public static final String SOMEKEY = "somekey";

	private static final String NOSE = "No se";

	public ThinkUp() {
		currentKeyWords = new ArrayList<>();
		stringKeyWords = new ArrayList<>();
		currentIdeaPage = -1;
		inOrder = false;
		currentIdeaLike = false;
		onProfile = false;
		keywordsFilter = new ArrayList<>();
	}

	@PostConstruct
	private void getFirstIdea() {
		changeIdea("next");
	}

	public String login(String username, String password) {
		if (loginBean.login(username, password)) {
			currentUser = loginBean.getUser();
			resetOrder();
			return "main.xhtml?faces-redirect=true&nocache=" + Math.random();
		} else {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "El usuario o contraseña es erroneo",
					NOSE);
			context.addMessage(SOMEKEY, msg);
			return null;
		}
	}

	public void editIdeaStatus(String newStatus) {
		currentIdea.setStatus(newStatus);
		myIdeaService.updateIdea(currentIdea);
	}

	// Cambia de idea dependiendo si es a la izquierda o a la derecha, y depende si
	// se hace desde el perfil o desde la pagina principal
	public void changeIdea(String way) {
		changeNumberOfPage(way);
		try {
			Page<Idea> ideaPage;
			if (inOrder) {
				ideaPage = getIdeasInOrder();
			} else if (filterStatus || filterKeyword) {
			
				ideaPage = getIdeasFilter();
			} else if ((filterStatus || filterKeyword) && inOrder) {
				ideaPage = getIdeasFilterInOrder();
			} else {
				ideaPage = getIdeasDisordered();
			}
			List<Idea> allIdeas = ideaPage.getContent();
			currentIdea = allIdeas.get(0);
			verifyLiked();
		} catch (Exception e) {
			if (way.equals("next")) {
				currentIdeaPage -= 1;
			} else if (way.equals("back")) {
				currentIdeaPage += 1;
			}
		}
	}

	private Page<Idea> getIdeasFilter() {
		if (filterStatus) {
		
			return myIdeaService.getAllIdeasByStatuses(filter, currentIdeaPage);
		} else {
		
			return myIdeaService.getAllIdeasByKeyword(filter, currentIdeaPage);
		}
	}

	private Page<Idea> getIdeasFilterInOrder() {
		return null;
	}

	// Si el usuario desea reiniciar el orden por el que lo estaba haciendo
	public void resetOrder() {
		if (currentIdeaPage != -1) {
			currentIdeaPage = -1;
		}
		refreshKeywords();
		inOrder = false;
		filterStatus = false;
		filterKeyword = false;
		changeIdea("next");
	}

	// Cuando el cliente desee ordenar las ideas manda la columna por la cual desea
	// y si es asc o desc
	public void orderIdeasBy(String column, String order) {
		if (currentIdeaPage != -1) {
			currentIdeaPage = -1;
		}
		inOrder = true;
		filterKeyword = false;
		filterStatus = false;
		columnOrder = column;
		orderBy = order;
		changeIdea("next");
		RequestContext.getCurrentInstance().execute("PF('popUpOrdenar').hide()");
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Las ideas fueron ordenadas por el criterio seleccionado", "Orden");
		context.addMessage("anotherkey", msg);
	}

	public void filterIdeasBy(String[] typelist, String type) {
		if (currentIdeaPage != -1) {
			currentIdeaPage = -1;
		}
		if (type.equals("estado")) {
			filterStatus = true;
			filterKeyword = false;
			
		} else if (type.equals("keyword")) {
			filterStatus = false;
			filterKeyword = true;
		}

		filter = typelist;
		areThereIdeasWithThatFilter();
		changeIdea("next");
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Las ideas fueron ordenadas por el criterio seleccionado", "Orden");
		context.addMessage("anotherkey", msg);
	}

	public void listkeywordIdeasBy(String type) {
		if (type.isBlank()) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Está vacía la KeyWord",
					"No se");
			context.addMessage("addKeyWord", msg);
		} else {
			keywordsFilter.add(type);
		}
	}

	public List<String> getkeywordlist() {
		return keywordsFilter;
	}

	public void constructFilterIdeas() {
		String[] ideasList = keywordsFilter.toArray(new String[0]);
		filterIdeasBy(ideasList, "keyword");
		refreshKeywords();
	}

	private Page<Idea> getIdeasDisordered() {
		if (onProfile) {
			return myIdeaService.getIdeasPageableByUser(currentIdeaPage, currentUser);
		} else {
			return myIdeaService.getAllIdeasPageable(currentIdeaPage);
		}

	}

	private void refreshKeywords() {
		keywordsFilter = new ArrayList<>();
	}

	private Page<Idea> getIdeasInOrder() {
		if (onProfile) {
			return myIdeaService.getIdeasOrderedByUser(columnOrder, orderBy, currentIdeaPage, currentUser);
		} else {
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

	// Verifica si la currentIdea tiene like del currentUser
	private void verifyLiked() {
		if (myLikeService.getLikeByIdeaUser(currentIdea, currentUser) != null) {
			currentIdeaLike = true;
		} else {
			currentIdeaLike = false;
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
						NOSE);
			context.addMessage(SOMEKEY, msg);
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
						NOSE);
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
	}

	// Para redireccionar a recurso que muestra el perfil, o el main dependiendo en
	// que página está
	public void redirection(String site) throws IOException {
		if(site.equals("Profile")){
			setOnProfile(true);
			FacesContext.getCurrentInstance().getExternalContext().redirect("profile.xhtml?faces-redirect=true&nocache=" + Math.random());
		} else if(site.equals("Stats")){
			setOnProfile(false);
			FacesContext.getCurrentInstance().getExternalContext().redirect("statistics.xhtml");
		} else if(site.equals("Main")){
			setOnProfile(false);
			FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml?faces-redirect=true&nocache=" + Math.random());
		}

		/** if (!onProfile) {
			setOnProfile(true);
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("profile.xhtml?faces-redirect=true&nocache=" + Math.random());
		} else {
			setOnProfile(false);
			FacesContext.getCurrentInstance().getExternalContext().redirect("main.xhtml?faces-redirect=true&nocache=" + Math.random());
			System.out.println("ok");
		} **/
	}

	// Dar like
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void giveLike() {
		if (currentIdeaLike) {
			// lo quiero eliminar
			Like likeToDelete = myLikeService.getLikeByIdeaUser(currentIdea, currentUser);
			myLikeService.deleteLike(likeToDelete.getLikeId());

		} else {
			// lo quiero crear
			Like likeToSet = new Like(currentIdea, currentUser);
			myLikeService.addLike(likeToSet);
		}
		currentIdeaLike = !currentIdeaLike;
	}

	public int getAmountOfLikes() {
		return myLikeService.getLikeByIdea(currentIdea).size();
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

	// Determina si tiene o no ideas el usuario actual, para saber si mostrar o no
	// el panelgrid que se encarga de contenerlas
	public boolean userHasIdeas() {
		boolean userIdea = true;
		if (currentUser.getIdeas().isEmpty()) {
			userIdea = false;
		}
		return userIdea;
	}

	// Determina si hay ideas al buscar por un filtro determinado (estado o palabra clave), para saber si mostrar o no
	// el panelgrid que se encarga de contenerlas
	public void areThereIdeasWithThatFilter() {
		if (filter != null && ((filterStatus && myIdeaService.getAllByKey(filter).isEmpty()) || (filterKeyword && myIdeaService.getAllBysta(filter).isEmpty()))) {
			FacesContext context = FacesContext.getCurrentInstance();
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
				"No hay ideas con el filtro seleccionado", "Orden");
			context.addMessage(SOMEKEY, msg);
		}
	
	}


	// Cuando se da click a boton de perfil, para cambiar atributo onProfile a true
	// Cuando se da click a boton de logo, para cambiar atributo onProfile a false
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

	public List<String> getStringKeyWords() {
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

	public String getStringKeyWordsfilter() {
		String result = "";
		for (int i = 0; i < keywordsFilter.size(); i++) {
			if (i != keywordsFilter.size() - 1) {
				result += keywordsFilter.get(i) + ", ";
			} else {
				result += keywordsFilter.get(i);
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
		if (onProfile) {
			return myIdeaService.getIdeasByUser(currentUser).size();
		} else {
			if (filterKeyword) {
				return myIdeaService.getAllByKey(filter).size();
			}else if(filterStatus){
				return myIdeaService.getAllBysta(filter).size();

			}else{
				return myIdeaService.getAllIdeas().size();
			}
		}
	}

	public boolean getCurrentIdeaLike() {
		return currentIdeaLike;
	}
	public void addComment(String comment) {
		Comment description = new Comment(currentIdea, currentUser, comment);
		myCommentService.addComment(description);
	}

	public String getComments() {
		List<Comment> comments = myCommentService.getCommentByIdeaId(currentIdea);
		return showComments(comments);
	}

	public String showComments(List<Comment> comments) {
        String allComments = "";
        for (Comment comment : comments){
            allComments += comment.toString() + "\n";
        }
        return allComments;
    }

	public List<KeyWord> getAllKeywords() {
		return myKeyWordService.getAllKeyWords();
	}

}
