package com.example.shardingsphere.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

/**
 * @author Rimal
 */
@Configuration
@PropertySource("classpath:/datasource.properties")
@ConfigurationProperties(prefix = "custom")
public class AppProperties {

  private Map<String, DatabaseCredentials> jdbc;

  public Map<String, DatabaseCredentials> getJdbc() {
    return jdbc;
  }

  public void setJdbc(Map<String, DatabaseCredentials> jdbc) {
    this.jdbc = jdbc;
  }

  public static class DatabaseCredentials {
    private String url;
    private String username;
    private String password;

    public String getUrl() {
      return url;
    }

    public void setUrl(String url) {
      this.url = url;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }
  }
}