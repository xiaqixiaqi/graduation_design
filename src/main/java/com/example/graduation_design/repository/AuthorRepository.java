package com.example.graduation_design.repository;

import com.example.graduation_design.bean.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Map;

public interface AuthorRepository extends CrudRepository<Author,Integer> {
    @Query("from Author p where p.authorName = ?1" )
    Author findAuthorByAuthorName(String authorName);
    @Query(value = "select year(a_age),count(*) from author group by year(a_age)",nativeQuery = true)
    List<Object> vvv();
}
