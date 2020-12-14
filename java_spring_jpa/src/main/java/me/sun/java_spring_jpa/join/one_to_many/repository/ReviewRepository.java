package me.sun.java_spring_jpa.join.one_to_many.repository;

import me.sun.java_spring_jpa.join.one_to_many.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

//    @Query(value =
//            "select r from Review as r"
//                    + " join fetch r.attachments as a"
//                    + " join fetch r.surveyAnswers as sa"
//    )
//    List<Review> findWithQuery();
}
