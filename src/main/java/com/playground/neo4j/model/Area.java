package com.playground.neo4j.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Area {


    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "LIVES_IN", direction = "INCOMING")
    //@JsonIgnore
    private Set<Person> lives_in;

    public Area() {
        lives_in = new HashSet<>();
    }

    public Set<Person> getLives_in() {
        return lives_in;
    }

    public void setLives_in(Set<Person> lives_in) {
        this.lives_in = lives_in;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
