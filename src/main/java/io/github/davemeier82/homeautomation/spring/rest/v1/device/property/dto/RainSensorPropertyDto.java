/*
 * Copyright 2021-2024 the original author or authors.
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

package io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto;

import java.time.OffsetDateTime;

public class RainSensorPropertyDto extends DevicePropertyDto {
  private final Float rateInMmph;
  private final Float intervalMm;
  private final Float todayInMm;
  private final OffsetDateTime rateInMmphLastUpdated;
  private final OffsetDateTime intervalMmLastUpdated;
  private final OffsetDateTime todayInMmLastUpdated;

  public RainSensorPropertyDto(String id,
                               String type,
                               String displayName,
                               Float rateInMmph,
                               Float gustSpeedInKmh,
                               Float todayInMm,
                               OffsetDateTime rateInMmphLastUpdated,
                               OffsetDateTime intervalMmLastUpdated,
                               OffsetDateTime todayInMmLastUpdated
  ) {
    super(id, type, displayName);
    this.rateInMmph = rateInMmph;
    intervalMm = gustSpeedInKmh;
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

  public OffsetDateTime getRateInMmphLastUpdated() {
    return rateInMmphLastUpdated;
  }

  public OffsetDateTime getIntervalMmLastUpdated() {
    return intervalMmLastUpdated;
  }

  public OffsetDateTime getTodayInMmLastUpdated() {
    return todayInMmLastUpdated;
  }
}
