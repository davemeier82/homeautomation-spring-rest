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

import io.github.davemeier82.homeautomation.core.device.property.RollerState;

import java.time.OffsetDateTime;

public class RollerPropertyDto extends DevicePropertyDto {
  private final RollerState state;
  private final OffsetDateTime stateLastUpdated;
  private final Integer positionInPercent;
  private final OffsetDateTime positionLastUpdated;

  public RollerPropertyDto(String id,
                           String type,
                           String displayName,
                           RollerState state,
                           OffsetDateTime stateLastUpdated,
                           Integer positionInPercent,
                           OffsetDateTime positionLastUpdated
  ) {
    super(id, type, displayName);
    this.state = state;
    this.stateLastUpdated = stateLastUpdated;
    this.positionInPercent = positionInPercent;
    this.positionLastUpdated = positionLastUpdated;
  }

  public RollerState getState() {
    return state;
  }

  public OffsetDateTime getStateLastUpdated() {
    return stateLastUpdated;
  }

  public Integer getPositionInPercent() {
    return positionInPercent;
  }

  public OffsetDateTime getPositionLastUpdated() {
    return positionLastUpdated;
  }
}
