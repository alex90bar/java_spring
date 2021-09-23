package com.example.external;

import com.example.time_serv.times.TimeProviderProperties;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@ConditionalOnProperty(value = "spring.profiles.active",
    havingValue = "prod", matchIfMissing = true)  //если свойство отсутствует, будет запущено с дефолтным профилем
//@Profile("prod")
public class TimeService implements TimeServiceInterface{

  private final TimeProviderProperties timeProviderProperties;


  @Autowired
  public TimeService(TimeProviderProperties timeProviderProperties) {
    this.timeProviderProperties = timeProviderProperties;
  }


  public void printCurrentTime() {
    SimpleDateFormat dateFormat = new SimpleDateFormat(timeProviderProperties.getFormat());
    Logger.getLogger(TimeService.class.getName()).info(timeProviderProperties.getDescription());
    Logger.getLogger(TimeService.class.getName()).info("current time: " + dateFormat.format(new Date()));
  }
}
