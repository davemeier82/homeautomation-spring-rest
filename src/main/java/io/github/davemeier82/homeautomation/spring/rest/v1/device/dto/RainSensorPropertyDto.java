/*
 * Copyright 2021-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.davemeier82.homeautomation.spring.rest.v1.device.dto;

import java.time.ZonedDateTime;

/**
 * @author David Meier
 * @since 0.4.0
 */
public class RainSensorPropertyDto extends DevicePropertyDto {
  private final Float rateInMmph;
  private final Float intervalMm;
  private final Float todayInMm;
  private final ZonedDateTime rateInMmphLastUpdated;
  private final ZonedDateTime intervalMmLastUpdated;
  private final ZonedDateTime todayInMmLastUpdated;

  public RainSensorPropertyDto(long id,
                               String label,
                               Float rateInMmph,
                               Float gustSpeedInKmh,
                               Float todayInMm,
                               ZonedDateTime rateInMmphLastUpdated,
                               ZonedDateTime intervalMmLastUpdated,
                               ZonedDateTime todayInMmLastUpdated
  ) {
    super(id, "RainSensor", label);
    this.rateInMmph = rateInMmph;
    this.intervalMm = gustSpeedInKmh;
    this.todayInMm = todayInMm;
    this.rateInMmphLastUpdated = rateInMmphLastUpdated;
    this.intervalMmLastUpdated = intervalMmLastUpdated;
    this.todayInMmLastUpdated = todayInMmLastUpdated;
  }

  public Float getRateInMmph() {
    return rateInMmph;
  }

  public Float getIntervalMm() {
    return intervalMm;
  }

  public Float getTodayInMm() {
    return todayInMm;
  }

  public ZonedDateTime getRateInMmphLastUpdated() {
    return rateInMmphLastUpdated;
  }

  public ZonedDateTime getIntervalMmLastUpdated() {
    return intervalMmLastUpdated;
  }

  public ZonedDateTime getTodayInMmLastUpdated() {
    return todayInMmLastUpdated;
  }
}
