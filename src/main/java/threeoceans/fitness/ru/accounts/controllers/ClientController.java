package threeoceans.fitness.ru.accounts.controllers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/subscriptions/info")
    public List<SubscriptionResponse> getClientSubscriptions(@RequestHeader(name="login")String login){
        return  clientAccountService.getClientSubscriptions(login);
    }


    @PostMapping("/subscriptions/add")
    public void addSubscription(@RequestBody SubscriptionToProductRequest subRequest){
        clientAccountService.addSubscription(subRequest);
    }


    @PostMapping("/subscriptions/unsubscribe/{subId}")
    public void unsubscribeEvent(@PathVariable (name="subId")Long subId){
        clientAccountService.unsubscribeAtEvent(subId);
    }

    @PostMapping("/subscriptions/subscribe")
    public ResponseEntity<?> subscribeEvent(@RequestBody SubScheduleRequest subRequest){
        return clientAccountService.subscribeAtEvent(subRequest.getLogin(),subRequest.getDiscipline());
    }
    @PostMapping("subscriptions/confirm/{subId}")
    public void confirmArrival(@PathVariable(name = "subId")Long subId){
        clientAccountService.confirmWorkout(subId);
    }

    @PostMapping("/subscriptions/change")
    public void changeNumOfWOuts(
            @RequestParam(name = "login")String login,
            @RequestParam("discipline")String disciplineName,
            @RequestParam("delta")int delta
    ){
        try{
            clientAccountService.changeNumOfWorkouts(login,disciplineName,delta);
        }catch (Exception e){

        }

    }

}
