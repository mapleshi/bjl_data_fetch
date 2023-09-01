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
@Table(name = "caiyule_msg")
public class CaiyuleMsg {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    int id;

    String account;
    String msg;
    int level;
    Timestamp createTime;
}
