package com.example.shardingsphere.model.ticket;

import com.example.shardingsphere.constants.AppConstants;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Rimal
 */
@Entity
@Table(name = "ticket")
@Filters({
    @Filter(name = AppConstants.CLIENT_FILTER, condition = "client_id = :" + AppConstants.CLIENT_FILTER_CLIENT_ID_PARAM)
})
public class Ticket {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "client_id")
  private Long clientId;

  @Column(name = "customerId")
  private Long customerId;

  @Column(name = "ticket_status_id")
  private Integer ticketStatusId = 10;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "create_date")
  private Date createDate = new Date();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getClientId() {
    return clientId;
  }

  public void setClientId(Long clientId) {
    this.clientId = clientId;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public Integer getTicketStatusId() {
    return ticketStatusId;
  }

  public void setTicketStatusId(Integer ticketStatusId) {
    this.ticketStatusId = ticketStatusId;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
}