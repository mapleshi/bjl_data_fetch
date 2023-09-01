package com.bjl.javafxboot.dao;

import com.bjl.javafxboot.entity.Bjl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Timestamp;
import java.util.List;

public interface BjlDao extends JpaRepository<Bjl, Integer> {
    List<Bjl> findByTabAndCntGreaterThanAndCreateTimeBetweenOrderByCreateTime(String tab, int cnt, Timestamp time, Timestamp time2);

    List<Bjl> findByTabAndCreateTimeBetweenOrderByCreateTime(String tab, Timestamp time1, Timestamp time2);
}
