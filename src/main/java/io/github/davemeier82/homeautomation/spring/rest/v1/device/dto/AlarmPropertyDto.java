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

import io.github.davemeier82.homeautomation.core.device.property.AlarmState;

import java.time.ZonedDateTime;

/**
 * @author David Meier
 * @since 0.3.0
 */
public class AlarmPropertyDto extends DevicePropertyDto {
  private final AlarmState state;
  private final ZonedDateTime lastUpdated;

  public AlarmPropertyDto(long id, String label, AlarmState state, ZonedDateTime lastUpdated) {
    super(id, "Alarm", label);
    this.state = state;
    this.lastUpdated = lastUpdated;
  }

  public AlarmState getState() {
    return state;
  }

  public ZonedDateTime getLastUpdated() {
    return lastUpdated;
  }
}
