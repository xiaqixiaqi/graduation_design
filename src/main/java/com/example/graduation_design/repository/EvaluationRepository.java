package com.example.graduation_design.repository;

import com.example.graduation_design.bean.Evaluation;
import com.example.graduation_design.bean.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EvaluationRepository extends CrudRepository<Evaluation,Integer> {
    List<Evaluation> findAllByBookBookId(int bookId);
    List<Evaluation> findAllByUser(User user);
}
