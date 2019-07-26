package com.example.shardingsphere.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author Rimal
 */
public class BotRequestContextHolder {

  private static final Logger logger = LoggerFactory.getLogger(BotRequestContextHolder.class);

  private static final ThreadLocal<ClientContext> clientContextHolder = new ThreadLocal<>();
  /**
   * if set to true, the messages will be inferred for a global context, rather than just in the context of the bot
   * running on the current ticket
   */
  private static final ThreadLocal<Boolean> globalConversationContextFlagHolder = ThreadLocal.withInitial(() -> Boolean.FALSE);
  private static final ThreadLocal<Boolean> enableFilterFlagHolder = ThreadLocal.withInitial(() -> Boolean.TRUE);


  /**
   * if set to @{@code true}, the data for the corresponding call will be fetched from the archived database.
   *
   * @see com.fbots.core.utils.jdbc.datasource.SpdRoutingDatasource
   *//*
  private static final ThreadLocal<Boolean> archivedDataRequestFlagHolder = ThreadLocal.withInitial(() -> Boolean.FALSE);*/

  /**
   * holds the timezone offset, in minutes, for the current request
   */
  private static final ThreadLocal<Integer> timezoneOffsetHolder = ThreadLocal.withInitial(() -> 0);

  // holds the current date to calculate the processing time
  private static final ThreadLocal<Date> initProcessingTimeHolder = ThreadLocal.withInitial(Date::new);

  // holds the api request name that will be used in logging the processing time
  private static final ThreadLocal<String> apiRequestNameHolder = new ThreadLocal<>();

  public static void resetBotRequestContextHolder() {
    clientContextHolder.remove();
    globalConversationContextFlagHolder.remove();
    enableFilterFlagHolder.remove();
    timezoneOffsetHolder.remove();
    initProcessingTimeHolder.remove();
    apiRequestNameHolder.remove();
  }

  public static void setClientContext(ClientContext clientContext) {
    clientContextHolder.set(clientContext);
    logger.info("Client context set to " + getClientId());
//    LogHelper.addVariableToLogContext(LogConstants.Variable.CLIENT_HASH, getClientHash());
  }


  public static ClientContext getClientContext() {
    return clientContextHolder.get();
  }

  public static String getClientHash() {
    String clientHash = null;

    ClientContext clientContext = getClientContext();
    if (null != clientContext) {
      clientHash = clientContext.getClientHash();
    }
    return clientHash;
  }

  /*public static String getProductHash() {
    String productHash = null;

    ClientContext clientContext = getClientContext();
    if (null != clientContext) {
      if (null != clientContext.getProductId()) {
        ProductVO productVO = ProductJvmCache.getInstance().getProductById(clientContext.getProductId());
        if (null != productVO) {
          productHash = productVO.getProductHash();
        }
      }
    }
    return productHash;
  }*/

  public static Long getClientId() {
    Long clientId = null;

    ClientContext clientContext = getClientContext();
    if (null != clientContext) {
      clientId = clientContext.getClientId();
    }
    return clientId;
  }

  public static void setGlobalConversationContextFlag(boolean globalConversationContext) {
    globalConversationContextFlagHolder.set(globalConversationContext);
  }

  public static boolean isGlobalConversationContext() {
    return globalConversationContextFlagHolder.get();
  }

  /**
   * If not sure about session creation, then call BaseDao's disableClientFilterIfNeeded and enableClientFilter
   *
   * @param enable
   */
  public static void setEnableFilterFlag(boolean enable) {
    logger.debug("Setting enable flag as: " + enable + (null != getClientContext() ? "; request url: " + getClientContext().getRequestUrl() : ""));
    enableFilterFlagHolder.set(enable);
  }

  public static boolean getEnableFilterFlag() {
    return enableFilterFlagHolder.get();
  }


  /*public static void setArchivedDataRequestFlag(boolean archivedDataRequest) {
    if (archivedDataRequest) {
      logger.debug("Request received for archived data");
    }

    archivedDataRequestFlagHolder.set(archivedDataRequest);
  }

  public static boolean isArchivedDataRequest() {
    return archivedDataRequestFlagHolder.get();
  }*/

  public static void setTimezoneOffset(Integer offset) {
    timezoneOffsetHolder.set(offset);
  }

  public static int getTimezoneOffset() {
    return timezoneOffsetHolder.get();
  }

  public static Date getInitProcessingTime() {
    return initProcessingTimeHolder.get();
  }

  public static void setInitProcessingTime(Date initProcessingTime) {
    initProcessingTimeHolder.set(initProcessingTime);
  }

  public static String getApiRequestNameHolder() {
    return apiRequestNameHolder.get();
  }

  public static void setApiRequestNameHolder(String apiRequestName) {
    apiRequestNameHolder.set(apiRequestName);
  }
}