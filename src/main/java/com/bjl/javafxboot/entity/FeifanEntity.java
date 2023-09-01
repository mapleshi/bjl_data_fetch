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
@Table(name = "feifanguoji_helei")
public class FeifanEntity {
    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    String issue;

    String code;
    Timestamp belongDate;
}
