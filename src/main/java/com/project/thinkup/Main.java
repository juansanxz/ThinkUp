package com.project.thinkup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.webapp.FacesServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.project.thinkup.model.Idea;
import com.project.thinkup.model.KeyWord;
import com.project.thinkup.model.Like;
import com.project.thinkup.model.Topic;
import com.project.thinkup.model.User;
import com.project.thinkup.service.IdeaService;
import com.project.thinkup.service.KeyWordService;
import com.project.thinkup.service.TopicService;
import com.project.thinkup.service.LikeService;
import com.project.thinkup.service.UserService;
import com.project.thinkup.service.CommentService;

@SpringBootApplication
public class Main {

	@Autowired
	UserService myUserService;

	@Autowired
	IdeaService myIdeaService;

	@Autowired
	KeyWordService myKeyWordService;

	@Autowired
	LikeService myLikeService;
  
  @Autowired
	TopicService myTopicService;

	@Autowired
	CommentService myCommentService;

	public static final String ACTIVO = "activo";
	public static final String ESTUDIANTE = "estudiante"; 

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

	}

	@Bean(name = "database")
	public CommandLineRunner run() {
		return (args) -> {
			//Deleting tables's content...
			myIdeaService.deleteAllIdeas();
			myUserService.deleteAllUsers();
			myKeyWordService.deleteAllKeyWords();
			myLikeService.deleteAllLikes();
			myTopicService.deleteAllTopics();
			myCommentService.deleteAllComments();

			//Adding Admon...
			User user = new User("andres", "oñate", "andrescamiloquimbayo@gmail.com", "123", ACTIVO, "user",
					ESTUDIANTE);
			myUserService.addUser(user);

			User user1 = new User("juan", "sanchez", "juansanchez@gmail.com", "123", ACTIVO, "user",
					ESTUDIANTE);
			myUserService.addUser(user1);

			User user2 = new User("Mateo", "Olaya", "mateo.olaya@gmail.com", "123", ACTIVO, "user",
					ESTUDIANTE);
			myUserService.addUser(user2);
			

			User user3 = new User("Santiago", "Arevalo", "santiago.arevalo@gmail.com", "123", ACTIVO, "user",
					ESTUDIANTE);
			myUserService.addUser(user3);

			User user4 = new User("Alejandro", "Huertas", "alejandro.huertas@gmail.com", "123", ACTIVO, "user",
					ESTUDIANTE);
			myUserService.addUser(user4);

			User user5 = new User("Juan", "Poveda", "juan.poveda@gmail.com", "123", ACTIVO, "user",
					ESTUDIANTE);
			myUserService.addUser(user5);

			User user6 = new User("Jorge", "Useche", "jorge.useche@gmail.com", "123", ACTIVO, "user",
					"profesor");
			myUserService.addUser(user6);

			User admon = new User("Administrador", "Administrador", "administrador@gmail.com", "admin123", ACTIVO,
					"admin",
					"administrativo");
			myUserService.addUser(admon);

			//Adding keywords...
			KeyWord keyWord1 = new KeyWord("Redes");
			myKeyWordService.addKeyWord(keyWord1);

			KeyWord keyWord2 = new KeyWord("Ciclos");
			myKeyWordService.addKeyWord(keyWord2);

			KeyWord keyWord3 = new KeyWord("Aupn");
			myKeyWordService.addKeyWord(keyWord3);

			List<KeyWord> keyWords1 = new ArrayList<>();
			keyWords1.add(keyWord1);
			keyWords1.add(keyWord2);

			List<KeyWord> keyWords2 = new ArrayList<>();
			keyWords2.add(keyWord1);
			keyWords2.add(keyWord3);

			List<KeyWord> keyWords3 = new ArrayList<>();
			keyWords3.add(keyWord2);

			//Adding Admon's ideas...
			Idea idea1 = new Idea("Titulo1", "Proyecto de redes", keyWords1);

			Idea idea2 = new Idea("Titulo2", "Proyecto de ciclos", keyWords2);

			Idea idea3 = new Idea("Titulo3", "Proyecto de aupn", keyWords3);

			myIdeaService.addIdea(idea1);
			myIdeaService.addIdea(idea2);
			myIdeaService.addIdea(idea3);


			user.addIdea(idea1);
			user.addIdea(idea2);
			myUserService.updateUser(user);
			idea1.setUser(user);
			myIdeaService.updateIdea(idea1);
			idea2.setUser(user);
			myIdeaService.updateIdea(idea2);

			user1.addIdea(idea3);
			myUserService.updateUser(user1);
			idea3.setUser(user1);
			myIdeaService.updateIdea(idea3);
		

			//Adding new idea and adding to admon1...
			Idea idea4 = new Idea("Titulo4", "Idea adicional", keyWords3);
			myIdeaService.addIdea(idea4);
			user1.addIdea(idea4);
			myUserService.updateUser(user1);
			idea4.setUser(user1);
			myIdeaService.updateIdea(idea4);

			//Giving like to the idea4....
			Like like1 = new Like(idea4, user3);
			myLikeService.addLike(like1);
			user3.giveLike(like1);
			//idea4.giveLike(like1);
			myUserService.updateUser(user3);
			myIdeaService.updateIdea(idea4);

			Topic topic1 = new Topic("Marvel", "Peliculas");
			myTopicService.addTopic(topic1);
			topic1.addIdea(idea4);
			topic1.addIdea(idea2);
			topic1.addIdea(idea3);
			myTopicService.updateTopic(topic1);
			myIdeaService.updateIdea(idea4);
			myIdeaService.updateIdea(idea2);
			myIdeaService.updateIdea(idea3);
		};
	}

	@Bean
	@DependsOn({ "database" })
	ServletRegistrationBean jsfServletRegistration(ServletContext servletContext) {
		// spring boot only works if this is set
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());

		// registration
		ServletRegistrationBean srb = new ServletRegistrationBean<>();
		srb.setServlet(new FacesServlet());
		srb.setUrlMappings(Arrays.asList("*.xhtml"));
		srb.setLoadOnStartup(1);
		return srb;
	}
}
