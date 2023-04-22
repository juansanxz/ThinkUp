package com.project.thinkup;

import java.util.Arrays;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;

import com.project.thinkup.model.User;
import com.project.thinkup.service.UserService;

@SpringBootApplication
public class Main {

	@Autowired
    UserService myUserService;


	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);

	}

	@Bean(name = "database")
    public CommandLineRunner run() throws Exception {
        return (args) -> {
            System.out.println("Adding Admon...\n");
            myUserService.addUser(new User("andres","oñate", "andrescamiloquimbayo@gmail.com", "123", "activo", "admon", "sistemas" ));

            System.out.println("\nGetting all User....");
            myUserService.getAllUsers().forEach(configuration -> System.out.println(configuration));
			System.out.println(myUserService.getUserByEmail("andrescamiloquimbayo@gmail.com").getPassword());
        };
    }

	@Bean
	@DependsOn({"database"})
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
