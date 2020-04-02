package com.example.shardingsphere.aop.advice;

import com.example.shardingsphere.constants.AppConstants;
import com.example.shardingsphere.core.BotRequestContextHolder;
import com.example.shardingsphere.core.ClientContext;
import org.apache.shardingsphere.core.rule.TableRule;
import org.apache.shardingsphere.core.strategy.route.value.ListRouteValue;
import org.apache.shardingsphere.core.strategy.route.value.RouteValue;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Rimal
 */
@Aspect
public class SessionFilterAdvice {

  private static final Logger logger = LoggerFactory.getLogger(SessionFilterAdvice.class);

  @AfterReturning(
      pointcut = "com.example.shardingsphere.aop.AopSystemArchitecture.gettingSession()",
      returning = "session")
  public void setupFilter(Session session) {
    logger.trace("Inside setup filter method");

    /*if (!BotRequestContextHolder.getEnableFilterFlag()) {
      logger.info("Not enabling client filter as enable filter flag is false in context holder");
      return;
    }*/

    //set client filter param value
    ClientContext clientContext = BotRequestContextHolder.getClientContext();
    if (null != clientContext) {
      logger.trace("Enabling client filter with client id: " + clientContext.getClientId());
      Filter filter = session.enableFilter(AppConstants.CLIENT_FILTER);
      filter.setParameter(AppConstants.CLIENT_FILTER_CLIENT_ID_PARAM, clientContext.getClientId());
    } else {
      logger.info("Client context is null.");
    }
  }

  @Around(value = "com.example.shardingsphere.aop.AopSystemArchitecture.routingDatasources(tableRule,databaseShardingValues)",
      argNames = "tableRule,databaseShardingValues")
  @SuppressWarnings("unchecked")
  public Collection<String> routingFilter(ProceedingJoinPoint joinPoint, final TableRule tableRule, List<RouteValue> databaseShardingValues) throws Throwable {
    if (databaseShardingValues.isEmpty()) {
      logger.info("empty");

      ListRouteValue listShardingValue = new ListRouteValue("", "", null);
      databaseShardingValues = Collections.singletonList(listShardingValue);
    }

    return (Collection<String>) joinPoint.proceed(new Object[]{tableRule, databaseShardingValues});
  }

  /*@AfterReturning(
      pointcut = "com.example.shardingsphere.aop.AopSystemArchitecture.routing()",
      returning = "routingResult")
  public void routingFilter(RoutingResult routingResult) {
    logger.info("routingResult: " + routingResult);
  }*/
}