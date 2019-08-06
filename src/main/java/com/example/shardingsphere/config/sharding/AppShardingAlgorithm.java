package com.example.shardingsphere.config.sharding;

import io.shardingsphere.api.algorithm.sharding.ListShardingValue;
import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.api.algorithm.sharding.complex.ComplexKeysShardingAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Rimal
 */
public class AppShardingAlgorithm implements ComplexKeysShardingAlgorithm {

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
  @SuppressWarnings("unchecked")
  public Collection<String> doSharding(Collection<String> availableTargetNames, Collection<ShardingValue> shardingValues) {
    List<String> applicableTargetNames = null;

    if (null != shardingValues && shardingValues.size() > 0) {
      ListShardingValue<Long> clientIdShardingValue = (ListShardingValue<Long>) shardingValues.stream()
          .filter(shardingValue -> shardingValue.getColumnName().equalsIgnoreCase("client_id"))
          .findFirst().orElse(null);
      if (null != clientIdShardingValue) {
        Long clientId = clientIdShardingValue.getValues().stream().findFirst().orElse(null);
        if (null != clientId) {
          if (null != CLIENT_TO_DS_NUMBER.get(clientId) && CLIENT_TO_DS_NUMBER.get(clientId).size() > 0) {
            applicableTargetNames = Collections.singletonList("speedy-" + CLIENT_TO_DS_NUMBER.get(clientId).get(0));
          }
        }
      }
    }

    if (null == applicableTargetNames) {
      applicableTargetNames = new ArrayList<>(availableTargetNames);
      logger.error("Could not find applicable shard. Executing query on all shards");
    }

    return applicableTargetNames;
  }
}