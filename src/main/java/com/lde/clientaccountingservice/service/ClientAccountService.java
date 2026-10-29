package com.lde.clientaccountingservice.service;

import com.lde.clientaccountingservice.dto.ClientAccountResponse;
import com.lde.clientaccountingservice.entity.ClientAccount;
import com.lde.clientaccountingservice.entity.ClientStatus;
import com.lde.clientaccountingservice.entity.Gender;
import com.lde.clientaccountingservice.exception.ResourceNotFoundException;
import com.lde.clientaccountingservice.repository.ClientAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ClientAccountService {

    private final ClientAccountRepository clientAccountRepository;

    public ClientAccountResponse createClientAccount(String fullName, Gender gender) {
        ClientAccount client = new ClientAccount();
        client.setFullName(fullName);
        client.setGender(gender);
        client.setStatus(ClientStatus.ACTIVE);
        return toResponse(clientAccountRepository.save(client));
    }

    public List<ClientAccountResponse> getAllClientAccounts() {
        return clientAccountRepository.findAll().stream().map(this::toResponse).toList();
    }

    public ClientAccountResponse getClientAccount(long id) {
        return toResponse(findClient(id));
    }

    public ClientAccountResponse updateClientAccount(long id, String fullName, Gender gender, ClientStatus status) {
        ClientAccount client = findClient(id);
        updateFullNameIfNotNull(client, fullName);
        client.setGender(gender);
        updateStatusIfNotNull(client, status);
        return toResponse(client);
    }

    public void deleteClientAccount(long id) {
        ClientAccount client = findClient(id);
        clientAccountRepository.delete(client);
    }

    private ClientAccountResponse toResponse(ClientAccount client) {
        return new ClientAccountResponse(client.getId(),
                client.getFullName(),
                client.getGender(),
                client.getStatus(),
                client.getCreateDttm(),
                client.getModifyDttm());
    }

    private ClientAccount findClient(long id) {
        return clientAccountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Client with id " + id + " not found"));
    }

    private void updateFullNameIfNotNull(ClientAccount client, String fullName) {
        if (fullName != null && !fullName.isBlank()) { client.setFullName(fullName);}
    }

    private void updateStatusIfNotNull(ClientAccount client, ClientStatus status) {
        if (status != null) { client.setStatus(status);}
    }
}