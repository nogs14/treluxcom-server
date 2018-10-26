package com.treluxcom.database;

import java.io.File;
import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class HibernateUtil implements Serializable {

    private static final SessionFactory sessionFactory;
    
    static {
        try {
            File file = new File("src//main//resources//hibernate.cfg.xml");
        	StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure(file).build();
		sessionFactory = new MetadataSources( standardRegistry ).buildMetadata().buildSessionFactory();
                

        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static Session getSession(){
        return sessionFactory.openSession();
    }
    public static void closeSession(){
       sessionFactory.close();
    }
}
