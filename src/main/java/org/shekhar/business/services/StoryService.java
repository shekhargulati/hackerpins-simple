package org.shekhar.business.services;

import org.shekhar.business.domain.Story;

import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by shekhargulati on 11/04/14.
 */
@Stateless
public class StoryService {

    @PersistenceContext(unitName = "hackerpinsPU")
    private EntityManager entityManager;

    public Story create(Story story) {
        entityManager.persist(story);
        return story;
    }

    public boolean exists(String url) {
        try {
            Story story = entityManager.createQuery("SELECT s from Story s where s.url =:url", Story.class).setParameter("url", url).getSingleResult();
            return true;
        } catch (NoResultException e) {
            return false;
        }
    }

    public List<Story> findAll() {
        return entityManager.createQuery("SELECT s from Story s order by s.submittedAt desc", Story.class).getResultList();
    }
}
