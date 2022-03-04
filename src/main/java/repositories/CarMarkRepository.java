package repositories;

import models.CarMark;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import store.HibernateFactory;

import java.util.List;
import java.util.function.Function;

public class CarMarkRepository {
    private final SessionFactory sf = HibernateFactory.instOf().getSf();

    private static final class Lazy {
        private static final CarMarkRepository INST = new CarMarkRepository();
    }

    public static CarMarkRepository instOf() {
        return CarMarkRepository.Lazy.INST;
    }

    public CarMark addCarMark(CarMark carMark) {
        int id = (int) this.tx(session -> session.save(carMark));
        carMark.setId(id);
        return carMark;
    }

    public List<CarMark> findAllCarMark() {
        return this.tx(
                session -> session.createQuery("from CarMark order by name").list()
        );
    }

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
