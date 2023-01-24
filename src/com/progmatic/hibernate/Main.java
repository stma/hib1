package com.progmatic.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;
import java.util.Scanner;

public class Main {

    private static SessionFactory sessionFactory;

    static void addPizza() {
        try (
            Scanner sc = new Scanner(System.in);
        ) {
            Pizza1 p = new Pizza1();

            System.out.println("Add meg a pizza nevet: ");
            String name = sc.nextLine();
            p.setName("Lisbinba");
            System.out.println("Add meg a pizza arat: ");
            Integer price = sc.nextInt();
            sc.nextLine();
            p.setPrice(price);

            SessionFactory sessionFactory = Main.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();

            session.beginTransaction();

            session.persist(p);

//            session.clear();
//            session.merge(p);
//
//            session.remove(p);
//            session.persist(p);
//
//            Pizza a = session.find(Pizza.class, p.getId());

            session.flush();

            session.getTransaction().commit();
            System.out.println("Pizza id = " + p.getId());
        } finally {
            if (sessionFactory!= null) {
                sessionFactory.close();
            }
        }
    }

    static void getAllPizza() {
        SessionFactory sessionFactory = Main.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

//        Pizza1 p1 = new Pizza1();
//        p1.setName("Almas");
//        session.persist(p1);

//        Query<Pizza1> q = session.createQuery("FROM Pizza1", Pizza1.class);
//        for (Pizza1 p : q.list()) {
//            System.out.println(p);
//        }

//        NativeQuery<Object> pizzas = session.createNativeQuery("SELECT * FROM pizza p", Object.class);
//
//        for (Object a: pizzas.list()) {
//            System.out.println(a);
//        }

//        session.flush();

        Query<Pizza> q = session.createQuery("SELECT p FROM Pizza p", Pizza.class);

//        Query<String> q = session.createQuery("SELECT p.name FROM Pizza p", String.class);
//        for (String p : q.list()) {
//            System.out.println(p);
//        }

//        Query<Pizza> q = session.createQuery("SELECT P FROM Pizza P WHERE P.id = 6", Pizza.class);
//        Query<Pizza> q = session.createQuery("FROM Pizza P WHERE P.id > 3 ORDER BY P.price DESC", Pizza.class);

//        Query<Pizza> q = session.createQuery("FROM Pizza P WHERE P.price < :priceLimit ORDER BY P.price DESC", Pizza.class);
//        q.setParameter("priceLimit", 1000);

//        Query<Pizza> q = session.createQuery("FROM Pizza P ORDER BY P.price DESC Limit 10 offset 5", Pizza.class);
//        Query<Pizza> q = session.createQuery("FROM Pizza P ORDER BY P.price DESC", Pizza.class);
//        q.setFirstResult(5);
//        q.setMaxResults(10);

        for (Pizza p : q.list()) {
            System.out.println(p);
        }

//        Long priceSum = session.createQuery("select sum(p.price) from Pizza p ", Long.class)
//                .getSingleResult();
//        System.out.println(String.format("Osszes pizza ara: %d", priceSum));
//
//        String pizzasName = session.createQuery("select group_concat(p.name) from Pizza p ", String.class)
//                .getSingleResult();
//        System.out.printf("Osszes pizza neve: %s%n", pizzasName);


        session.getTransaction().commit();
        sessionFactory.close();
    }
    public static void main(String[] args) {
//        addPizza();
        getAllPizza();
    }

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration();

            Properties props = new Properties();

            props.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            props.put("hibernate.connection.url", System.getenv("pdb_url"));
            props.put("hibernate.connection.username", System.getenv("pdb_user"));
            props.put("hibernate.connection.password", System.getenv("pdb_pw"));

            props.put("hibernate.current_session_context_class", "thread");
            props.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
            props.put("hibernate.show_sql", "true");

            configuration.setProperties(props);
//            configuration.addClass(Pizza1.class);
            configuration.addAnnotatedClass(Pizza.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            return configuration.buildSessionFactory(serviceRegistry);

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static SessionFactory buildSessionFactoryByXml() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");

//            configuration.addAnnotatedClass(Pizza.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();

            return configuration.buildSessionFactory(serviceRegistry);

        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed. " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = buildSessionFactory();
//            sessionFactory = buildSessionFactoryByXml();
        }
        return sessionFactory;
    }
}
