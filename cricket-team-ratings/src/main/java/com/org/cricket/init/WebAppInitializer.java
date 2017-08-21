package com.org.cricket.init;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.context.ContextLoaderListener;

/*Here we will define the configuration (servlet configuration) for WEB.XML. This is done by implementing WebApplicationInitializer*/

public class WebAppInitializer implements WebApplicationInitializer {

	public void onStartup(ServletContext servletContext) throws ServletException {
		// TODO Auto-generated method stub
		
		/*
		 *AnnotationConfigWebApplicationContext is used to create application context for web applications by 
		 *using java clases as input for bean definitions instead of xml files.
	     */
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		/*
		 *Register one or more annotated classes to be processed. In WebAppConfiguration file we are configuring spring related
		 *stuff. We will pass this context (ctx) to dispatcher servlet in the line--> LINE DISPATCHER.
		 */
		ctx.register(WebAppConfiguration.class); 
		servletContext.addListener(new ContextLoaderListener(ctx));
		
		ctx.setServletContext(servletContext);
		
		/*
		 * DispatcherServlet will look for context (ctx) which in turn will look for WebAppConfiguration file to configure spring.
		 */
		Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx)); //LINE DISPATCHER
		servlet.addMapping("/"); //Adds a servlet mapping with the given URL patterns for the Servlet represented by this ServletRegistration.
		
	   /*
		*Sets the loadOnStartup priority on the Servlet represented by this dynamic ServletRegistration. You set load on startup for the 
		*dispatcher servlet so the spring container will be initialized on app server (tomcat etc) startup.
		*/
		
		servlet.setLoadOnStartup(1); 
	
	}

}
