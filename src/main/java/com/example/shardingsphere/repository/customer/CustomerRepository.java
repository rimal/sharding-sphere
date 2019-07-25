package com.example.shardingsphere.repository.customer;

import com.example.shardingsphere.model.customer.Customer;
import com.example.shardingsphere.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rimal
 */
@Repository
public interface CustomerRepository extends BaseRepository<Customer, Long> {

  Customer findByClientIdAndId(Long clientId, Long customerId);

  List<Customer> findAllByOrderByIdDesc();
}
