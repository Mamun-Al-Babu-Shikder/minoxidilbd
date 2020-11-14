package com.mcubes.minoxidilbd.controller.client;

import com.mcubes.minoxidilbd.data.CommonData;
import com.mcubes.minoxidilbd.entity.ClientOrder;
import com.mcubes.minoxidilbd.service.ClientOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by A.A.MAMUN on 11/1/2020.
 */
@Controller
@RequestMapping("/account")
public class ClientOrderController {

    private static final Logger LOG = Logger.getLogger(ClientOrderController.class.getName());

    @Autowired
    private CommonData commonData;
    @Autowired
    private ClientOrderService clientOrderService;

    @RequestMapping("/my-order")
    public String myOrderHistory(Principal principal, Model model,
                                 @RequestParam(name = "status", defaultValue = "all") String status,
                                 @RequestParam(name="page", defaultValue = "1") int page,
                                 @RequestParam(name = "count", defaultValue = "10") int count)
    {


        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);

        ClientOrder.OrderStatus orderStatus = commonData.getOrderStatusMap().get(status.toLowerCase());

        page = page - 1 <= 0 ? 0 : page - 1;
        count = count < 1 ? 10 : count;

        Page<ClientOrder> orderPage = clientOrderService.fetchRegularClientOrderForClientOrderHistory(
                principal.getName(), orderStatus, page, count
        );

        model.addAttribute("orderPage", orderPage);
        model.addAttribute("fetched_by", orderStatus==null ? "all" : orderStatus.name().toLowerCase());

        return "/client/pages/account/my_order";
    }

    @RequestMapping("/order-details")
    public String orderDetails(Principal principal, Model model, @RequestParam(name = "q") long orderId){
        model.addAttribute("login", principal!=null);
        commonData.setCategoryList(model);
        commonData.setContactAndSocialInfo(model);
        ClientOrder clientOrder = clientOrderService.fetchClientOrderByClientEmailAndOrderId(principal.getName(),
                orderId);
        if(clientOrder==null){
            return "redirect:/error";
        }
        model.addAttribute("clientOrder", clientOrder);
        return "/client/pages/account/order_details";
    }

    @RequestMapping("/invoice")
    public String invoice(Principal principal, Model model, @RequestParam(name = "q") long orderId){
        commonData.setContactAndSocialInfo(model);
        try {
            ClientOrder orderInfo = clientOrderService.fetchClientOrderByClientEmailAndOrderId(principal.getName(),
                    orderId);
            if(orderInfo==null)
                return "redirect:/error";
            model.addAttribute("orderInfo", orderInfo);
        }catch (Exception e){
            LOG.warning("# Ex : " + e.getMessage());
            return "redirect:/error";
        }
        return "invoice";
    }

    @ResponseBody
    @RequestMapping("/order-cancel")
    public String cancelOrder(Principal principal, @RequestParam(name = "oid") long orderId){
        return clientOrderService.cancelPendingOrder(principal.getName(), orderId);
    }

}
