package ImageHoster.repository;

import ImageHoster.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

@Repository
public class TagRepository {
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    public Tag createTag(Tag tag) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(tag);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
        return tag;
    }

    public Tag getTagByName(String tagName) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Tag> typedQuery = em.createQuery("select t from Tag t where t.name = :name", Tag.class);
            typedQuery.setParameter("name", tagName);
            Tag result = typedQuery.getSingleResult();
            return result;
        } catch (NoResultException e) {
            return null;
        }
    }
}
