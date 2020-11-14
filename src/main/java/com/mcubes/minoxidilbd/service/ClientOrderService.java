package com.mcubes.minoxidilbd.service;

import com.mcubes.minoxidilbd.entity.ClientOrder;
import com.mcubes.minoxidilbd.entity.OrderItem;
import com.mcubes.minoxidilbd.entity.ShippingInformation;
import com.mcubes.minoxidilbd.repository.ClientOrderRepository;
import com.mcubes.minoxidilbd.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by A.A.MAMUN on 10/19/2020.
 */
@Service
@Transactional
public class ClientOrderService {

    private static final Logger LOG = Logger.getLogger(ClientOrderService.class.getName());

    @Autowired
    private ClientOrderRepository clientOrderRepository;
    @Autowired
    private ProductRepository productRepository;

    @Transactional(rollbackFor = Exception.class)
    public String submitOrder(List<OrderItem> orderItems, ShippingInformation shippingInformation,
                           String contactEmailOrPhone, boolean regular){
        try {
            ClientOrder order = new ClientOrder();
            order.setOrderItems(orderItems);
            order.setShippingInformation(shippingInformation);
            order.setClientEmail(contactEmailOrPhone);
            order.setRegularClient(regular);
            String clientType = regular ? "R" : "G";
            String city = shippingInformation.getCity().trim().toLowerCase();
            if(city.contains("dhaka") || city.contains(" dhaka") || city.contains("dhaka ") || city.contains(" dhaka "))
            {
                clientType += "ID-";
                order.setDeliveryCharge(60);
            } else {
                clientType += "OD-";
                order.setDeliveryCharge(150);
            }
            order.setClientType(clientType);
            order.setStatus(ClientOrder.OrderStatus.Pending);
            clientOrderRepository.save(order);

            for(OrderItem item : orderItems){
                long productId = item.getProductId();
                int currentStock = productRepository.findStockByProductId(productId);
                productRepository.updateProductStock(currentStock - item.getQuantity(), productId);

                int currentSell = productRepository.findSellByProductId(productId);
                productRepository.updateProductSell(currentSell + item.getQuantity(), productId);
            }

            return clientType + order.getId();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ClientOrder getClientOrderByOrderId(long orderId){
        return clientOrderRepository.findById(orderId).get();
    }


    public Page<ClientOrder> fetchRegularClientOrderForClientOrderHistory(
            String clientEmail, ClientOrder.OrderStatus status, int page, int count){
        if (status==null)
          return clientOrderRepository.findRegularClientOrderByClientEmail(clientEmail, PageRequest.of(page, count));
        else
            return clientOrderRepository.findRegularClientOrderByClientEmailAndOrderStatus(clientEmail, status,
                    PageRequest.of(page, count));
    }

    public ClientOrder fetchClientOrderByClientEmailAndOrderId(String clientEmail, long id){
         return clientOrderRepository.findClientOrderByClientEmailAndId(clientEmail, id);
    }

    public synchronized String cancelPendingOrder(String clientEmail, long id){
        /**
         * lock the admin order service so that admin can't perform update
         * the order status while client canceled the order.
         */
        ClientOrder clientOrder = clientOrderRepository.findClientOrderByClientEmailAndId(clientEmail, id);
        if(clientOrder!=null && clientOrder.getStatus()== ClientOrder.OrderStatus.Pending){
            try {
                for (OrderItem item: clientOrder.getOrderItems()){
                    int currentStock = productRepository.findProductStockById(item.getProductId());
                    productRepository.updateProductStock(currentStock + item.getQuantity(), item.getProductId());
                }
                clientOrderRepository.updateClientOrderStatus(ClientOrder.OrderStatus.Canceled, clientEmail, id);
            }catch (Exception e){
                LOG.warning("# Ex : " + e.getMessage());
                return "Server error. Order can't canceled. Please try again later.";
            }
        } else{
            return "Can't cancel the order. Because it is " + clientOrder.getStatus() + " state.";
        }
        return "success";
    }

    public Page<ClientOrder> fetchClientOrderByStatus(ClientOrder.OrderStatus status){
        return clientOrderRepository.findClientOrderByStatus(status, PageRequest.of(0, 10));
    }
}
