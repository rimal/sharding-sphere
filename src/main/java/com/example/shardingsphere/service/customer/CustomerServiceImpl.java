package com.example.shardingsphere.service.customer;

import com.example.shardingsphere.core.BotRequestContextHolder;
import com.example.shardingsphere.model.customer.Customer;
import com.example.shardingsphere.repository.customer.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rimal
 */
@Service
public class CustomerServiceImpl {

  private final CustomerRepository customerRepository;

  @Autowired
  public CustomerServiceImpl(
      CustomerRepository customerRepository
  ) {
    this.customerRepository = customerRepository;
  }

  @Transactional
  public Customer createCustomer(String name) {
    Customer customer = new Customer();
    customer.setClientId(BotRequestContextHolder.getClientId());
    customer.setName(name);

    customerRepository.create(customer);
    return customer;
  }

  @Transactional
  public Customer updateCustomer(Long customerId, String name) {
    Customer customer = getCustomer(customerId);
    //customer.setClientId(BotRequestContextHolder.getClientId());
    customer.setName(name);

    customerRepository.update(customer);
    return customer;
  }

  public Customer getCustomer(Long customerId) {
    return customerRepository.getById(customerId);
  }

  public Customer getCustomer(Long clientId, Long customerId) {
    return customerRepository.findByClientIdAndId(clientId, customerId);
  }

  public List<Customer> getAllCustomers() {
    return customerRepository.findAllByOrderByIdDesc();
  }
}