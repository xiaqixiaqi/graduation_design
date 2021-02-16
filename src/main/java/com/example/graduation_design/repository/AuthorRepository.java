package com.example.graduation_design.repository;

import com.example.graduation_design.bean.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author,Integer> {
    @Query("from Author p where p.authorName = ?1" )
    Author findAuthorByAuthorName(String authorName);
}
