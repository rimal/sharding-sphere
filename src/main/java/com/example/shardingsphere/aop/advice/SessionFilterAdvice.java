package com.example.shardingsphere.aop.advice;

import com.example.shardingsphere.constants.AppConstants;
import com.example.shardingsphere.core.BotRequestContextHolder;
import com.example.shardingsphere.core.ClientContext;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Filter;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}