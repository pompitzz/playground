package me.sun.java_spring_jpa.join.one_to_many.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    private String content;

    @OneToMany(mappedBy = "review")
    private List<Attachment> attachments;

    @OneToMany(mappedBy = "review")
    private List<SurveyAnswer> surveyAnswers;
}
