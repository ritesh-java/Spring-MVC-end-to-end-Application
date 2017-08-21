package com.org.cricket.service;

/*
 * Communication between controller class and repository class happens through service class.
 *Controller will call service class which in turn will call repository class and it will call sessionfactory to execute queries on 
 * database.
 */

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.org.cricket.dao.TeamRepository;
import com.org.cricket.model.Team;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

	/*
	 * @Autorwired will make sure to establish the connection between TeamRepository class and the variable defined below.
	 * */
	
	@Autowired
	private TeamRepository repository;
	
	/*
	 *Here we can clearly see that service class is just acting as a bridge between controller class and repository class. Controller class
	 *will call this class which calls repository class. 
	 * */
	
	@Override
	public void addTeam(Team team) {
		// TODO Auto-generated method stub
		repository.addTeam(team);
		
	}

	@Override
	public void updateTeam(Team team) {
		// TODO Auto-generated method stub
		repository.updateTeam(team);
	}

	@Override
	public void deleteTeam(int id) {
		// TODO Auto-generated method stub
		repository.deleteTeam(id);
	}

	@Override
	public Team getTeam(int id) {
		// TODO Auto-generated method stub
		return repository.getTeam(id);
	}

	@Override
	public List<Team> allTeams() {
		// TODO Auto-generated method stub
		return repository.allTeams();
	}

}
