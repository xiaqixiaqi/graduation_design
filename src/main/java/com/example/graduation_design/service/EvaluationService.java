package com.example.graduation_design.service;

import com.example.graduation_design.bean.Evaluation;
import com.example.graduation_design.repository.BookRepository;
import com.example.graduation_design.repository.EvaluationRepository;
import com.example.graduation_design.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class EvaluationService {
    @Resource
    EvaluationRepository evaluationRepository;
    @Resource
    BookRepository bookRepository;
    @Resource
    UserRepository userRepository;
    //添加评论
    public Boolean addEvaltion(int userId,int bookId,String content){
        Evaluation evaluation=new Evaluation();
        evaluation.setBook(bookRepository.findById(bookId).get());
        evaluation.setUser(userRepository.findById(userId).get());
        evaluation.seteContent(content);
        //存放发表日期
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        evaluation.seteTime(formatter.format(date));
        evaluationRepository.save(evaluation);
        return true;
    }
    //查询该书的所有评论
    public List<Evaluation> findAllEvaluationByBook(int bookId){
        return evaluationRepository.findAllByBookBookId(bookId);
    }


}
