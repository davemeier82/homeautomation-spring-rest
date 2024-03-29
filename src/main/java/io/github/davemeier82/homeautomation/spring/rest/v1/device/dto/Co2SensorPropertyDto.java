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
 * @since 0.3.0
 */
public class Co2SensorPropertyDto extends DevicePropertyDto {
  private final Integer ppm;
  private final ZonedDateTime lastUpdated;

  public Co2SensorPropertyDto(long id, String label, Integer ppm, ZonedDateTime lastUpdated) {
    super(id, "Co2Sensor", label);
    this.ppm = ppm;
    this.lastUpdated = lastUpdated;
  }

  public Integer getPpm() {
    return ppm;
  }

  public ZonedDateTime getLastUpdated() {
    return lastUpdated;
  }
}
