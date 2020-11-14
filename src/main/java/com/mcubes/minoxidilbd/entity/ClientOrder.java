package com.mcubes.minoxidilbd.entity;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

/**
 * Created by A.A.MAMUN on 10/19/2020.
 */
@Entity
public class ClientOrder {

    public enum OrderStatus {
        Pending, Processing, Transit, Complete, Failed, Canceled
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "client_order_sequence")
    @SequenceGenerator(name = "client_order_sequence", sequenceName = "client_order_sequence", allocationSize = 1,
            initialValue = 101)
    private long id;
    private String date;
    private boolean regularClient;
    private String clientEmail;
    @OneToMany(targetEntity = OrderItem.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "clientOrderId", referencedColumnName = "id")
    private List<OrderItem> orderItems;
    @OneToOne(targetEntity = ShippingInformation.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "shippingInformationId", referencedColumnName = "id")
    private ShippingInformation shippingInformation;
    @Column(length = 10)
    private String clientType;
    private float deliveryCharge;
    @Transient
    private double subTotal;
    @Transient
    private double total;
    private OrderStatus status;

    @SuppressWarnings("deprecation")
    public ClientOrder(){
       date = Calendar.getInstance().getTime().toLocaleString();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isRegularClient() {
        return regularClient;
    }

    public void setRegularClient(boolean regularClient) {
        this.regularClient = regularClient;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public ShippingInformation getShippingInformation() {
        return shippingInformation;
    }

    public void setShippingInformation(ShippingInformation shippingInformation) {
        this.shippingInformation = shippingInformation;
    }

    public float getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(float deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public double getSubTotal() {
        subTotal = 0;
        for (OrderItem orderItem : orderItems){
            subTotal += orderItem.getProductPrice() * orderItem.getQuantity();
        }
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ClientOrder{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", regularClient=" + regularClient +
                ", clientEmail='" + clientEmail + '\'' +
                ", orderItems=" + orderItems +
                ", shippingInformation=" + shippingInformation +
                ", clientType='" + clientType + '\'' +
                ", deliveryCharge=" + deliveryCharge +
                ", subTotal=" + subTotal +
                ", total=" + total +
                ", status=" + status +
                '}';
    }
}
