package com.playground.neo4j;

import com.playground.neo4j.model.Area;
import com.playground.neo4j.model.Company;
import com.playground.neo4j.model.Hobby;
import com.playground.neo4j.model.Person;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.neo4j.ogm.cypher.ComparisonOperator.CONTAINING;
import static org.neo4j.ogm.cypher.ComparisonOperator.EQUALS;

public class Play {

    public static void main(String[] args) {


        Configuration configuration = new Configuration.Builder()
                .uri("http://127.0.0.1:7474")
                .credentials("neo4j", "mihalis")
                .build();

//        Configuration configuration = new Configuration.Builder()
//                .uri("bolt://127.0.0.1:7687")
//                .credentials("neo4j", "mihalis")
//                .build();

        SessionFactory sessionFactory = new SessionFactory(configuration, "com.playground.neo4j");
        Session session = sessionFactory.openSession();

        session.deleteAll(Person.class);
        session.deleteAll(Company.class);
        session.deleteAll(Area.class);
        session.deleteAll(Hobby.class);

        Company eurobank = new Company();
        eurobank.setCompany_name("Eurobank");

        Company etravel = new Company();
        etravel.setCompany_name("e-Travel");

        Company foodakai = new Company();
        foodakai.setCompany_name("FOODAKAI");

        Company athensvoice = new Company();
        athensvoice.setCompany_name("AthensVoice");

        Company agroknow = new Company();
        agroknow.setCompany_name("Agroknow");

        Hobby cs = new Hobby();
        cs.setName("Computer Science");

        Hobby hugs = new Hobby();
        hugs.setName("Hugs");

        Hobby walking = new Hobby();
        walking.setName("Walking");

        Hobby naked = new Hobby();
        naked.setName("Being Naked");

        Hobby panathinaikos = new Hobby();
        panathinaikos.setName("Panathinaikos");

        Area glyfada = new Area();
        glyfada.setName("Glyfada");

        Area gerakas = new Area();
        gerakas.setName("Gerakas");

        Person mihalis = new Person();
        mihalis.setName("Mihalis");

        Person konstantina = new Person();
        konstantina.setName("Konstantina");


        /*
        *   Relationships
        * */

        konstantina.currently_works(etravel);
        konstantina.add_past_empolyment(eurobank);
        konstantina.add_lives_in(gerakas);
        konstantina.add_hobby(cs);
        konstantina.add_hobby(walking);
        konstantina.add_hobby(hugs);
        konstantina.add_hobby(naked);
        konstantina.add_hobby(naked);

        mihalis.currently_works(eurobank);
        mihalis.add_past_empolyment(foodakai);
        mihalis.add_past_empolyment(athensvoice);
        mihalis.add_past_empolyment(agroknow);
        mihalis.setLives_in(glyfada);
        mihalis.add_hobby(panathinaikos);
        mihalis.add_hobby(cs);
        mihalis.add_hobby(naked);

        session.save(konstantina);
        session.save(mihalis);

        /*
        *   Queries
        * */

        ArrayList<Person> persons;

        persons=(ArrayList<Person>)session.loadAll(Person.class,
                new Filter("name", EQUALS, "Konstantina"));
        for(Person p : persons){
            System.out.println(p.toString());
        }
        System.out.println("-1-");

        Filter filter = new Filter("company_name", ComparisonOperator.EQUALS, etravel.getCompany_name());
        filter.setNestedPropertyName("current_work");
        filter.setNestedPropertyType(Company.class);
        persons = (ArrayList<Person>) session.loadAll(Person.class, filter);
        for(Person p : persons){
            System.out.println(p.toString());
        }
        System.out.println("-2-");

        String query = "match(n:Person)-[r:CURRENT_EMPLOYMENT]-(c:Company) where c.company_name=$cname return n";
        Map<String, String> parameters = Collections.singletonMap( "cname", "Eurobank" );

        Result res=session.query(query, parameters);

        for(Map<String, Object> map : res.queryResults()){
            System.out.println(map.entrySet().iterator().next().getValue());
        }
        System.out.println("-3-");

        System.out.println("----------------------------------------");
        System.out.println("Commons between: "+konstantina.getName()+" and "+mihalis.getName());
        System.out.println("----------------------------------------");

        System.out.print("Common Hobbies: ");
        query = "match(n1:Person)-[r1:LIKES]-(h:Hobby)-[r2:LIKES]-(n2:Person) return distinct h";
        res=session.query(query, parameters);
        String commons="";
        for(Map<String, Object> map : res.queryResults()){
            commons+=((Hobby)map.entrySet().iterator().next().getValue()).getName()+", ";
        }
        System.out.println(commons.trim().substring(0,commons.length() - 2));

        System.out.print("Common Areas: ");
        query = "match(n1:Person)-[r1:LIVES_IN]-(a:Area)-[r2:LIVES_IN]-(n2:Person) return distinct a";
        res=session.query(query, parameters);
        for(Map<String, Object> map : res.queryResults()){
            System.out.print(((Hobby)map.entrySet().iterator().next().getValue()).getName());
        }

        System.out.print("\nCommon Employment: ");
        query = "match(n1:Person)-[r]-(c:Company)-[r2]-(n2:Person) return distinct c";
        res=session.query(query, parameters);
        commons="";
        for(Map<String, Object> map : res.queryResults()){
            commons+=((Company)map.entrySet().iterator().next().getValue()).getCompany_name()+", ";
        }
        System.out.println(commons.trim().substring(0,commons.length() - 2));



    }

}
