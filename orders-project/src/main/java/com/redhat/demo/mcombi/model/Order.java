package com.redhat.demo.mcombi.model;

public class Order {
    public String description;
    public int id;
    public int quantity;

    public String itemCategory;

    public Order(){

    }

    public Order (String description,int quantity,String itemCategory){

        this.description=description;
        this.quantity=quantity;
        this.itemCategory=itemCategory;
    }

}
