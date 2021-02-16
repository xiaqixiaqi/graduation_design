package com.example.graduation_design.repository;

import com.example.graduation_design.bean.Book;
import com.example.graduation_design.bean.BookType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book,Integer> {
    List<Book> findAll();
    @Query("select b from Book b where b.bookType.bTypeName=?1")
    List<Book> findBooksByBookType(String booktype);
    Book findBookByBookName(String bookname);
}
