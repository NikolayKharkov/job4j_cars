package repositories;

import models.CarBody;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import store.HibernateFactory;

import java.util.List;
import java.util.function.Function;

public class CarBodyRepository {

    private final SessionFactory sf = HibernateFactory.instOf().getSf();

    private static final class Lazy {
        private static final CarBodyRepository INST = new CarBodyRepository();
    }

    public static CarBodyRepository instOf() {
        return CarBodyRepository.Lazy.INST;
    }

    public CarBody addCarBody(CarBody carBody) {
        int id = (int) this.tx(session -> session.save(carBody));
        carBody.setId(id);
        return carBody;
    }

    public List<CarBody> findAllCarBody() {
        return this.tx(
                session -> session.createQuery("from CarBody order by name").list()
        );
    }

    public CarBody findCarBodyById(int carBodyId) {
        return tx(session -> {
            Query<CarBody> query = session.createQuery("from CarBody where id = :id");
            query.setParameter("id", carBodyId);
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
