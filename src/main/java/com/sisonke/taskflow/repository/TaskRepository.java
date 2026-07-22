package com.sisonke.taskflow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sisonke.taskflow.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserEmail(String email);

    Optional<Task> findByIdAndUserEmail(Long id, String email);

}
