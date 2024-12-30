package com.patroclos.cqrs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Order(0)
class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

  @Override
  public void onApplicationEvent(ApplicationReadyEvent ignored) {
    log.info("****************************************");
    log.info("*                                      *");
    log.info("*      CQRS service started            *");
    log.info("*                                      *");
    log.info("****************************************");
  }

}