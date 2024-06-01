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
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.RainSensorPropertyDto;

import java.util.List;
import java.util.Optional;

import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyType.RAIN_SENSOR;
import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyValueType.*;
import static io.github.davemeier82.homeautomation.spring.rest.v1.device.property.DataWithTimestampUtil.getTimestampOrNull;
import static io.github.davemeier82.homeautomation.spring.rest.v1.device.property.DataWithTimestampUtil.getValueOrNull;

public class RainSensorDtoFactory implements DevicePropertyDtoFactory {

  private final DevicePropertyValueRepository devicePropertyValueRepository;

  public RainSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    this.devicePropertyValueRepository = devicePropertyValueRepository;
  }

  @Override
  public DevicePropertyType supportedType() {
    return RAIN_SENSOR;
  }

  @Override
  public DevicePropertyDto map(DeviceProperty deviceProperty) {
    Optional<DataWithTimestamp<Float>> rainRate = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), RAIN_RATE, Float.class);
    Optional<DataWithTimestamp<Float>> rainToday = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), RAIN_TODAY_AMOUNT, Float.class);
    Optional<DataWithTimestamp<Float>> rainInterval = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), RAIN_INTERVAL_AMOUNT, Float.class);
    return new RainSensorPropertyDto(
        deviceProperty.getId().id(),
        deviceProperty.getType().getTypeName(),
        deviceProperty.getDisplayName(),
        getValueOrNull(rainRate),
        getValueOrNull(rainToday),
        getValueOrNull(rainInterval),
        getTimestampOrNull(rainRate),
        getTimestampOrNull(rainToday),
        getTimestampOrNull(rainInterval)
    );
  }

  @Override
  public DevicePropertyDto map(List<LatestDevicePropertyValueEntity> entities) {
    Optional<DataWithTimestamp<Float>> rainRate = entities.stream().filter(e -> e.getId().getDevicePropertyValueType().equals(RAIN_RATE.getTypeName())).map(p ->
        new DataWithTimestamp<>(p.getTimestamp(), Float.valueOf(p.getValue()))).findFirst();
    Optional<DataWithTimestamp<Float>> rainToday = entities.stream().filter(e -> e.getId().getDevicePropertyValueType().equals(RAIN_TODAY_AMOUNT.getTypeName())).map(p ->
        new DataWithTimestamp<>(p.getTimestamp(), Float.valueOf(p.getValue()))).findFirst();
    Optional<DataWithTimestamp<Float>> rainInterval = entities.stream().filter(e -> e.getId().getDevicePropertyValueType().equals(RAIN_INTERVAL_AMOUNT.getTypeName())).map(p ->
        new DataWithTimestamp<>(p.getTimestamp(), Float.valueOf(p.getValue()))).findFirst();

    LatestDevicePropertyValueEntity deviceProperty = entities.getFirst();
    return new RainSensorPropertyDto(
        deviceProperty.getId().getDevicePropertyId(),
        deviceProperty.getDevicePropertyType(),
        deviceProperty.getDevicePropertyDisplayName(),
        getValueOrNull(rainRate),
        getValueOrNull(rainToday),
        getValueOrNull(rainInterval),
        getTimestampOrNull(rainRate),
        getTimestampOrNull(rainToday),
        getTimestampOrNull(rainInterval)
    );
  }
}
