package com.example.graduation_design.service;

import com.example.graduation_design.bean.Book;
import com.example.graduation_design.bean.Evaluation;
import com.example.graduation_design.bean.OrderItem;
import com.example.graduation_design.bean.User;
import com.example.graduation_design.repository.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EvaluationService {
    @Resource
    EvaluationRepository evaluationRepository;
    @Resource
    BookRepository bookRepository;
    @Resource
    UserRepository userRepository;
    @Resource
    OrderItemRepository orderItemRepository;
    @Resource
    BusinessRepository businessRepository;
//    //添加评论
//    public Boolean addEvaltion(int userId,int bookId,String content){
//        Evaluation evaluation=new Evaluation();
//        evaluation.setBook(bookRepository.findById(bookId).get());
//        evaluation.setUser(userRepository.findById(userId).get());
//        evaluation.seteContent(content);
//        //存放发表日期
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        evaluation.seteTime(formatter.format(date));
//        evaluationRepository.save(evaluation);
//        return true;
//    }
    //查询该书的所有评论
    public List<Evaluation> findAllEvaluationByBook(int bookId){
        return evaluationRepository.findAllByBookBookId(bookId);
    }
    public boolean addEvaluation(int orderItemId, int praise,String comment, int userId){
        Evaluation evaluation=new Evaluation();
        Book book=orderItemRepository.findById(orderItemId).get().getBook();
        User user=userRepository.findById(userId).get();
        OrderItem orderItem=orderItemRepository.findById(orderItemId).get();
        //存放发表日期
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        evaluation.seteTime(formatter.format(date));
        evaluation.seteContent(comment);
        evaluation.setBook(book);
        evaluation.setUser(user);
        evaluation.setOrderItem(orderItem);
        evaluationRepository.save(evaluation);
        orderItem.setIsPayment(5);//修改订单项为已修改
        //orderItemRepository.updateIsPaymentByorderItemId(5,orderItemId);//修改订单项为已修改
        orderItem.setEvaluation(evaluation);
        orderItemRepository.save(orderItem);
        //修改书籍的好评度
        if (praise==1){
            //好评
            bookRepository.updatePraiseByBookId(book.getBookId(),1);
        }else{
            bookRepository.updatePoorReviewsByBookId(book.getBookId(),1);
        }
        return true;
    }
    //返回该用户所有的评论
    public List<Evaluation> findAllEvaluationByUserId(int userId){
        List<Evaluation> evaluations= (List<Evaluation>) userRepository.findById(userId).get().getEvaluations();
        return  evaluations;
    }
    //返回该店家的所有书籍的评价
    public List<Map<String,Integer>> findAllBookPraise(int bId){
        List<Book> books=businessRepository.findById(bId).get().getBooks();
        List<Map<String,Integer>> bookspraise=new LinkedList<>();
        for(Book book:books){
            Map map=new HashMap<>();
            map.put("bookName",book.getBookName());
            map.put("praise",book.getPraise());
            map.put("poorReviews",book.getPoorReviews());
            bookspraise.add(map);
        }
       // JSONArray json = JSONArray.fromObject(bookspraise);
        return bookspraise;
    }


}
