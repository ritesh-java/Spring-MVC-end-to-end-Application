package com.org.cricket.dao;

import java.util.List;

import com.org.cricket.model.Team;

/*
 * We will mention all the CRUD operations here. 
 */
public interface TeamRepository {
	public void addTeam(Team team);
	public void updateTeam(Team team);
	public void deleteTeam(int id);
	public Team getTeam(int id);
	public List<Team> allTeams();
	
}
