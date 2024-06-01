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

public class WindSensorPropertyDto extends DevicePropertyDto {
  private final Float speedInKmh;
  private final Float gustSpeedInKmh;
  private final Float directionInDegree;
  private final Float gustDirectionInDegree;
  private final Double intervalRunInKm;
  private final OffsetDateTime speedLastUpdated;
  private final OffsetDateTime gustSpeedLastUpdated;
  private final OffsetDateTime directionLastUpdated;
  private final OffsetDateTime gustDirectionLastUpdated;
  private final OffsetDateTime intervalRunLastUpdated;

  public WindSensorPropertyDto(String id,
                               String type,
                               String displayName,
                               Float speedInKmh,
                               Float gustSpeedInKmh,
                               Float directionInDegree,
                               Float gustDirectionInDegree,
                               Double intervalRunInKm,
                               OffsetDateTime speedLastUpdated,
                               OffsetDateTime gustSpeedLastUpdated,
                               OffsetDateTime directionLastUpdated,
                               OffsetDateTime gustDirectionLastUpdated,
                               OffsetDateTime intervalRunLastUpdated
  ) {
    super(id, type, displayName);
    this.speedInKmh = speedInKmh;
    this.gustSpeedInKmh = gustSpeedInKmh;
    this.directionInDegree = directionInDegree;
    this.gustDirectionInDegree = gustDirectionInDegree;
    this.intervalRunInKm = intervalRunInKm;
    this.speedLastUpdated = speedLastUpdated;
    this.gustSpeedLastUpdated = gustSpeedLastUpdated;
    this.directionLastUpdated = directionLastUpdated;
    this.gustDirectionLastUpdated = gustDirectionLastUpdated;
    this.intervalRunLastUpdated = intervalRunLastUpdated;
  }

  public Float getSpeedInKmh() {
    return speedInKmh;
  }

  public Float getGustSpeedInKmh() {
    return gustSpeedInKmh;
  }

  public Float getDirectionInDegree() {
    return directionInDegree;
  }

  public Float getGustDirectionInDegree() {
    return gustDirectionInDegree;
  }

  public Double getIntervalRunInKm() {
    return intervalRunInKm;
  }

  public OffsetDateTime getSpeedLastUpdated() {
    return speedLastUpdated;
  }

  public OffsetDateTime getGustSpeedLastUpdated() {
    return gustSpeedLastUpdated;
  }

  public OffsetDateTime getDirectionLastUpdated() {
    return directionLastUpdated;
  }

  public OffsetDateTime getGustDirectionLastUpdated() {
    return gustDirectionLastUpdated;
  }

  public OffsetDateTime getIntervalRunLastUpdated() {
    return intervalRunLastUpdated;
  }
}
