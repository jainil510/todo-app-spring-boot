
package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This is the repository for our Todo entity.
 * It extends JpaRepository, which provides all the basic CRUD (Create, Read, Update, Delete) operations.
 * We specify the type of the entity (Todo) and the type of its primary key (Long).
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}
