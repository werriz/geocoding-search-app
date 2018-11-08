package com.jurijz.geocodingsearch.utils;

import com.jurijz.geocodingsearch.domain.City;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jurijz on 10/18/2018.
 */
public class HibernateUtil {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {

                // Create registry builder
                StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Map<String, String> settings = new HashMap<>();

                settings.put(Environment.DRIVER, PropertyUtil.getProperty(Environment.DRIVER));
                settings.put(Environment.URL, PropertyUtil.getProperty(Environment.URL));
                settings.put(Environment.USER, PropertyUtil.getProperty(Environment.USER));
                settings.put(Environment.PASS, PropertyUtil.getProperty(Environment.PASS));
                settings.put(Environment.DIALECT, PropertyUtil.getProperty(Environment.DIALECT));
                settings.put(Environment.HBM2DDL_AUTO, PropertyUtil.getProperty(Environment.HBM2DDL_AUTO));
                settings.put(Environment.DEFAULT_SCHEMA, PropertyUtil.getProperty(Environment.DEFAULT_SCHEMA));
                settings.put(Environment.SHOW_SQL, PropertyUtil.getProperty(Environment.SHOW_SQL));

                // Apply settings
                registryBuilder.applySettings(settings);

                // Create registry
                registry = registryBuilder.build();

                // Create MetadataSources
                MetadataSources sources = new MetadataSources(registry);
                sources.addAnnotatedClass(City.class);

                // Create Metadata
                Metadata metadata = sources.getMetadataBuilder().build();

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            } catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
