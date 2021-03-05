package com.example.graduation_design.service;

import com.example.graduation_design.bean.*;
import com.example.graduation_design.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import sun.rmi.log.LogInputStream;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.File;
import java.text.SimpleDateFormat;
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
    @Resource
    private ShoppingOrderRepository shoppingOrderRepository;
    @Resource
    private BusinessRepository businessRepository;
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
                           String introduction,MultipartFile[] imgAddress,int bId){
        Business business=businessRepository.findById(bId).get();
        book=new Book();
        book.setBookName(bookName);
        book.setPrice(price);
        book.setBookType(bookTypeRepository.findBookTypeByBTypeNameEquals(bookType));
        book.setIntroduction(introduction);
        book.setInventory(inventory);
        book.setAuthor(authorRepository.findAuthorByAuthorName(author));
        book.setBookImages(addBookImage(book,imgAddress));
        book.setIsValuable(1);//设置书的有效值
        book.setBusiness(business);
        //获取当前的时间
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String addDate1=formatter.format(new Date());
        book.setAddDate(addDate1);
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
    //通过书名查找书籍(后台：需要是商家的书籍）
    public Book findBookByBookNameAndBId(String bookname,int bId) {
        return bookRepository.findBookByBookNameAndBId(bookname,bId);
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
        //获取当前的时间
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String addDate=formatter.format(date);
        book.setAddDate(addDate);
        bookRepository.save(book);
        return true;
    }
    //返回最新书籍的前4条
    public List<Book> findFourNewBook(){
        List<Book> books=bookRepository.findBooksDESC(1).subList(0,4);
        return books;
    }
    //对订单的书籍修改销售量，通过订单编号（orderNumber）
    public boolean updateAllBookSalesByOrderNumber(String orderNumber){
        List<OrderItem> orderItems=  shoppingOrderRepository.findShoppingOrderByOrderNumber(orderNumber).get(0).getOrderItems();
        for (OrderItem orderItem:orderItems){
            Book book=orderItem.getBook();
            bookRepository.updateSalesByBookId(book.getBookId(),orderItem.getoINumber());
        }
        return true;
    }
    //搜索该书店的书籍销售量
    public List<Map<String, Integer>> findBookSaleByBId(int bId){
        List<Book> books=bookRepository.findBooksByBusinessBIdOrderBySalesDesc(bId,1);
        List<Map<String, Integer>> data=new LinkedList<>();
        for (Book book:books) {
            Map map=new HashMap();
            map.put("bookName",book.getBookName());
            map.put("sales",book.getSales());
            map.put("poorReviews",book.getPoorReviews());
            data.add(map);

        }
return data;
    }
    //查询书籍的所有评价
    public List<Evaluation> findAllEvaluation(int bId,String bookName){
        return (List<Evaluation>) bookRepository.findBookByBookNameAndBId(bookName,bId).getEvaluations();
    }
    //设置书籍为无效书籍
    public boolean updateBookIsValuation(int bookId){
        bookRepository.updateBookIsValuationByBookId(0,bookId);
        return true;
    }
    //模糊搜索书名
    public List<Book> findBookByBookNameLike(String bookName){
        return  bookRepository.findAllByBookNameLike(bookName);
    }
}
