package com.bjl.javafxboot.dao;

import com.bjl.javafxboot.entity.FeifanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeifanDao extends JpaRepository<FeifanEntity, Integer> {
    List<FeifanEntity> findByIssueBetweenOrderByIssue(String issueStart, String issueEnd);
}
