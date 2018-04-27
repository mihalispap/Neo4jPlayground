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
public class Hobby {

    @Id
    @GeneratedValue
    private Long id;

    private String name;


    @Relationship(type = "LIKES", direction = "INCOMING")
    //@JsonIgnore
    private Set<Person> likes;

    public Hobby() {
        likes = new HashSet<>();
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

    public Set<Person> getLikes() {
        return likes;
    }

    public void setLikes(Set<Person> likes) {
        this.likes = likes;
    }
}
