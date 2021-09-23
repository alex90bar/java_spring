package com.example.external.dev;

import com.example.external.TimeServiceInterface;
import java.util.logging.Logger;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "dev")
public class DevTimeService implements TimeServiceInterface {

  @Override
  public void printCurrentTime() {
    Logger.getLogger(DevTimeService.class.getName()).info("UNDER CONSTRUCTION!");
  }
}
