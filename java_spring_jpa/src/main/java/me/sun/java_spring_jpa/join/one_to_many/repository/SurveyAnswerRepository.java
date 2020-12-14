package me.sun.java_spring_jpa.join.one_to_many.repository;

import me.sun.java_spring_jpa.join.one_to_many.entity.Attachment;
import me.sun.java_spring_jpa.join.one_to_many.entity.SurveyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {
}
