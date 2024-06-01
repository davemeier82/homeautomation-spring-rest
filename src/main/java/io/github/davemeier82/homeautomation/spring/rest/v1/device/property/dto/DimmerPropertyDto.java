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

public class DimmerPropertyDto extends RelayPropertyDto {
  private final Integer dimmingLevelInPercent;
  private final OffsetDateTime dimmingLevelLastUpdated;


  public DimmerPropertyDto(String id,
                           String type,
                           String displayName,
                           Boolean isOn,
                           OffsetDateTime lastUpdated,
                           Integer dimmingLevelInPercent,
                           OffsetDateTime dimmingLevelLastUpdated
  ) {
    super(id, type, displayName, isOn, lastUpdated);
    this.dimmingLevelInPercent = dimmingLevelInPercent;
    this.dimmingLevelLastUpdated = dimmingLevelLastUpdated;
  }

  public Integer getDimmingLevelInPercent() {
    return dimmingLevelInPercent;
  }

  public OffsetDateTime getDimmingLevelLastUpdated() {
    return dimmingLevelLastUpdated;
  }
}
