package com.example.graduation_design.service;

import com.example.graduation_design.bean.Book;
import com.example.graduation_design.bean.BookType;
import com.example.graduation_design.bean.ImageAddress;
import com.example.graduation_design.repository.AuthorRepository;
import com.example.graduation_design.repository.BookRepository;
import com.example.graduation_design.repository.BookTypeRepository;
import com.example.graduation_design.repository.ImageAddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.log.LogInputStream;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

@Service
public class BookService {
    @Resource
    private PictureDeal pictureDeal;
    @Resource
    private BookTypeRepository bookTypeRepository;
    @Resource
    private AuthorRepository authorRepository;
    @Resource
    private ImageAddressRepository imageAddressRepository;
    @Resource
    private BookRepository bookRepository;
    Book book;
    //后台：添加书籍的类型
    public boolean addBookType(String bTypeName){
        bookTypeRepository.save(new BookType(bTypeName));
        return true;
    }
    public List<BookType> findAllBookType(){
        for (BookType bookType:
        bookTypeRepository.findAll()) {
            System.out.println(bookType.getbTypeName());
        }
        return bookTypeRepository.findAll();
    }
    //添加书籍的图片
    public Set<ImageAddress> addBookImage(Book book,MultipartFile[] imgAddress) {
        Set<ImageAddress> imageAddressSet = new HashSet<>();
        for (int i = 0; i < imgAddress.length - 1; i++) {
            String address = pictureDeal.upload(imgAddress[i], "bookPicture");
            System.out.println(i + ":" + address);
            if ((address != "") && !(address.equals(""))) {
                ImageAddress imageAddress = new ImageAddress();
                imageAddress.setpAddress(address);
                imageAddress.setIsValuable(1);
                imageAddress.setBook(book);
                //保存书的照片
                imageAddressRepository.save(imageAddress);
                imageAddressSet.add(imageAddress);
            }
        }
        return imageAddressSet;
    }
    //添加书籍
    public boolean addBook(String bookName,float price,int inventory,
                           String author,String bookType,
                           String introduction,MultipartFile[] imgAddress){
        book=new Book();
        book.setBookName(bookName);
        book.setPrice(price);
        book.setBookType(bookTypeRepository.findBookTypeByBTypeNameEquals(bookType));
        book.setIntroduction(introduction);
        book.setInventory(inventory);
        book.setAuthor(authorRepository.findAuthorByAuthorName(author));
        book.setBookImages(addBookImage(book,imgAddress));
        book.setIsValuable(1);//设置书的有效值
        bookRepository.save(book);

        return true;
    }
    //查询所有书籍并按照类型分类
    public Map<String,List<Book>> findAllBookByBookType(){
        Map<String,List<Book>> stringListMap=new HashMap<>();
        List<BookType> bookTypes=bookTypeRepository.findAll();
        for (BookType bookType: bookTypes) {
            String booktypename = bookType.getbTypeName();
            stringListMap.put(booktypename,bookRepository.findBooksByBookType(booktypename));
        }
        return stringListMap;
    }

    //通过书名查找书籍
    public Book findBookByBookName(String bookname) {
        return bookRepository.findBookByBookName(bookname);
    }
    //查找一类型的所有书籍
    public List<Book> findBooksByType(String bookType){
        return bookRepository.findBooksByBookType(bookType);
    }
    //通过书的id查找书
    public Book findBookByBookId(int bookId){
        return bookRepository.findById(bookId).get();
    }
    //查询所有书的类型和该类型的总书数
    public List<List> findAllBookTypeAndBookSize(){
        List<List> bookTypeList=new ArrayList<>();
        List<BookType> bookTypes=bookTypeRepository.findAll();
        for (BookType bookType:bookTypes) {
            List list=new ArrayList<>();
            list.add(bookType.getBookTypeId());
            list.add(bookType.getbTypeName());
            list.add(bookType.getBooks().size());
            bookTypeList.add(list);
        }
        return bookTypeList;
    }
    //查询所有书籍
    public List<Book> findAllBook(){
        return bookRepository.findAll();
    }
    //修改书籍详情
    public boolean updatingBook(String bookName,float price,int inventory,String authorName, String typeName,
                                String introduction,MultipartFile[] imgAddress){
        book=bookRepository.findBookByBookName(bookName);
        if (imgAddress.length>1){
            for (ImageAddress img:book.getBookImages()) {
                img.setIsValuable(0);
            }
            book.setBookImages(addBookImage(book,imgAddress));
        }
        book.setBookName(bookName);
        book.setPrice(price);
        book.setInventory(inventory);
        book.setAuthor(authorRepository.findAuthorByAuthorName(authorName));
        book.setBookType(bookTypeRepository.findBookTypeByBTypeNameEquals(typeName));
        book.setIntroduction(introduction);
        bookRepository.save(book);
        return true;
    }
}
