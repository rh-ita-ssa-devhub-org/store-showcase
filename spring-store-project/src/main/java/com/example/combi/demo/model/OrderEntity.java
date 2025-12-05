package com.example.combi.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "orderentity")
public class OrderEntity {
    public String description;
    @Id
    public Long id;
    public int quantity;

    public String itemCategory;

}
