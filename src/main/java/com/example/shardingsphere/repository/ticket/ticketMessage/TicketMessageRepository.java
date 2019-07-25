package com.example.shardingsphere.repository.ticket.ticketMessage;

import com.example.shardingsphere.model.ticket.ticketMessage.TicketMessage;
import com.example.shardingsphere.repository.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Rimal
 */
@Repository
public interface TicketMessageRepository extends BaseRepository<TicketMessage, Long> {
}
