package com.david.api_test.repository;

import com.david.api_test.models.Task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
    public interface TaskRepository extends JpaRepository<Task, Long> {

        public List<Task> findByUserId(Long userId);


        List<Task> findByUserCompanyId(Long companyId);
    }