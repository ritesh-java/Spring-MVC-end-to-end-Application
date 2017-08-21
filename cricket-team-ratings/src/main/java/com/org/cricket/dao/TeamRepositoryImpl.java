package com.org.cricket.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.org.cricket.model.Team;

/*
 *HERE WE NEED TO WRITE THE LOGIC TO CONNECT TO THE DATABASE. HERE WE HAVE ESTABLISHED THE DATABASE CONNECTIVITY AND WE ARE PERFORMING
 *CRUD OPERATION ON OUR DATABASE. 
 */

@Repository
public class TeamRepositoryImpl implements TeamRepository {

	/*
	 * We need to use @Autowired here. This will ensure that sessionFactory is initialized from WebAppConfiguration.java class using
	 * sessionFactory() method. If we don't use @Autowired then sessionFactory will be null.
	 */

	@Autowired
	SessionFactory sessionFactory;
	
	/*
	 * getCurrentSession() returns the new available session and we don't need to worry about closing the session but we need to flush and
	 * close the session when we use openSession() method.
	 * 
	 */
	
	public Session getCurrentSession(){  
		return sessionFactory.getCurrentSession();
	} 
	
	@Override
	public void addTeam(Team team) {
		// TODO Auto-generated method stub
		getCurrentSession().save(team);
		
	}

	@Override
	public void updateTeam(Team team) {
		// TODO Auto-generated method stub
		getCurrentSession().merge(team);	
	}

	@Override
	public void deleteTeam(int id) {
		// TODO Auto-generated method stub
		Team team = getTeam(id);
		
		if(team!=null)
			getCurrentSession().delete(team);
		else
			System.out.println("Team you're trying to delete is not available");
	}

	@Override
	public Team getTeam(int id) {
		// TODO Auto-generated method stub
		Team team = (Team) getCurrentSession().get(Team.class, id);
		return team;
	}

	@Override
	public List<Team> allTeams() {
		// TODO Auto-generated method stub
		return getCurrentSession().createQuery("from Team").list();
		
	}

}
