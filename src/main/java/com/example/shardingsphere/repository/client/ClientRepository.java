package com.example.shardingsphere.repository.client;

import com.example.shardingsphere.model.client.Client;
import com.example.shardingsphere.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rimal
 */
@Repository
public interface ClientRepository extends BaseRepository<Client, Long> {
}
