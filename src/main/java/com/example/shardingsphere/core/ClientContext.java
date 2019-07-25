package com.example.shardingsphere.core;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author Rimal
 */
public class ClientContext {

  private String clientHash;
  private Long clientId;
  private String externalClientId;
  private String requestUrl;
  private Long productId;


  public String getClientHash() {
    return clientHash;
  }

  public void setClientHash(String clientHash) {
    this.clientHash = clientHash;
  }

  public Long getClientId() {
    return clientId;
  }

  public void setClientId(Long clientId) {
    this.clientId = clientId;
  }

  public String getExternalClientId() {
    return externalClientId;
  }

  public void setExternalClientId(String externalClientId) {
    this.externalClientId = externalClientId;
  }

  public String getRequestUrl() {
    return requestUrl;
  }

  public void setRequestUrl(String requestUrl) {
    this.requestUrl = requestUrl;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o instanceof ClientContext) {
      ClientContext clientContext = (ClientContext) o;
      return new EqualsBuilder()
          .append(this.getClientId(), clientContext.getClientId())
          .append(this.getClientHash(), clientContext.getClientHash())
          .isEquals();
    }

    return false;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
        .append(this.getClientId())
        .append(this.getClientHash())
        .toHashCode();
  }
}