package com.org.cricket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.org.cricket.model.Team;
import com.org.cricket.service.TeamService;

/*
 * To execute the code in browser:http://localhost:8080/cricket-team-ratings/
 * */

/*
 * Communication between controller class and repository class happens through service class. Check TeamServiceImpl class.
 * Controller will call service class which in turn will call repository class and it will call sessionfactory to execute queries on 
 * database.
 * 
 * Here ModelAndView is return a particular jsp page. Here add-team-form, home, list-of-teams, edit-team-form are jsp pages defined 
 * in WEB-INF/pages folder. 
 * */

@Controller
@RequestMapping(value="/team")  //All request related to "/team" will come in this controller.
public class TeamController {
	
	@Autowired
	private TeamService teamService;
	
	@RequestMapping(value="/add", method=RequestMethod.GET)
	public ModelAndView addTeamPage(){
		ModelAndView modelAndView = new ModelAndView("add-team-form");	
		/*
		 * We are passing the dummy object "team" and an empty Team so that when we get the team values through post method (IN LINE POST),
		 * those values are passed into this below object. 
		 */
		modelAndView.addObject("team", new Team());
		return modelAndView;
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST) //LINE POST
	public ModelAndView addingTeam(@ModelAttribute Team team){
		//After adding we will return to the home page, Before returning we will add the data to the database.
		ModelAndView modelAndView = new ModelAndView("home");
		
		/*
		 * Here from controller we are calling service class method which in turn calls repository class method. Repository class then calls
		 * sessionFactory method from WebAppConfiguration.java file. Then we are sending the message to be displayed back to the page.
		 * */
		modelAndView.addObject("message", "Data is successfully added");
		teamService.addTeam(team); 
		return modelAndView;
	}
	
	@RequestMapping(value="/list") //By default get method is called.
	public ModelAndView listOfTeams(){
		ModelAndView modelAndView = new ModelAndView("list-of-teams"); //We will return to list-of-team.jsp page with the list of all teams.
		List<Team> teams= teamService.allTeams(); //Calling service to get the list of all teams.
		modelAndView.addObject("teams",teams);
		return modelAndView;
	}
	
	/*
	 *@PathVariable - Annotation which indicates that a method parameter should be bound to a URI template variable. Supported for RequestMapping 
	 *annotated handler methods in Servlet environments.
	 *
	 * In the following method we are returning the team object to be modified to the edit-team-form.jsp.
	 */
	 
	@RequestMapping(value="/edit/{id}", method=RequestMethod.GET)
	public ModelAndView editTeamPage(@PathVariable Integer id){ 
		ModelAndView modelAndView = new ModelAndView("edit-team-form");
		Team team = teamService.getTeam(id); //Firstly, we will get the current data to be modified from the database.
		modelAndView.addObject("team",team); //We will pass that data to edit-team-form.jsp page to modify it.
		return modelAndView;
	}
	
	
	/*
	 * In the following method we are getting new value object from edit-team-form.jsp and will save it to the database. 
	 */
	@RequestMapping(value="/edit/{id}", method=RequestMethod.POST)
	public ModelAndView editingTeam(@ModelAttribute Team team, @PathVariable Integer id){
		ModelAndView modelAndView = new ModelAndView("home"); //We need to return to the home page after modifying the data in the database.
		teamService.updateTeam(team);
		modelAndView.addObject("message","Team updated successfully!!");
		return modelAndView;
		
	}
	
	@RequestMapping(value="/delete/{id}", method=RequestMethod.GET)
	public ModelAndView deleteTeam(@PathVariable Integer id){
		ModelAndView modelAndView = new ModelAndView("home"); //Will return to the home page after deleting the data from the database.
		teamService.deleteTeam(id);//Data is deleted from the database.
		modelAndView.addObject("message","Team deleted successfully!!");//Message is passed to the home.jsp page to be displayed.
		return modelAndView;
	}
	
	
}
