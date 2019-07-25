package com.example.shardingsphere.model.customer;

import com.example.shardingsphere.constants.AppConstants;
import com.example.shardingsphere.model.encryption.CryptoConverter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Rimal
 */
@Entity
@Table(name = "customer")
/*-- do not copy as a filter is to be defined once only in an app --*/
@FilterDefs({
    @FilterDef(name = AppConstants.CLIENT_FILTER, parameters = {@ParamDef(name = AppConstants.CLIENT_FILTER_CLIENT_ID_PARAM, type = "long")})
})
/*-- --*/

@Filters({
    @Filter(name = AppConstants.CLIENT_FILTER, condition = "client_id = :" + AppConstants.CLIENT_FILTER_CLIENT_ID_PARAM)
})
public class Customer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "client_id")
  private Long clientId;

  @Column(name = "name")
  @Convert(converter = CryptoConverter.class)
  private String name;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }
}