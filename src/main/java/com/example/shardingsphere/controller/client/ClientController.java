package com.example.shardingsphere.controller.client;

import com.example.shardingsphere.model.client.Client;
import com.example.shardingsphere.service.client.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Rimal
 */
@RestController
@RequestMapping("/clients")
public class ClientController {

  private final ClientServiceImpl clientService;

  @Autowired
  public ClientController(
      ClientServiceImpl clientService
  ) {
    this.clientService = clientService;
  }

  @GetMapping("/create/{name}")
  public ResponseEntity<Client> createClient(@PathVariable String name) {
    Client client = clientService.createClient(name);
    return new ResponseEntity<>(client, HttpStatus.OK);
  }
}