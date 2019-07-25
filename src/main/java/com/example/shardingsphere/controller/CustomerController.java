package com.example.shardingsphere.controller;

import com.example.shardingsphere.model.customer.Customer;
import com.example.shardingsphere.service.customer.CustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Rimal
 */
@RestController
@RequestMapping("/customers")
public class CustomerController {

  private final CustomerServiceImpl customerService;

  @Autowired
  public CustomerController(
      CustomerServiceImpl customerService
  ) {
    this.customerService = customerService;
  }

  @GetMapping("/get/{clientId}")
  public ResponseEntity<Customer> getCustomer(
      @PathVariable Long clientId,
      @RequestParam(value = "id") Long id
  ) {
    Customer customer = customerService.getCustomer(clientId, id);
    return new ResponseEntity<>(customer, HttpStatus.OK);
  }

  @GetMapping("/get-all")
  public ResponseEntity<List<Customer>> getAllCustomers() {
    List<Customer> customers = customerService.getAllCustomers();
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @GetMapping("/create/{clientId}")
  public ResponseEntity<Customer> createCustomer(
      @PathVariable Long clientId,
      @RequestParam(value = "name") String name
  ) {
    Customer customer = customerService.createCustomer(clientId, name);
    return new ResponseEntity<>(customer, HttpStatus.OK);
  }
}