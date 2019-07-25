package com.example.shardingsphere.filter.client;

import com.example.shardingsphere.core.BotRequestContextHolder;
import com.example.shardingsphere.core.ClientContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Rimal
 */
@Component
public class ClientFilter extends GenericFilterBean {

  private static final Logger logger = LoggerFactory.getLogger(ClientFilter.class);

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain
      chain) throws IOException, ServletException {
    //skip non-http requests
    if (!(request instanceof HttpServletRequest)) {
      chain.doFilter(request, response);
      return;
    }
    HttpServletRequest httpReq = (HttpServletRequest) request;

    if (httpReq.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())) {
      chain.doFilter(request, response);
      return;
    }

    if (
        httpReq.getRequestURI().equals("")
            || httpReq.getRequestURI().equals("/")
            || httpReq.getRequestURI().matches("^/hello.*")
            || httpReq.getRequestURI().matches("/error/?")

            //static resource excludes
            || httpReq.getRequestURI().matches("/assets.*")
            || httpReq.getRequestURI().matches("/favicon.ico/?")
            || httpReq.getRequestURI().matches("/.*.css")
            || httpReq.getRequestURI().matches("/.*.js")

            //swagger excludes
            || httpReq.getRequestURI().matches(".*swagger.*")
            || httpReq.getRequestURI().matches("/groups/?")
            || httpReq.getRequestURI().matches("/v2/api-docs/?")

//            || httpReq.getRequestURI().startsWith(RestConstants.Url.ACCOUNT_PREFIX)
    ) {
      chain.doFilter(request, response);
      return;
    }

    if (StringUtils.isNotBlank(httpReq.getHeader("client-id"))) {
      ClientContext clientContext = new ClientContext();
      clientContext.setClientId(Long.valueOf(httpReq.getHeader("client-id")));

      if (null == BotRequestContextHolder.getClientContext() || !clientContext.equals(BotRequestContextHolder.getClientContext())) {
        logger.debug("Setting client: " + clientContext.getClientId() + " from url: " + httpReq.getRequestURL());
      }

      BotRequestContextHolder.setClientContext(clientContext);
      BotRequestContextHolder.setEnableFilterFlag(true);
    } else {
      logger.info("No client-id header found in request");
    }

    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
  }
}