package ImageHoster.repository;

import ImageHoster.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.*;

//The annotation is a special type of @Component annotation which describes that the class defines a data repository
@Repository
public class UserRepository {
    //Get an instance of EntityManagerFactory from persistence unit with name as 'imageHoster'
    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    //The method receives the User object to be persisted in the database
    //Creates an instance of EntityManager
    //Starts a transaction
    //The transaction is committed if it is successful
    //The transaction is rolled back in case of unsuccessful transaction
    public void registerUser(User newUser) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            //persist() method changes the state of the model object from transient state to persistence state
            em.persist(newUser);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }


    //The method receives the entered username and password
    //Creates an instance of EntityManager
    //Executes JPQL query to fetch the user from User class where username is equal to received username and password is equal to received password
    //Returns the fetched user
    //Returns null in case of NoResultException
    public User checkUser(String username, String password) {
        //Complete the method
        EntityManager entityManager = emf.createEntityManager();
        try {
            TypedQuery<User> typedQuery = entityManager.createQuery("select u from User u where u.username = :un and u.password = :pwd", User.class);
            typedQuery.setParameter("un", username);
            typedQuery.setParameter("pwd", password);
            User user = typedQuery.getSingleResult();
            return user;
        } catch (NoResultException e) {
            return null;
        }
    }
}