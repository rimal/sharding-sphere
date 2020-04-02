package com.example.shardingsphere.config.sharding;

import com.example.shardingsphere.core.BotRequestContextHolder;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Rimal
 */
public class AppShardingAlgorithm implements ComplexKeysShardingAlgorithm<Long> {

  private static final Logger logger = LoggerFactory.getLogger(AppShardingAlgorithm.class);

  private static final Map<Long, List<Integer>> CLIENT_TO_DS_NUMBER = new HashMap<Long, List<Integer>>() {{
    put(1L, Collections.singletonList(1));
    put(2L, Collections.singletonList(1));
    put(3L, Collections.singletonList(2));
    put(4L, Collections.singletonList(3));
    put(5L, Collections.singletonList(3));
    put(6L, Collections.singletonList(4));
  }};

  @Override
  public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<Long> shardingValue) {
    List<String> applicableTargetNames = null;

    List<Long> clientIdList = new ArrayList<>();

    if (shardingValue.getColumnNameAndShardingValuesMap().containsKey("client_id")) {
      clientIdList = new ArrayList<>(shardingValue.getColumnNameAndShardingValuesMap().get("client_id"));
    }

    if (clientIdList.isEmpty()) {
      clientIdList = Collections.singletonList(BotRequestContextHolder.getClientId());
    }

    if (null != clientIdList && clientIdList.size() > 0) {
      applicableTargetNames = clientIdList.stream()
          .filter(clientId -> null != CLIENT_TO_DS_NUMBER.get(clientId) && CLIENT_TO_DS_NUMBER.get(clientId).size() > 0)
          .flatMap(clientId -> CLIENT_TO_DS_NUMBER.get(clientId).stream().map(dsNumber -> "speedy-" + dsNumber))
          .collect(Collectors.toList());
    }

    if (null == applicableTargetNames) {
      applicableTargetNames = new ArrayList<>(availableTargetNames);
      logger.error("Could not find applicable shard. Executing query on all shards");
    }

    return applicableTargetNames;
  }

  /*@Override
  @SuppressWarnings("unchecked")
  public Collection<String> doSharding(Collection<String> availableTargetNames, Collection<ShardingValue> shardingValues) {
    List<String> applicableTargetNames = null;

    if (null != shardingValues && shardingValues.size() > 0) {
      ListShardingValue<Long> clientIdShardingValue = (ListShardingValue<Long>) shardingValues.stream()
          .filter(shardingValue -> shardingValue.getColumnName().equalsIgnoreCase("client_id"))
          .findFirst().orElse(null);

      List<Long> clientIdList = null;

      if (null != clientIdShardingValue) {
        clientIdList = new ArrayList<>(clientIdShardingValue.getValues());
      } else if (null != BotRequestContextHolder.getClientId()) {
        clientIdList = Collections.singletonList(BotRequestContextHolder.getClientId());
      }

      if (null != clientIdList && clientIdList.size() > 0) {
        applicableTargetNames = clientIdList.stream()
            .filter(clientId -> null != CLIENT_TO_DS_NUMBER.get(clientId) && CLIENT_TO_DS_NUMBER.get(clientId).size() > 0)
            .flatMap(clientId -> CLIENT_TO_DS_NUMBER.get(clientId).stream().map(dsNumber -> "speedy-" + dsNumber))
            .collect(Collectors.toList());
      }
    }

    if (null == applicableTargetNames) {
      applicableTargetNames = new ArrayList<>(availableTargetNames);
      logger.error("Could not find applicable shard. Executing query on all shards");
    }

    return applicableTargetNames;
  }*/
}