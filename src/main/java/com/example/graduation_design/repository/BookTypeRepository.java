package com.example.graduation_design.repository;

import com.example.graduation_design.bean.Book;
import com.example.graduation_design.bean.BookType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookTypeRepository extends CrudRepository<BookType,Integer> {
    List<BookType> findAll();
    @Query("from BookType p where p.bTypeName = ?1" )
    BookType findBookTypeByBTypeNameEquals(String bTypeName);
}
