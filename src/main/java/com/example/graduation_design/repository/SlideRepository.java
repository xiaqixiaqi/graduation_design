package com.example.graduation_design.repository;

import com.example.graduation_design.bean.Slide;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SlideRepository extends CrudRepository<Slide,Integer> {
    @Query("select s.imgAddress from Slide s ")
    List<String> findAllImgAddress();

    @Modifying
    @Transactional
    @Query("update Slide s set s.slideTheme=?1,s.content=?2,s.url=?3,s.imgAddress=?4 where s.imgAddress=?5")
    int updateSlide(String slideTheme,String content, String url, String updateIm, String imgAddress);

}
