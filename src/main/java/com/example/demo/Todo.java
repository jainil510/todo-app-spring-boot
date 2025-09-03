
package com.example.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * This class represents a single To-Do item.
 * The @Entity annotation tells Spring that this is a database entity.
 */
@Entity
public class Todo {

    /**
     * The unique ID for the to-do item.
     * @Id marks this field as the primary key.
     * @GeneratedValue tells Spring to automatically generate this value.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private boolean completed;

    // Constructors
    public Todo() {
    }

    public Todo(String title) {
        this.title = title;
        this.completed = false;
    }

    // Getters and Setters are required by JPA to create and access the data.

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
