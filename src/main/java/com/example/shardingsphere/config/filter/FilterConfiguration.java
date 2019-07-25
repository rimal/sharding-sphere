package com.example.shardingsphere.config.filter;

import com.example.shardingsphere.filter.client.ClientFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Rimal
 */
@Configuration
public class FilterConfiguration {

  @Bean
  public FilterRegistrationBean accountFilterRegBean(ClientFilter clientFilter) {
    FilterRegistrationBean<ClientFilter> registrationBean = new FilterRegistrationBean<>(clientFilter);
//    registrationBean.setOrder(securityProperties.getFilter().getOrder() - 50);

    registrationBean.setAsyncSupported(true);

    return registrationBean;
  }
}