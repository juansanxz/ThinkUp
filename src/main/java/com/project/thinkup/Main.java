package com.project.thinkup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.webapp.FacesServlet;
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
import com.project.thinkup.model.User;
import com.project.thinkup.service.IdeaService;
import com.project.thinkup.service.KeyWordService;
import com.project.thinkup.service.UserService;

@SpringBootApplication
public class Main {

	@Autowired
	UserService myUserService;

	@Autowired
	IdeaService myIdeaService;

	@Autowired
	KeyWordService myKeyWordService;

	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

	}

	@Bean(name = "database")
	public CommandLineRunner run() throws Exception {
		return (args) -> {
			System.out.println("Deleting tables's content...\n");
			myIdeaService.deleteAllIdeas();
			myUserService.deleteAllUsers();
			myKeyWordService.deleteAllKeyWords();

			System.out.println("Adding keywords...\n");
			KeyWord keyWord1 = new KeyWord("Redes");
			myKeyWordService.addKeyWord(keyWord1);

			KeyWord keyWord2 = new KeyWord("Ciclos");
			myKeyWordService.addKeyWord(keyWord2);

			KeyWord keyWord3 = new KeyWord("Aupn");
			myKeyWordService.addKeyWord(keyWord3);

			List<KeyWord> keyWords1 = new ArrayList<KeyWord>();
			keyWords1.add(keyWord1);
			keyWords1.add(keyWord2);

			List<KeyWord> keyWords2 = new ArrayList<KeyWord>();
			keyWords2.add(keyWord1);
			keyWords2.add(keyWord3);

			List<KeyWord> keyWords3 = new ArrayList<KeyWord>();
			keyWords3.add(keyWord2);

			System.out.println("Adding Admon's ideas...\n");
			Idea idea1 = new Idea("Activa", "Proyecto de redes", keyWords1);

			Idea idea2 = new Idea("En proceso", "Proyecto de ciclos", keyWords2);

			Idea idea3 = new Idea("Activa", "Proyecto de aupn", keyWords3);

			List<Idea> ideas1 = new ArrayList<Idea>();
			ideas1.add(idea1);
			ideas1.add(idea2);
			ideas1.add(idea3);

			List<Idea> ideas2 = new ArrayList<Idea>();
			ideas2.add(idea1);

			List<Idea> ideas3 = new ArrayList<Idea>();
			ideas3.add(idea2);
			ideas3.add(idea3);

			myIdeaService.addIdea(idea1);
			myIdeaService.addIdea(idea2);
			myIdeaService.addIdea(idea3);

			System.out.println("Adding Admon...\n");
			User admon = new User("andres", "oñate", "andrescamiloquimbayo@gmail.com", "123", "activo", "admon",
					"sistemas", ideas1);
			myUserService.addUser(admon);

			User admon1 = new User("juan", "sanchez", "juansanchez@gmail.com", "123", "activo", "admon",
					"sistemas", ideas2);
			myUserService.addUser(admon1);

			System.out.println("\nGetting all Users....");
			myUserService.getAllUsers().forEach(configuration -> System.out.println(configuration));

			System.out.println("\nGetting all ideas....");
			myIdeaService.getAllIdeas().forEach(item -> System.out.println(item));

			System.out.println("\nGetting ideas with active status....");
			myIdeaService.getAllIdeasByStatus("Activa").forEach(item -> System.out.println(item));

			System.out.println("\nGetting ideas by user....");
		};
	}

	@Bean
	@DependsOn({ "database" })
	ServletRegistrationBean jsfServletRegistration(ServletContext servletContext) {
		// spring boot only works if this is set
		servletContext.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());

		// registration
		ServletRegistrationBean srb = new ServletRegistrationBean();
		srb.setServlet(new FacesServlet());
		srb.setUrlMappings(Arrays.asList("*.xhtml"));
		srb.setLoadOnStartup(1);
		return srb;
	}
}
