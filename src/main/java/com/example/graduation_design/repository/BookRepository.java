package com.example.graduation_design.repository;

import com.example.graduation_design.bean.Book;
import com.example.graduation_design.bean.BookType;
import com.example.graduation_design.bean.ReceiptsAddress;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRepository extends CrudRepository<Book,Integer> {
    List<Book> findAll();
    @Query("select b from Book b where b.bookType.bTypeName=?1")
    List<Book> findBooksByBookType(String booktype);
    Book findBookByBookName(String bookname);
    //返回最新的信息
    @Query("select b from Book b where b.isValuable=?1 ORDER BY b.bookId desc ")
    List<Book> findBooksDESC(int isValuable);
    @Modifying
    @Transactional
    @Query("update Book b set b.sales=b.sales+?2 where b.bookId=?1")
    int updateSalesByBookId(int bookId,int sales);
    @Query("select b from Book b where b.business.bId=?1 and b.isValuable=?2 ORDER BY b.sales desc")
    List<Book> findBooksByBusinessBIdOrderBySalesDesc(int bId,int isValuable);
    @Query("select b from Book b where b.business.bId=?2 and b.bookName=?1")
    Book findBookByBookNameAndBId(String bookName,int bId);
    @Modifying
    @Transactional
    @Query("update Book b set b.praise=b.praise+?2 where b.bookId=?1")
    int updatePraiseByBookId(int bookId,int praise);
    @Modifying
    @Transactional
    @Query("update Book b set b.poorReviews=b.poorReviews+?2 where b.bookId=?1")
    int updatePoorReviewsByBookId(int bookId,int poorReviews);
    @Modifying
    @Transactional
    @Query("update Book b set b.isValuable=?1 where b.bookId=?2 ")
    int updateBookIsValuationByBookId(int isValuable,int bookId);
    @Query("select b from Book b where b.bookName like %?1% or b.bookType.bTypeName like %?1% or b.author.authorName like %?1%")
    List<Book> findAllByBookNameLike(String bookName);
}
