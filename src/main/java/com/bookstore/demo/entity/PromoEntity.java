package com.bookstore.demo.entity;

import lombok.Data;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import java.util.Map;

@Data
@Entity
@Table(name = "PROMO_CODE")
public class PromoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long promoId;
    @Column(unique=true)
    private String code;
    @ElementCollection
    @CollectionTable(name = "PROMO_CODE_MAP",
            joinColumns = {@JoinColumn(name = "promo_id", referencedColumnName = "promoId")})
    @MapKeyColumn(name = "item_name")
    @Column(name = "discount")
    private Map<String, Integer> discount;
}
