package threeoceans.fitness.ru.accounts.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import threeoceans.fitness.ru.accounts.dto.*;
import threeoceans.fitness.ru.accounts.services.ClientAccountService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientAccountService clientAccountService;


    @GetMapping("accounts/info")
    public ClientInfoResponse getClientInfo(@RequestHeader(name = "login")String login ){
        return clientAccountService.getClientInfo(login);
    }


    @PostMapping("/accounts/info/update")
    public void addAccount(@RequestBody ClientInfoRequest clientAccountDto ){
        clientAccountService.createOrUpdateAccount(clientAccountDto);
    }

    @PostMapping("/subscriptions/info")
    public List<SubscriptionResponse> getClientSubscriptions(@RequestHeader(name="login")String login){
        return  clientAccountService.getClientSubscriptions(login);
    }


    @PostMapping("/subscriptions/add")
    public void addSubscription(@RequestHeader(name = "login")String login,  @RequestBody SubscriptionDto subDto){}

    @PostMapping("/subscriptions/change")
    public void changeNumOfWOuts(
            @RequestHeader(name = "login")String login,
            @RequestParam("discipline")String disciplineName,
            @RequestParam("delta")int delta
    ){}

}
