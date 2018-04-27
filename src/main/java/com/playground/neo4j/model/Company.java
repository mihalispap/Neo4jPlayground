package com.playground.neo4j.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.Objects;
import java.util.Set;

@NodeEntity
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Company {

    @Id
    @GeneratedValue
    private Long id;

    private String company_name;

    @Relationship(type = "CURRENT_EMPLOYMENT", direction = "INCOMING")
    //@JsonIgnore
    private Set<Person> employees;

    @Relationship(type = "HAS_WORKED_FOR", direction = "INCOMING")
    //@JsonIgnore
    private Set<Person> past_employees;

    public Set<Person> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Person> employees) {
        this.employees = employees;
    }

    public Set<Person> getPast_employees() {
        return past_employees;
    }

    public void setPast_employees(Set<Person> past_employees) {
        this.past_employees = past_employees;
    }

    public Company() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", company_name='" + company_name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o){
        return true;
    }
}
