package store;

import models.CarBody;
import models.CarMark;
import models.Post;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;
import java.util.function.Function;

public class DbStore implements Store {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final DbStore INST = new DbStore();
    }

    public static DbStore instOf() {
        return Lazy.INST;
    }

    @Override
    public User addUser(User user) {
        int id = (int) this.tx(session -> session.save(user));
        user.setId(id);
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        return tx(session -> {
            Query<User> query = session.createQuery("from User where email = :email");
            query.setParameter("email", email);
            return query.uniqueResult();
        });
    }

    @Override
    public User findUserById(int id) {
        return tx(session -> {
            Query<User> query = session.createQuery("from User where id = :id");
            query.setParameter("id", id);
            return query.uniqueResult();
        });
    }

    @Override
    public Post addPost(Post post) {
        if (post.getId() == 0) {
            int id = (int) this.tx(session -> session.save(post));
            post.setId(id);
        } else {
            updatePost(post);
        }
        return post;
    }

    @Override
    public void deletePost(Post post) {
        this.tx(session -> {
            session.delete(post);
            return true;
        });
    }

    @Override
    public void updatePost(Post post) {
        this.tx(session -> {
            session.update(post);
            return true;
        });
    }

    @Override
    public void updatePostStatus(int idPost) {
        Post post = findPostById(idPost);
        if (post.isSold()) {
            post.setSold(false);
            updatePost(post);
        } else {
            post.setSold(true);
            updatePost(post);
        }
    }

    @Override
    public void updatePostPhotoStatus(int idPost) {
        Post post = findPostById(idPost);
        post.setPhoto(true);
        updatePost(post);
    }


    @Override
    public List<Post> findAllPosts() {
        return this.tx(
                session -> session.createQuery("from Post order by created").list()
        );
    }

    @Override
    public Post findPostById(int id) {
        return tx(session -> {
            Query<Post> query = session.createQuery("from Post where id = :id");
            query.setParameter("id", id);
            return query.uniqueResult();
        });
    }

    @Override
    public List<Post> findAllUsersPosts(int userId) {
        User user = findUserById(userId);
        return tx(session -> {
            Query<Post> query = session.createQuery("from Post where user = :user");
            query.setParameter("user", user);
            return query.list();
        });
    }

    @Override
    public CarBody addCarBody(CarBody carBody) {
        int id = (int) this.tx(session -> session.save(carBody));
        carBody.setId(id);
        return carBody;
    }

    @Override
    public List<CarBody> findAllCarBody() {
        return this.tx(
                session -> session.createQuery("from CarBody order by name").list()
        );
    }

    @Override
    public CarBody findCarBodyById(int carBodyId) {
        return tx(session -> {
            Query<CarBody> query = session.createQuery("from CarBody where id = :id");
            query.setParameter("id", carBodyId);
            return query.uniqueResult();
        });
    }

    @Override
    public CarMark addCarMark(CarMark carMark) {
        int id = (int) this.tx(session -> session.save(carMark));
        carMark.setId(id);
        return carMark;
    }

    @Override
    public List<CarMark> findAllCarMark() {
        return this.tx(
                session -> session.createQuery("from CarMark order by name").list()
        );
    }

    @Override
    public CarMark findCarMarkById(int carMarkId) {
        return tx(session -> {
            Query<CarMark> query = session.createQuery("from CarMark where id = :id");
            query.setParameter("id", carMarkId);
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
