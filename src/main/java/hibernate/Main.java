package hibernate;

import hibernate.entity.CountryEntity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) {
        try (Session session = getSession()) {
            CountryEntity countryEntity = new CountryEntity();
            countryEntity.setCountryId(101);
            countryEntity.setCountryName("Country 101");
            session.save(countryEntity);
            System.out.println(session.get(CountryEntity.class, countryEntity.getCountryId()));
            countryEntity.setCountryName(countryEntity.getCountryName() + " changed");
            session.update(countryEntity);
            System.out.println(session.get(CountryEntity.class, countryEntity.getCountryId()));
            session.delete(countryEntity);
            System.out.println(session.get(CountryEntity.class, countryEntity.getCountryId()));
        }
    }
}