package com.example.shardingsphere.config.persistence;

import com.example.shardingsphere.config.properties.AppProperties;
import com.example.shardingsphere.config.sharding.AppShardingAlgorithm;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Rimal
 */
@Configuration
public class AppPersistenceConfiguration {

  private final AppProperties appProperties;

  @Autowired
  public AppPersistenceConfiguration(
      AppProperties appProperties
  ) {
    this.appProperties = appProperties;
  }


  @Primary
  @Bean
  public DataSource getShardingDataSource() throws SQLException {
    ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();

    shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new ComplexShardingStrategyConfiguration("client_id", new AppShardingAlgorithm()));

    shardingRuleConfig.getTableRuleConfigs().add(getTableRuleConfiguration("customer"));
    shardingRuleConfig.getTableRuleConfigs().add(getTableRuleConfiguration("ticket"));

    TableRuleConfiguration clientTableRuleConfig = getTableRuleConfiguration("client");
    clientTableRuleConfig.setDatabaseShardingStrategyConfig(new ComplexShardingStrategyConfiguration("id", new AppShardingAlgorithm()));
    shardingRuleConfig.getTableRuleConfigs().add(clientTableRuleConfig);

//    shardingRuleConfig.getBindingTableGroups().add("t_order, t_order_item");

    return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, new Properties() {{
      put("sql.show", true);
    }});
  }

  private TableRuleConfiguration getTableRuleConfiguration(String tableName) {
    TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration(tableName, "speedy-$->{1..4}." + tableName);
    //tableRuleConfiguration.setLogicTable(tableName);
    //tableRuleConfiguration.setActualDataNodes("speedy-$->{1..4}." + tableName);


    //tableRuleConfiguration.setKeyGeneratorColumnName("id");

    return tableRuleConfiguration;
  }

  private Map<String, DataSource> createDataSourceMap() {
    Map<String, DataSource> result = new HashMap<>();

    appProperties.getJdbc().forEach((dsName, credentials) -> {
      result.put(dsName, getHikariDataSource(credentials));
    });

    return result;
  }

  private HikariDataSource getHikariDataSource(AppProperties.DatabaseCredentials credentials) {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    /*dataSource.setMaximumPoolSize(spdDatasourceProperties.getMaster().getMaxPoolSize());
    dataSource.setMinimumIdle(spdDatasourceProperties.getMaster().getMinIdle());
    dataSource.setIdleTimeout(spdDatasourceProperties.getMaster().getIdleTimeout());
    dataSource.setConnectionTimeout(spdDatasourceProperties.getMaster().getConnectionTimeout());
    dataSource.setTransactionIsolation(TRANSACTION_LEVEL);*/

    dataSource.setJdbcUrl(credentials.getUrl());
    dataSource.setUsername(credentials.getUsername());
    dataSource.setPassword(credentials.getPassword());

    dataSource.setAutoCommit(false);

    return dataSource;
  }
}
