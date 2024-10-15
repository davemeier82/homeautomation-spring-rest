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
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.DevicePropertyDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.LightningSensorPropertyDto;

import java.util.List;
import java.util.Optional;

import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyType.LIGHTNING_SENSOR;
import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyValueType.LIGHTNING_COUNT;
import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyValueType.LIGHTNING_DISTANCE;
import static io.github.davemeier82.homeautomation.spring.rest.v1.device.property.DataWithTimestampUtil.getTimestampOrNull;
import static io.github.davemeier82.homeautomation.spring.rest.v1.device.property.DataWithTimestampUtil.getValueOrNull;

public class LightningSensorDtoFactory implements DevicePropertyDtoFactory {

  private final DevicePropertyValueRepository devicePropertyValueRepository;

  public LightningSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    this.devicePropertyValueRepository = devicePropertyValueRepository;
  }

  @Override
  public DevicePropertyType supportedType() {
    return LIGHTNING_SENSOR;
  }

  @Override
  public DevicePropertyDto map(DeviceProperty deviceProperty) {
    Optional<DataWithTimestamp<Integer>> count = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), LIGHTNING_COUNT, Integer.class);
    Optional<DataWithTimestamp<Integer>> distance = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), LIGHTNING_DISTANCE, Integer.class);
    return new LightningSensorPropertyDto(
        deviceProperty.getId().id(),
        deviceProperty.getType().getTypeName(),
        deviceProperty.getDisplayName(),
        getValueOrNull(distance),
        getValueOrNull(count),
        getTimestampOrNull(distance),
        getTimestampOrNull(count)
    );
  }

  @Override
  public DevicePropertyDto map(List<LatestDevicePropertyValueEntity> entities) {
    Optional<DataWithTimestamp<Integer>> count = entities.stream().filter(e -> e.getId().getDevicePropertyValueType().equals(LIGHTNING_COUNT.getTypeName())).map(p ->
        new DataWithTimestamp<>(p.getTimestamp(), Integer.valueOf(p.getValue()))).findFirst();
    Optional<DataWithTimestamp<Integer>> distance = entities.stream().filter(e -> e.getId().getDevicePropertyValueType().equals(LIGHTNING_DISTANCE.getTypeName())).map(p ->
        new DataWithTimestamp<>(p.getTimestamp(), Integer.valueOf(p.getValue()))).findFirst();

    LatestDevicePropertyValueEntity deviceProperty = entities.getFirst();
    return new LightningSensorPropertyDto(
        deviceProperty.getId().getDevicePropertyId(),
        deviceProperty.getDevicePropertyType(),
        deviceProperty.getDevicePropertyDisplayName(),
        getValueOrNull(distance),
        getValueOrNull(count),
        getTimestampOrNull(distance),
        getTimestampOrNull(count)
    );
  }
}
