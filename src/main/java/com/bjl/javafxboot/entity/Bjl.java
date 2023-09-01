package com.bjl.javafxboot.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "bjl")
public class Bjl {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    int id;

    String tab;
    String result;
    int cnt;
    Timestamp createTime;
    String xy1;
    String xy2;

    public Bjl(String table, String result, int cnt, String xy1, String xy2) {
        this.setTab(table);
        this.setResult(result);
        this.setCnt(cnt);
        this.setXy1(xy1);
        this.setXy2(xy2);
    }

    public Bjl() {
    }
}
