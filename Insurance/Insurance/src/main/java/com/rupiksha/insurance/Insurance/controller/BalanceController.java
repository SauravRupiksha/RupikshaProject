package com.rupiksha.insurance.Insurance.controller;




import com.rupiksha.insurance.Insurance.service.VenusBalanceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bbps")
public class BalanceController {

    private final VenusBalanceService balanceService;

    public BalanceController(VenusBalanceService balanceService) {
        this.balanceService = balanceService;
    }

    @GetMapping("/balance")
    public String getBalance(@RequestParam String service) {
        return balanceService.checkBalance(service);
    }
}
