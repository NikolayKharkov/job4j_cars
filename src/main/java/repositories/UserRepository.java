package repositories;

import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import store.HibernateFactory;

import java.util.function.Function;

public class UserRepository {

    private final SessionFactory sf = HibernateFactory.instOf().getSf();

    private static final class Lazy {
        private static final UserRepository INST = new UserRepository();
    }

    public static UserRepository instOf() {
        return UserRepository.Lazy.INST;
    }

    public User addUser(User user) {
        int id = (int) this.tx(session -> session.save(user));
        user.setId(id);
        return user;
    }

    public User findUserByEmail(String email) {
        return tx(session -> {
            Query<User> query = session.createQuery("from User where email = :email");
            query.setParameter("email", email);
            return query.uniqueResult();
        });
    }

    public User findUserById(int id) {
        return tx(session -> {
            Query<User> query = session.createQuery("from User where id = :id");
            query.setParameter("id", id);
            return query.uniqueResult();
        });
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
