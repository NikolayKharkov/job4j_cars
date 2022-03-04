package repositories;

import models.Post;
import models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import store.HibernateFactory;

import java.util.List;
import java.util.function.Function;

public class PostRepository {

    private final SessionFactory sf = HibernateFactory.instOf().getSf();

    private static final class Lazy {
        private static final PostRepository INST = new PostRepository();
    }

    public static PostRepository instOf() {
        return PostRepository.Lazy.INST;
    }

    public Post addPost(Post post) {
        if (post.getId() == 0) {
            int id = (int) this.tx(session -> session.save(post));
            post.setId(id);
        } else {
            updatePost(post);
        }
        return post;
    }

    public void deletePost(Post post) {
        this.tx(session -> {
            session.delete(post);
            return true;
        });
    }

    public void updatePost(Post post) {
        this.tx(session -> {
            session.update(post);
            return true;
        });
    }

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

    public void updatePostPhotoStatus(int idPost) {
        Post post = findPostById(idPost);
        post.setPhoto(true);
        updatePost(post);
    }

    public List<Post> findAllPosts() {
        return this.tx(
                session -> session.createQuery("from Post order by created").list()
        );
    }

    public Post findPostById(int id) {
        return tx(session -> {
            Query<Post> query = session.createQuery("from Post where id = :id");
            query.setParameter("id", id);
            return query.uniqueResult();
        });
    }

    public List<Post> findAllUsersPosts(int userId) {
        User user = UserRepository.instOf().findUserById(userId);
        return tx(session -> {
            Query<Post> query = session.createQuery("from Post where user = :user");
            query.setParameter("user", user);
            return query.list();
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
