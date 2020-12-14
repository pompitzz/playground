package me.sun.java_spring_jpa.join.one_to_many.repository;

import me.sun.java_spring_jpa.join.one_to_many.entity.Attachment;
import me.sun.java_spring_jpa.join.one_to_many.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
}
