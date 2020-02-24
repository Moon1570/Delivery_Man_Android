package com.example.delivery;

public class OrderModel {
    private String order_id, orderdate, ordername, orderStatus, careofstatus, careofcontact, price, phonenumber, ordervillage, orderquantity, orderstreet, zipcode, division;
    private String expectedDeliveryDate;
    private String district;
    private String union;
    private String upazilla;
    private String cutomer_name;




    public OrderModel(String order_id, String orderdate, String ordername, String orderStatus, String careofstatus, String cutomer_name, String careofcontact, String price, String phonenumber, String ordervillage, String orderquantity, String orderstreet, String zipcode, String division, String expectedDeliveryDate, String district, String union, String upazilla) {
        this.order_id = order_id;
        this.orderdate = orderdate;
        this.ordername = ordername;
        this.orderStatus = orderStatus;
        this.careofstatus = careofstatus;
        this.careofcontact = careofcontact;
        this.price = price;
        this.phonenumber = phonenumber;
        this.ordervillage = ordervillage;
        this.orderquantity = orderquantity;
        this.orderstreet = orderstreet;
        this.zipcode = zipcode;
        this.division = division;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.district = district;
        this.union = union;
        this.upazilla = upazilla;
        this.cutomer_name = cutomer_name;
    }



    public OrderModel(String name, String orderId, String date) {
        this.ordername = name;
        this.order_id = orderId;
        this.expectedDeliveryDate = date;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public String getOrdername() {
        return ordername;
    }

    public void setOrdername(String ordername) {
        this.ordername = ordername;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCareofstatus() {
        return careofstatus;
    }

    public void setCareofstatus(String careofstatus) {
        this.careofstatus = careofstatus;
    }

    public String getCareofcontact() {
        return careofcontact;
    }

    public void setCareofcontact(String careofcontact) {
        this.careofcontact = careofcontact;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getOrdervillage() {
        return ordervillage;
    }

    public void setOrdervillage(String ordervillage) {
        this.ordervillage = ordervillage;
    }

    public String getOrderquantity() {
        return orderquantity;
    }

    public void setOrderquantity(String orderquantity) {
        this.orderquantity = orderquantity;
    }

    public String getOrderstreet() {
        return orderstreet;
    }

    public void setOrderstreet(String orderstreet) {
        this.orderstreet = orderstreet;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(String expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getUnion() {
        return union;
    }

    public void setUnion(String union) {
        this.union = union;
    }

    public String getUpazilla() {
        return upazilla;
    }

    public void setUpazilla(String upazilla) {
        this.upazilla = upazilla;
    }

    public String getCutomer_name() {
        return cutomer_name;
    }

    public void setCutomer_name(String cutomer_name) {
        this.cutomer_name = cutomer_name;
    }
}


