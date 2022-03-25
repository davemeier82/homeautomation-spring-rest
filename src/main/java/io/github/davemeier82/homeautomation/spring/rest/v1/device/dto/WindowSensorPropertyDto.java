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
public class WindowSensorPropertyDto extends DevicePropertyDto {
  private final Boolean isOpen;
  private final ZonedDateTime isOpenLastUpdated;
  private final boolean isTiltingSupported;
  private final Integer tiltAngleInDegree;
  private final ZonedDateTime tiltLastUpdated;

  public WindowSensorPropertyDto(long id,
                                 Boolean isOpen,
                                 ZonedDateTime isOpenLastUpdated,
                                 boolean isTiltingSupported,
                                 Integer tiltAngleInDegree,
                                 ZonedDateTime tiltLastUpdated
  ) {
    super(id, "WindowSensor");
    this.isOpen = isOpen;
    this.isOpenLastUpdated = isOpenLastUpdated;
    this.isTiltingSupported = isTiltingSupported;
    this.tiltAngleInDegree = tiltAngleInDegree;
    this.tiltLastUpdated = tiltLastUpdated;
  }

  public Boolean getIsOpen() {
    return isOpen;
  }

  public ZonedDateTime getIsOpenLastUpdated() {
    return isOpenLastUpdated;
  }

  public boolean isIsTiltingSupported() {
    return isTiltingSupported;
  }

  public Integer getTiltAngleInDegree() {
    return tiltAngleInDegree;
  }

  public ZonedDateTime getTiltLastUpdated() {
    return tiltLastUpdated;
  }
}
