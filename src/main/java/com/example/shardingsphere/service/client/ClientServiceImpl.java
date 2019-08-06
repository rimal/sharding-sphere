package com.example.shardingsphere.service.client;

import com.example.shardingsphere.model.client.Client;
import com.example.shardingsphere.repository.client.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Rimal
 */
@Service
public class ClientServiceImpl {

  private final ClientRepository clientRepository;

  @Autowired
  public ClientServiceImpl(
      ClientRepository clientRepository
  ) {
    this.clientRepository = clientRepository;
  }

  @Transactional
  public Client createClient(String name) {
    Client client = new Client();
    client.setName(name);

    clientRepository.create(client);
    return client;
  }
}