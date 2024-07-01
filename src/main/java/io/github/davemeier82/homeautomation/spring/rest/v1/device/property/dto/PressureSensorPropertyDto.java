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

public class PressureSensorPropertyDto extends DevicePropertyDto {
  private final Float pressureInMillibar;
  private final OffsetDateTime lastUpdated;

  public PressureSensorPropertyDto(String id,
                                   String type,
                                   String displayName,
                                   Float pressureInMillibar,
                                   OffsetDateTime lastUpdated
  ) {
    super(id, type, displayName);
    this.pressureInMillibar = pressureInMillibar;
    this.lastUpdated = lastUpdated;
  }

  public Float getPressureInMillibar() {
    return pressureInMillibar;
  }

  public OffsetDateTime getLastUpdated() {
    return lastUpdated;
  }
}