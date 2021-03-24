package com.bookstore.demo.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "BOOK_TYPE")
@Entity
@Data
public class BookType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long typeId;
    private String name;
}