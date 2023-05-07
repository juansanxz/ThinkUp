package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.KeyWord;
import com.project.thinkup.model.User;
import com.project.thinkup.service.IdeaService;
import com.project.thinkup.service.ThinkUp;
import com.project.thinkup.service.UserService;

public class Test {
	
	@Before
	public void setUp() {	
	}
	
	@Test
	public void debeHacerLogin() {
		LoginController loginController = new LoginController();
		String result = loginController.login("username", "password");
		assertEquals("main.xhtml?faces-redirect=true", result);
	}
	
	@Test
	public void debeAñadirPalabrasClave() {
		ThinkUp thinkUp = new ThinkUp();
		List<String> stringKeyWords = new ArrayList<>();
		ThinkUp.setStringKeyWords(stringKeyWords);

		ThinkUp thinkUp2 = new ThinkUp();
		thinkUp2.addStringKeyWord("example");
		assertEquals(1, stringKeyWords.size());
		assertEquals("example", stringKeyWords.get(0));

		thinkUp.addStringKeyWord("");
		assertEquals(0, stringKeyWords.size());
	}
	
	@Test
	public void debeAñadirUsuario() {
		UserService userService = new UserService(null);

		User user = new User("juan", "poveda", "juan.poveda@gmail.com", "password", "Creada", "admon", "sistemas");
		User result = userService.addUser(user);

		assertEquals(user, result);
	}
	
	@Test
	public void debeCambiarNumerodePagina() {
		ThinkUp thinkUp = new ThinkUp();
		int currentIdeaPage = 0;
		thinkUp.setCurrentIdeaPage(currentIdeaPage);

	}
	
	@Test
	public void debePublicarIdea() {
	    List<String> keyWords = Arrays.asList("keyword1", "keyword2", "keyword3");
	    List<String> currentKeyWords;
		currentKeyWords.clear();
	    List<String> stringKeyWords;
		stringKeyWords.clear();
	    publishAnIdea("Titulo","Descripción de la idea");
	    String idea = idea.getTitle();
	    assertNotNull(idea);
	    assertEquals("Descripción de la idea", idea.getDescription());
	    assertEquals(keyWords.size(), idea.getKeyWords().size());
	}
	

}