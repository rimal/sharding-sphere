package com.example.shardingsphere.aop;

import io.shardingsphere.api.algorithm.sharding.ShardingValue;
import io.shardingsphere.core.rule.TableRule;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Rimal
 */
@Aspect
@Component
public class AopSystemArchitecture {

  @Pointcut("execution(* org.hibernate.engine.spi.SessionBuilderImplementor.openSession())")
  public void gettingSession() {
  }

  @Pointcut("execution(* io.shardingsphere.core.routing.type.standard.StandardRoutingEngine.routeDataSources(..)) && args(tableRule, databaseShardingValues)")
  public void routingDatasources(final TableRule tableRule, final List<ShardingValue> databaseShardingValues) {

  }

  /*@Pointcut("execution(* io.shardingsphere.core.routing.type.standard.StandardRoutingEngine.route())")
  public void routing() {

  }*/
}