package com.org.cricket.service;

import java.util.List;

/*
 * Communication between controller class and repository class happens through service class.
 *Controller will call service class which in turn will call repository class and it will call sessionfactory to execute queries on 
 * database.
 */

import com.org.cricket.model.Team;

public interface TeamService {
	public void addTeam(Team team);
	public void updateTeam(Team team);
	public void deleteTeam(int id);
	public Team getTeam(int id);
	public List<Team> allTeams();
}
