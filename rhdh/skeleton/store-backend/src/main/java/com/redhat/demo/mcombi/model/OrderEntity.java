package com.redhat.demo.mcombi.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.util.List;

@Entity
public class OrderEntity extends PanacheEntity {

    public int quantity;
    public String description;

    public String itemCategory;

    public static OrderEntity findByDescription(String description){
        return  find("description",description).firstResult();
    }

    public static void deleteById(int id){
        deleteById(id);
    }

    public static List<OrderEntity> findAllOrderEntity(){
        return listAll();
    }
}
