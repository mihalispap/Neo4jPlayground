package com.playground.neo4j.model;

import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "LIKES_GRADED")
public class HobbyGraded {

    @Id
    @GeneratedValue
    private Long id;

    @StartNode
    private Person person;

    @EndNode
    private Hobby hobby;

    private int how_much;

    public HobbyGraded(Person person, Hobby hobby, int how_much) {
        this.person = person;
        this.hobby = hobby;
        this.how_much = how_much;
    }

    public HobbyGraded() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Hobby getHobby() {
        return hobby;
    }

    public void setHobby(Hobby hobby) {
        this.hobby = hobby;
    }

    public int getHow_much() {
        return how_much;
    }

    public void setHow_much(int how_much) {
        this.how_much = how_much;
    }
}
