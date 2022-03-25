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
 * @since 0.1.0
 */
public class MotionSensorPropertyDto extends DevicePropertyDto {
  private final Boolean motionDetected;
  private final ZonedDateTime lastUpdated;

  public MotionSensorPropertyDto(long id, Boolean motionDetected, ZonedDateTime lastUpdated) {
    super(id, "MotionSensor");
    this.motionDetected = motionDetected;
    this.lastUpdated = lastUpdated;
  }

  public Boolean getMotionDetected() {
    return motionDetected;
  }

  public ZonedDateTime getLastUpdated() {
    return lastUpdated;
  }
}
