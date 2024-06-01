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

import io.github.davemeier82.homeautomation.core.device.DeviceId;
import io.github.davemeier82.homeautomation.core.device.DeviceTypeMapper;
import io.github.davemeier82.homeautomation.core.device.property.DeviceProperty;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyId;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyType;
import io.github.davemeier82.homeautomation.core.event.DataWithTimestamp;
import io.github.davemeier82.homeautomation.core.repositories.DevicePropertyValueRepository;
import io.github.davemeier82.homeautomation.spring.core.persistence.entity.LatestDevicePropertyValueEntity;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.DevicePropertyDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.MotionSensorPropertyDto;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyType.MOTION_SENSOR;
import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyValueType.MOTION_STATE;
import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyValueType.RELAY_STATE;
import static io.github.davemeier82.homeautomation.spring.rest.v1.device.property.DataWithTimestampUtil.getTimestampOrNull;
import static io.github.davemeier82.homeautomation.spring.rest.v1.device.property.DataWithTimestampUtil.getValueOrNull;

public class MotionSensorDtoFactory implements DevicePropertyDtoFactory {

  private final DevicePropertyValueRepository devicePropertyValueRepository;
  private final DeviceTypeMapper deviceTypeMapper;

  public MotionSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository, DeviceTypeMapper deviceTypeMapper) {
    this.devicePropertyValueRepository = devicePropertyValueRepository;
    this.deviceTypeMapper = deviceTypeMapper;
  }

  @Override
  public DevicePropertyType supportedType() {
    return MOTION_SENSOR;
  }

  @Override
  public DevicePropertyDto map(DeviceProperty deviceProperty) {
    Optional<DataWithTimestamp<Boolean>> isOn = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), MOTION_STATE, Boolean.class);
    Optional<OffsetDateTime> lastMotion = devicePropertyValueRepository.lastTimeValueMatched(deviceProperty.getId(), MOTION_STATE, Boolean.TRUE);
    return new MotionSensorPropertyDto(
        deviceProperty.getId().id(),
        deviceProperty.getType().getTypeName(),
        deviceProperty.getDisplayName(),
        getValueOrNull(isOn),
        lastMotion.orElse(null),
        getTimestampOrNull(isOn)
    );
  }

  @Override
  public DevicePropertyDto map(List<LatestDevicePropertyValueEntity> entities) {
    Optional<DataWithTimestamp<Boolean>> isOn = entities.stream().filter(e -> e.getId().getDevicePropertyValueType().equals(RELAY_STATE.getTypeName())).map(p ->
        new DataWithTimestamp<>(p.getTimestamp(), Boolean.valueOf(p.getValue()))).findFirst();
    LatestDevicePropertyValueEntity deviceProperty = entities.getFirst();
    var id = deviceProperty.getId();
    DevicePropertyId devicePropertyId = new DevicePropertyId(new DeviceId(id.getDeviceId(), deviceTypeMapper.map(id.getDeviceType())), id.getDevicePropertyId());
    Optional<OffsetDateTime> lastMotion = devicePropertyValueRepository.lastTimeValueMatched(devicePropertyId, MOTION_STATE, Boolean.TRUE);

    return new MotionSensorPropertyDto(
        id.getDevicePropertyId(),
        deviceProperty.getDevicePropertyType(),
        deviceProperty.getDevicePropertyDisplayName(),
        getValueOrNull(isOn),
        lastMotion.orElse(null),
        getTimestampOrNull(isOn));
  }
}
