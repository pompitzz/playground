package me.sun.java_spring_jpa.join.one_to_many;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sun.java_spring_jpa.join.one_to_many.entity.Attachment;
import me.sun.java_spring_jpa.join.one_to_many.entity.Review;
import me.sun.java_spring_jpa.join.one_to_many.entity.SurveyAnswer;
import me.sun.java_spring_jpa.join.one_to_many.repository.AttachmentRepository;
import me.sun.java_spring_jpa.join.one_to_many.repository.ReviewRepository;
import me.sun.java_spring_jpa.join.one_to_many.repository.SurveyAnswerRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyApplicationRunner implements ApplicationRunner {

    private final EntityManager entityManager;
    private final ReviewRepository reviewRepository;
    private final SurveyAnswerRepository surveyAnswerRepository;
    private final AttachmentRepository attachmentRepository;

    @Transactional
    @Override
    public void run(ApplicationArguments args) throws Exception {
        init(3, 3, 5);
        private static final Pattern PATTERN = Pattern.compile("\\$\\[\\p{Alpha}+]");
        log.info("============ flush clear =================");
        entityManager.flush();
        entityManager.clear();

        List<Review> reviews = reviewRepository.findAll();

        reviews.forEach(r -> {
            log.info("attachment");
            r.getAttachments().forEach(Attachment::getImageUrl);
            log.info("surveyAnswers");
            r.getSurveyAnswers().forEach(SurveyAnswer::getAnswer);
        });
    }

    private void init(int reviewSize, int attachmentSizeEachReview, int surveySizeEachReview) {
        IntStream.range(0, reviewSize)
                .forEach(i1 ->
                        {
                            Review review = reviewRepository.save(
                                    Review.builder().content("review " + i1).build()
                            );

                            IntStream.range(0, attachmentSizeEachReview)
                                    .forEach(i2 ->
                                            attachmentRepository.save(
                                                    Attachment.builder()
                                                            .imageUrl("attachment " + i2 + "(" + review.getContent() + ")")
                                                            .thumbnailUrl("attachment " + i2 + "(" + review.getContent() + ")")
                                                            .review(review)
                                                            .build()
                                            )
                                    );

                            IntStream.range(0, surveySizeEachReview)
                                    .forEach(i3 ->
                                            surveyAnswerRepository.save(
                                                    SurveyAnswer.builder()
                                                            .question("surveyAnswer " + i3 + "(" + review.getContent() + ")")
                                                            .answer("surveyAnswer " + i3 + "(" + review.getContent() + ")")
                                                            .review(review)
                                                            .build()
                                            )
                                    );
                        }
                );
    }
}
