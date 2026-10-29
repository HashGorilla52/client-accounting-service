package com.lde.clientaccountingservice.controller;

import com.lde.clientaccountingservice.annotation.ById;
import com.lde.clientaccountingservice.dto.ClientAccountResponse;
import com.lde.clientaccountingservice.dto.CreateClientAccountRequest;
import com.lde.clientaccountingservice.dto.UpdateClientAccountRequest;
import com.lde.clientaccountingservice.service.ClientAccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clients")
public class ClientAccountController {

    private final ClientAccountService clientAccountService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientAccountResponse createClientAccount(@Valid @RequestBody CreateClientAccountRequest request) {
        return clientAccountService.createClientAccount(request.fullName(), request.gender());
    }

    @ById()
    public ClientAccountResponse getClientAccount(@PathVariable long id) {
        return clientAccountService.getClientAccount(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ClientAccountResponse> getAllClientAccounts() {
        return clientAccountService.getAllClientAccounts();
    }

   @ById(method = RequestMethod.PUT)
    public ClientAccountResponse updateClientAccount(@PathVariable long id, @Valid @RequestBody UpdateClientAccountRequest request) {
        return clientAccountService.updateClientAccount(id, request.fullName(), request.gender(), request.status());
    }

   @ById(method = RequestMethod.DELETE, status = HttpStatus.NO_CONTENT)
    public void deleteClientAccount(@PathVariable long id) {
        clientAccountService.deleteClientAccount(id);
    }
}
