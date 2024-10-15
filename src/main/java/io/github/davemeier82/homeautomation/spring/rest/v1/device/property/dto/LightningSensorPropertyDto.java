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

public class LightningSensorPropertyDto extends DevicePropertyDto {
  private final Integer distanceInKm;
  private final Integer count;
  private final OffsetDateTime distanceLastUpdated;
  private final OffsetDateTime countLastUpdated;

  public LightningSensorPropertyDto(String id, String type, String displayName, Integer distanceInKm, Integer count, OffsetDateTime distanceLastUpdated, OffsetDateTime countLastUpdated) {
    super(id, type, displayName);
    this.distanceInKm = distanceInKm;
    this.count = count;
    this.distanceLastUpdated = distanceLastUpdated;
    this.countLastUpdated = countLastUpdated;
  }

  public Integer getDistanceInKm() {
    return distanceInKm;
  }

  public Integer getCount() {
    return count;
  }

  public OffsetDateTime getDistanceLastUpdated() {
    return distanceLastUpdated;
  }

  public OffsetDateTime getCountLastUpdated() {
    return countLastUpdated;
  }
}
