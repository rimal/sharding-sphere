package com.example.shardingsphere.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author Rimal
 */
@Aspect
@Component
public class AopSystemArchitecture {

  @Pointcut("execution(* org.hibernate.engine.spi.SessionBuilderImplementor.openSession())")
  public void gettingSession() {
  }
}