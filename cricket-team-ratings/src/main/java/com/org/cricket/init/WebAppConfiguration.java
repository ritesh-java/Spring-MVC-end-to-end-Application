package com.org.cricket.init;

/*
 * THIS CLASS IS SAME AS DEFINING SPRING.XML FILE ALONG WITH HIBERNATE.CFG.XML FILE.
 * HIBERNATE.CFG.XML FILE IS USED TO DEFINE THE DATABASE PROEPRTIES, HIBERNATE PROPERTIES AND MAPPING ENTITY CLASSES. 
 */


import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

/*
 * @Configuration - Indicates that a class declares one or more @Bean methods and may be processed by 
 * the Spring container to generate bean definitions and service requests for those beans at runtime.
 * 
 *  @ComponentScan - Configures component scanning directives for use with @Configuration classes. 
 *  Provides support parallel with Spring XML's <context:component-scan> element. 
 *  
 *  @EnableWebMvc - Adding this annotation to an @Configuration class imports the Spring MVC configuration from WebMvcConfigurationSupport.
 *  
 *  WebMvcConfigurationSupport - This is the main class providing the configuration behind the MVC Java config. It is typically imported by 
 *  adding @EnableWebMvc to an application @Configuration class.
 *  
 *  @EnableTransactionManagement - This will enable transaction management support within the application and search the code for
 *  transaction boundaries. We declare transaction boundaries by using @Transactional. This annotation can be applied to a class or method or both.
 *  @Transactional tells spring that the particular method or class supports transaction. The transactional annotation itself defines the scope of 
 *  a single database transaction. The database transaction happens inside the scope of a persistence context.
 *  
 */

@Configuration
@ComponentScan("com.org.cricket")  
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class WebAppConfiguration {
	/*
	 * Initialization using application.properties file.
	 * To execute the code in browser:http://localhost:8080/cricket-team-ratings/
	 * 
	 * */
			private static final String PROPERTY_NAME_DATABASE_DRIVER = "db.driver";
			private static final String PROPERTY_NAME_DATABASE_URL = "db.url";
			private static final String PROPERTY_NAME_DATABASE_USERNAME = "db.username";
			private static final String PROPERTY_NAME_DATABASE_PASSWORD = "db.password";
			
			private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
			private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";			
			private static final String PROPERTY_NAME_HIBERNATE_FORMAT_SQL = "hibernate.format_sql";
			private static final String PROPERTY_NAME_HIBERNATE_DEFAULT_SCHEMA = "hibernate.default_schema";
			private static final String PROPERTY_NAME_HIBERNATE_CACHE_PROVIDER_CLASS = "hibernate.cache.provider_class";
			private static final String PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";
			
			private static final String HIBERNATE_ENTITYMANAGER_PACKAGES_TO_SCAN="hibernate.entitymanager.packages.to.scan";
			/*
			 * Environment class is used to load the properties from application.properties to our application. 
			 */
			
			@Autowired
			private Environment env;
			
			/*
			 *dataSource() contains the complete information of the database to be used to store data. 
			 *@Bean will be used to create an object of the below method. When we want to create a new object we will use @Bean on top of
			 *the method.
			 */
			
			@Bean
			public DataSource dataSource(){
				DriverManagerDataSource dataSource = new DriverManagerDataSource();
				dataSource.setDriverClassName(env.getProperty(PROPERTY_NAME_DATABASE_DRIVER));
				dataSource.setUrl(env.getProperty(PROPERTY_NAME_DATABASE_URL));
				dataSource.setUsername(env.getProperty(PROPERTY_NAME_DATABASE_USERNAME));
				dataSource.setPassword(env.getProperty(PROPERTY_NAME_DATABASE_PASSWORD));
				return dataSource;
			}
			
			@Bean
			public Properties hibernateProperties(){
				Properties property = new Properties();
				property.setProperty(PROPERTY_NAME_HIBERNATE_DIALECT, env.getProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
				property.setProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
				property.setProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL, env.getProperty(PROPERTY_NAME_HIBERNATE_FORMAT_SQL));
				property.setProperty(PROPERTY_NAME_HIBERNATE_DEFAULT_SCHEMA, env.getProperty(PROPERTY_NAME_HIBERNATE_DEFAULT_SCHEMA));
				property.setProperty(PROPERTY_NAME_HIBERNATE_CACHE_PROVIDER_CLASS,env.getProperty(PROPERTY_NAME_HIBERNATE_CACHE_PROVIDER_CLASS));
				property.setProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO, env.getProperty(PROPERTY_NAME_HIBERNATE_HBM2DDL_AUTO));
				return property;
			}
			
			/*
			 * Refer hibernate.cfg.xml and you will find that we are injecting the database properties inside the 
			 * sessionfactory tag. Hence we will use the following method to serve our purpose.
			 * 
			 *Use hibernate4 instead of hibernate3 to use the setPackagesToScan() method.
			 *import org.springframework.orm.hibernate4.LocalSessionFactoryBean; 
			 *
			 *setPackagesToScan() method will be used to scan for entity classes in the classpath mentioned in the env.getProperty().
			 *
			 *If you check the sessionfactory tag inside the hibernate.cfg.xml, you will notice that we are defining database properties, 
			 *hibernate properties and mapping entity classes. We will do the same in sesionFactory() method.
			 */
			
			
			@Bean
			public LocalSessionFactoryBean sessionFactory(){
				LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
				sessionFactory.setDataSource(dataSource()); //We will set all the values of datasource inside the sessionfactory.	
				sessionFactory.setHibernateProperties(hibernateProperties()); //Setting hibernate properties
				sessionFactory.setPackagesToScan(env.getProperty(HIBERNATE_ENTITYMANAGER_PACKAGES_TO_SCAN)); //Mapping entity classes.
				return sessionFactory;
			}

			@Bean
			public HibernateTransactionManager transactionManager(){
				HibernateTransactionManager trxnmgr = new HibernateTransactionManager();
				trxnmgr.setSessionFactory(sessionFactory().getObject()); // Set the SessionFactory that this instance should manage transactions for
				return trxnmgr;
			}
			
			/*
			 *InternalResourceViewResolver is used to resolve “internal resource view” (in simple, it’s final output, jsp or html page) 
			 *based on a predefined URL pattern. In additional, it allow you to add some predefined prefix or suffix to the view name 
			 *(prefix + view name + suffix), and generate the final view page URL.
			 */
			
			@Bean
			public UrlBasedViewResolver setUpView(){
			UrlBasedViewResolver viewresolver = new UrlBasedViewResolver();
			viewresolver.setPrefix("/WEB-INF/pages/");  //We will define all jsp pages inside the pages folder in WEB-INF folder.
			viewresolver.setSuffix(".jsp");
			viewresolver.setViewClass(JstlView.class);
			return viewresolver;
			}
}




