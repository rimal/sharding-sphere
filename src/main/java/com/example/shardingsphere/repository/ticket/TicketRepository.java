package com.example.shardingsphere.repository.ticket;

import com.example.shardingsphere.model.ticket.Ticket;
import com.example.shardingsphere.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Rimal
 */
@Repository
public interface TicketRepository extends BaseRepository<Ticket, Long> {

  List<Ticket> findAllByCustomerIdOrderByIdDesc(Long customerId);

  @Query("SELECT t FROM Ticket t JOIN Customer c ON c.id = t.customerId WHERE c.name = :customerName ORDER BY t.id desc")
  List<Ticket> findAllTicketsByCustomerName(@Param("customerName") String customerName);
}
