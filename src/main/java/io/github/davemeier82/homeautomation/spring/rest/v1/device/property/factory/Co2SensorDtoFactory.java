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

package io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory;

import io.github.davemeier82.homeautomation.core.device.property.DeviceProperty;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyType;
import io.github.davemeier82.homeautomation.core.event.DataWithTimestamp;
import io.github.davemeier82.homeautomation.core.repositories.DevicePropertyValueRepository;
import io.github.davemeier82.homeautomation.spring.core.persistence.entity.LatestDevicePropertyValueEntity;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.Co2SensorPropertyDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.DevicePropertyDto;

import java.util.List;
import java.util.Optional;

import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyType.CO2_SENSOR;
import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyValueType.CO2_LEVEL;
import static io.github.davemeier82.homeautomation.spring.rest.v1.device.property.DataWithTimestampUtil.getTimestampOrNull;
import static io.github.davemeier82.homeautomation.spring.rest.v1.device.property.DataWithTimestampUtil.getValueOrNull;

public class Co2SensorDtoFactory implements DevicePropertyDtoFactory {

  private final DevicePropertyValueRepository devicePropertyValueRepository;

  public Co2SensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    this.devicePropertyValueRepository = devicePropertyValueRepository;
  }

  @Override
  public DevicePropertyType supportedType() {
    return CO2_SENSOR;
  }

  @Override
  public DevicePropertyDto map(DeviceProperty deviceProperty) {
    Optional<DataWithTimestamp<Integer>> co2 = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), CO2_LEVEL, Integer.class);
    return new Co2SensorPropertyDto(
        deviceProperty.getId().id(),
        deviceProperty.getType().getTypeName(),
        deviceProperty.getDisplayName(),
        getValueOrNull(co2),
        getTimestampOrNull(co2)
    );
  }

  @Override
  public DevicePropertyDto map(List<LatestDevicePropertyValueEntity> entities) {
    LatestDevicePropertyValueEntity entity = entities.getFirst();
    return new Co2SensorPropertyDto(
        entity.getId().getDevicePropertyId(),
        entity.getDevicePropertyType(),
        entity.getDevicePropertyDisplayName(),
        entity.getValue() == null ? null : Integer.valueOf(entity.getValue()),
        entity.getTimestamp());
  }
}
