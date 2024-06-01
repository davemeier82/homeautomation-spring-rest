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
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.WindowSensorPropertyDto;

import java.util.List;
import java.util.Optional;

import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyType.WINDOW_SENSOR;
import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyValueType.WINDOW_STATE;
import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyValueType.WINDOW_TILT_ANGLE;
import static io.github.davemeier82.homeautomation.spring.rest.v1.device.property.DataWithTimestampUtil.getTimestampOrNull;
import static io.github.davemeier82.homeautomation.spring.rest.v1.device.property.DataWithTimestampUtil.getValueOrNull;

public class WindowSensorDtoFactory implements DevicePropertyDtoFactory {

  private final DevicePropertyValueRepository devicePropertyValueRepository;

  public WindowSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    this.devicePropertyValueRepository = devicePropertyValueRepository;
  }

  @Override
  public DevicePropertyType supportedType() {
    return WINDOW_SENSOR;
  }

  @Override
  public DevicePropertyDto map(DeviceProperty deviceProperty) {
    Optional<DataWithTimestamp<Boolean>> isOpen = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), WINDOW_STATE, Boolean.class);
    Optional<DataWithTimestamp<Integer>> tiltAngle = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), WINDOW_TILT_ANGLE, Integer.class);
    return new WindowSensorPropertyDto(
        deviceProperty.getId().id(),
        deviceProperty.getType().getTypeName(),
        deviceProperty.getDisplayName(),
        getValueOrNull(isOpen),
        getTimestampOrNull(isOpen),
        getValueOrNull(tiltAngle),
        getTimestampOrNull(tiltAngle)
    );
  }

  @Override
  public DevicePropertyDto map(List<LatestDevicePropertyValueEntity> entities) {
    Optional<DataWithTimestamp<Boolean>> isOpen = entities.stream().filter(e -> e.getId().getDevicePropertyValueType().equals(WINDOW_STATE.getTypeName())).map(p ->
        new DataWithTimestamp<>(p.getTimestamp(), Boolean.valueOf(p.getValue()))).findFirst();
    Optional<DataWithTimestamp<Integer>> tiltAngle = entities.stream().filter(e -> e.getId().getDevicePropertyValueType().equals(WINDOW_TILT_ANGLE.getTypeName())).map(p ->
        new DataWithTimestamp<>(p.getTimestamp(), Integer.valueOf(p.getValue()))).findFirst();

    LatestDevicePropertyValueEntity deviceProperty = entities.getFirst();
    return new WindowSensorPropertyDto(
        deviceProperty.getId().getDevicePropertyId(),
        deviceProperty.getDevicePropertyType(),
        deviceProperty.getDevicePropertyDisplayName(),
        getValueOrNull(isOpen),
        getTimestampOrNull(isOpen),
        getValueOrNull(tiltAngle),
        getTimestampOrNull(tiltAngle));
  }
}
