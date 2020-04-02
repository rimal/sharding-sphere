package com.example.shardingsphere.aop;

import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.core.strategy.route.value.RouteValue;
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

  @Pointcut(value = "execution(* org.apache.shardingsphere.core.route.type.standard.StandardRoutingEngine.routeDataSources(..)) && args(tableRule, databaseShardingValues)",
      argNames = "tableRule,databaseShardingValues")
  public void routingDatasources(final TableRule tableRule, final List<RouteValue> databaseShardingValues) {

  }

  /*@Pointcut("execution(* io.shardingsphere.core.routing.type.standard.StandardRoutingEngine.route())")
  public void routing() {

  }*/
}