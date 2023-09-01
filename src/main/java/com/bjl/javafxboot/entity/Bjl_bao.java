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
@Table(name = "bjl_bao")
public class Bjl_bao {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(unique=true, nullable=false)
    int id;

    int tab;
    int model;
    Timestamp cdt;
}
