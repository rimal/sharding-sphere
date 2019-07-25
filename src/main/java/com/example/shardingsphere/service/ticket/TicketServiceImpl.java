package com.example.shardingsphere.service.ticket;

import com.example.shardingsphere.core.BotRequestContextHolder;
import com.example.shardingsphere.model.ticket.Ticket;
import com.example.shardingsphere.repository.ticket.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Rimal
 */
@Service
public class TicketServiceImpl {

  private final TicketRepository ticketRepository;

  @Autowired
  public TicketServiceImpl(
      TicketRepository ticketRepository
  ) {
    this.ticketRepository = ticketRepository;
  }

  @Transactional
  public Ticket createTicket(Long customerId) {
    Ticket ticket = new Ticket();
    ticket.setClientId(BotRequestContextHolder.getClientId());
    ticket.setCustomerId(customerId);

    ticketRepository.create(ticket);
    return ticket;
  }

  public List<Ticket> getTickets(Long customerId) {
    return ticketRepository.findAllByCustomerIdOrderByIdDesc(customerId);
  }

  public List<Ticket> getTickets(String customerName) {
    return ticketRepository.findAllTicketsByCustomerName(customerName);
  }
}