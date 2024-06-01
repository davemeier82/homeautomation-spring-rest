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

public class WindowSensorPropertyDto extends DevicePropertyDto {
  private final Boolean isOpen;
  private final OffsetDateTime isOpenLastUpdated;
  private final Integer tiltAngleInDegree;
  private final OffsetDateTime tiltLastUpdated;

  public WindowSensorPropertyDto(String id,
                                 String type,
                                 String displayName,
                                 Boolean isOpen,
                                 OffsetDateTime isOpenLastUpdated,
                                 Integer tiltAngleInDegree,
                                 OffsetDateTime tiltLastUpdated
  ) {
    super(id, type, displayName);
    this.isOpen = isOpen;
    this.isOpenLastUpdated = isOpenLastUpdated;
    this.tiltAngleInDegree = tiltAngleInDegree;
    this.tiltLastUpdated = tiltLastUpdated;
  }

  public Boolean getIsOpen() {
    return isOpen;
  }

  public OffsetDateTime getIsOpenLastUpdated() {
    return isOpenLastUpdated;
  }

  public Integer getTiltAngleInDegree() {
    return tiltAngleInDegree;
  }

  public OffsetDateTime getTiltLastUpdated() {
    return tiltLastUpdated;
  }
}
