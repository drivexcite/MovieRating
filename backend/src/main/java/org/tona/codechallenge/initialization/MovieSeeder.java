package org.tona.codechallenge.initialization;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.tona.codechallenge.model.Genre;

@Component
public class MovieSeeder implements ApplicationRunner {

	private static String genreList = "Action, Adventure, Comedy, Crime, Drama, Historical, Documentary, Horror, Musical, Science Fiction, Westener, Foreign";
	private EntityManager entityManager;

	@Autowired
	public MovieSeeder(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Transactional
	@Override
	public void run(ApplicationArguments args) throws Exception {
		var genres = genreList.split(",");
		for(String genre : genres){
			var query = entityManager.createQuery("select g from Genre g where g.title = :title");
			query.setParameter("title", genre);
			
			var genreEntities = query.getResultList();
			
			if(genreEntities.size() < 1) {
				var genreEntity = new Genre();
				genreEntity.setTitle(genre);
				
				entityManager.persist(genreEntity);	
			}					
		}
	}
}