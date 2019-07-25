package com.example.shardingsphere.repository.ticket;

import com.example.shardingsphere.model.ticket.Ticket;
import com.example.shardingsphere.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rimal
 */
@Repository
public interface TicketRepository extends BaseRepository<Ticket, Long> {
}
