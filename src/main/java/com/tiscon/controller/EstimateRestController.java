package com.tiscon.controller;

import com.tiscon.dto.UserOrderDto;
import com.tiscon.service.EstimateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class EstimateRestController {

    private final EstimateService estimateService;

    public EstimateRestController(EstimateService estimateService) {
        this.estimateService = estimateService;
    }
    @GetMapping("/submitEstimate")
    public EstimatedPrice estimatedPrice(@RequestParam Map<String, String> params) {
        UserOrderDto dto = new UserOrderDto();
        dto.setOldPrefectureId(params.get("oldPrefectureId"));
        dto.setNewPrefectureId(params.get("newPrefectureId"));
        dto.setBox(params.get("box"));
        dto.setBed(params.get("bed"));
        dto.setBicycle(params.get("bicycle"));
        dto.setWashingMachine(params.get("washingMachine"));
        dto.setWashingMachineInstallation(Boolean.valueOf(params.get("washingMachineInstallation")));

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
