package com.jurijz.geocodingsearch.service.impl;

import com.jurijz.geocodingsearch.domain.City;
import com.jurijz.geocodingsearch.service.CityRepository;
import com.jurijz.geocodingsearch.utils.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by jurijz on 10/18/2018.
 */
public class CityRepositoryImpl implements CityRepository {

    public void save(City city) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        session.saveOrUpdate(city);

        session.getTransaction().commit();
        session.close();
    }

    @SuppressWarnings({"unchecked", "deprecation"})
    public List<City> loadAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();


        List<City> cities = (List<City>) session.createCriteria(City.class).list();

        session.getTransaction().commit();
        session.close();

        return cities;
    }
}
