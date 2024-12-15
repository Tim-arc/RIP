package com.project.course_project_1.repository;

import com.project.course_project_1.entity.Dialog;
import com.project.course_project_1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DialogRepository extends JpaRepository<Dialog, Long> {
    Optional<Dialog> findByUser1AndUser2(User user1, User user2);
    List<Dialog> findAllByUser1OrUser2(User user1, User user2);
}
