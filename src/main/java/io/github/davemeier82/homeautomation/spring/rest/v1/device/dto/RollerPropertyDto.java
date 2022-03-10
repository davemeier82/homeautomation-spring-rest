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

import io.github.davemeier82.homeautomation.core.device.property.RollerState;

import java.time.ZonedDateTime;

public class RollerPropertyDto extends DevicePropertyDto {
  private final RollerState state;
  private final ZonedDateTime stateLastUpdated;
  private final Integer positionInPercent;
  private final ZonedDateTime positionLastUpdated;

  public RollerPropertyDto(long id, RollerState state, ZonedDateTime stateLastUpdated, Integer positionInPercent, ZonedDateTime positionLastUpdated) {
    super(id, "Roller");
    this.state = state;
    this.stateLastUpdated = stateLastUpdated;
    this.positionInPercent = positionInPercent;
    this.positionLastUpdated = positionLastUpdated;
  }

  public RollerState getState() {
    return state;
  }

  public ZonedDateTime getStateLastUpdated() {
    return stateLastUpdated;
  }

  public Integer getPositionInPercent() {
    return positionInPercent;
  }

  public ZonedDateTime getPositionLastUpdated() {
    return positionLastUpdated;
  }
}
