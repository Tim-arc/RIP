package com.project.course_project_1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.course_project_1.entity.Message;


import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
}
