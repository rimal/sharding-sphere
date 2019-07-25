package com.example.shardingsphere.controller.ticket;

import com.example.shardingsphere.model.ticket.Ticket;
import com.example.shardingsphere.service.ticket.TicketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Rimal
 */
@RestController
@RequestMapping("/tickets")
public class TicketController {

  private final TicketServiceImpl ticketService;

  @Autowired
  public TicketController(
      TicketServiceImpl ticketService
  ) {
    this.ticketService = ticketService;
  }

  /*@GetMapping("/{id}")
  public ResponseEntity<Ticket> getTicket(@PathVariable Long id) {
    Ticket ticket = ticketService.getTicket(id);
    return new ResponseEntity<>(ticket, HttpStatus.OK);
  }*/

  @GetMapping("/{customerId}")
  public ResponseEntity<List<Ticket>> getTickets(@PathVariable Long customerId) {
    List<Ticket> tickets = ticketService.getTickets(customerId);
    return new ResponseEntity<>(tickets, HttpStatus.OK);
  }

  @GetMapping("")
  public ResponseEntity<List<Ticket>> getTickets(@RequestParam(name = "customer-name") String customerName) {
    List<Ticket> tickets = ticketService.getTickets(customerName);
    return new ResponseEntity<>(tickets, HttpStatus.OK);
  }

  @GetMapping("/create/{customerId}")
  public ResponseEntity<Ticket> createTicket(
      @PathVariable Long customerId
  ) {
    Ticket ticket = ticketService.createTicket(customerId);
    return new ResponseEntity<>(ticket, HttpStatus.OK);
  }
}