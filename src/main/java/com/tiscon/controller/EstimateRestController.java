package com.tiscon.controller;

import com.tiscon.dto.UserOrderDto;
import com.tiscon.form.EstimateOrderForm;
import com.tiscon.service.EstimateService;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EstimateRestController {

    private final EstimateService estimateService;

    public EstimateRestController(EstimateService estimateService) {
        this.estimateService = estimateService;
    }
    @GetMapping("/submitEstimate")
    public EstimatedPrice estimatedPrice(@Validated EstimateOrderForm estimateOrderForm) {
        UserOrderDto dto = new UserOrderDto();
        BeanUtils.copyProperties(estimateOrderForm, dto);

        Integer price = this.estimateService.getPrice(dto);

        return new EstimatedPrice(price);
    }

    public static class EstimatedPrice{
        private final int price;

        public EstimatedPrice(int price) {
            this.price = price;
        }

        public int getPrice() {
            return this.price;
        }
    }
}
