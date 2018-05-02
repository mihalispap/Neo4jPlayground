package com.playground.neo4j.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NodeEntity
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Person {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "CURRENT_EMPLOYMENT", direction = "OUTGOING")
    //@JsonIgnore
    private Company current_work;

    @Relationship(type = "HAS_WORKED_FOR", direction = "OUTGOING")
    //@JsonIgnore
    private Set<Company> past_employments;

    @Relationship(type = "LIKES", direction = "OUTGOING")
    //@JsonIgnore
    private Set<Hobby> likes;

    @Relationship(type="LIKES_GRADED", direction="OUTGOING")
    private Set<HobbyGraded> likes_graded;

    @Relationship(type = "LIVES_IN", direction = "OUTGOING")
    //@JsonIgnore
    private Area lives_in;

    public Set<Hobby> getLikes() {
        return likes;
    }

    public void setLikes(Set<Hobby> likes) {
        this.likes = likes;
    }

    public Area getLives_in() {
        return lives_in;
    }

    public void setLives_in(Area lives_in) {
        this.lives_in = lives_in;
    }

    public void add_hobby(Hobby h){
        likes.add(h);
        h.getLikes().add(this);
    }

    public void add_graded_hobby(Hobby h, int how_much){
        HobbyGraded hg = new HobbyGraded(this, h, how_much);
        likes_graded.add(hg);
        h.getLikes_graded().add(hg);
    }

    public void add_lives_in(Area a){
        lives_in=a;
        a.getLives_in().add(this);
    }

    public void currently_works(Company c){
        current_work=c;
        try {
            c.getEmployees().add(this);
        }
        catch(Exception e){
            c.setEmployees(new HashSet<Person>());
            c.getEmployees().add(this);
        }
    }

    public void add_past_empolyment(Company c){

        try {
            past_employments.add(c);
        }
        catch(Exception e){
            past_employments = new HashSet<Company>();
            past_employments.add(c);
        }

        try {
            c.getPast_employees().add(this);
        }
        catch(Exception e){
            c.setPast_employees(new HashSet<Person>());
            c.getPast_employees().add(this);
        }
    }

    public Set<Company> getPast_employments() {
        return past_employments;
    }

    public void setPast_employments(Set<Company> past_employments) {
        this.past_employments = past_employments;
    }

    public Company getCurrent_work() {
        return current_work;
    }

    public void setCurrent_work(Company current_work) {
        this.current_work = current_work;
    }

    public Person() {
        likes = new HashSet<>();
        likes_graded = new HashSet<>();
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

    public Set<HobbyGraded> getLikes_graded() {
        return likes_graded;
    }

    public void setLikes_graded(Set<HobbyGraded> likes_graded) {
        this.likes_graded = likes_graded;
    }

    @Override
    public String toString() {

        String pe = "";
        for(Company c : past_employments)
            pe+=c.toString()+" ";

        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", current_work=" + current_work.toString() +
                ", past_employments=" + pe +
                '}';
    }

    @Override
    public boolean equals(Object o){
        return true;
    }
}
