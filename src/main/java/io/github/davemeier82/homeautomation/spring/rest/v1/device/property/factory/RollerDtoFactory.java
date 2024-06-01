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
import io.github.davemeier82.homeautomation.core.device.property.RollerState;
import io.github.davemeier82.homeautomation.core.event.DataWithTimestamp;
import io.github.davemeier82.homeautomation.core.repositories.DevicePropertyValueRepository;
import io.github.davemeier82.homeautomation.spring.core.persistence.entity.LatestDevicePropertyValueEntity;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.DevicePropertyDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.RollerPropertyDto;

import java.util.List;
import java.util.Optional;

import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyType.ROLLER;
import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyValueType.ROLLER_POSITION;
import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyValueType.ROLLER_STATE;
import static io.github.davemeier82.homeautomation.spring.rest.v1.device.property.DataWithTimestampUtil.getTimestampOrNull;
import static io.github.davemeier82.homeautomation.spring.rest.v1.device.property.DataWithTimestampUtil.getValueOrNull;

public class RollerDtoFactory implements DevicePropertyDtoFactory {

  private final DevicePropertyValueRepository devicePropertyValueRepository;

  public RollerDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    this.devicePropertyValueRepository = devicePropertyValueRepository;
  }

  @Override
  public DevicePropertyType supportedType() {
    return ROLLER;
  }

  @Override
  public DevicePropertyDto map(DeviceProperty deviceProperty) {
    Optional<DataWithTimestamp<RollerState>> state = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), ROLLER_STATE, RollerState.class);
    Optional<DataWithTimestamp<Integer>> position = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), ROLLER_POSITION, Integer.class);
    return new RollerPropertyDto(
        deviceProperty.getId().id(),
        deviceProperty.getType().getTypeName(),
        deviceProperty.getDisplayName(),
        getValueOrNull(state),
        getTimestampOrNull(state),
        getValueOrNull(position),
        getTimestampOrNull(position)
    );
  }

  @Override
  public DevicePropertyDto map(List<LatestDevicePropertyValueEntity> entities) {
    Optional<DataWithTimestamp<RollerState>> state = entities.stream().filter(e -> e.getId().getDevicePropertyValueType().equals(ROLLER_STATE.getTypeName())).map(p ->
        new DataWithTimestamp<>(p.getTimestamp(), RollerState.valueOf(p.getValue()))).findFirst();
    Optional<DataWithTimestamp<Integer>> position = entities.stream().filter(e -> e.getId().getDevicePropertyValueType().equals(ROLLER_POSITION.getTypeName())).map(p ->
        new DataWithTimestamp<>(p.getTimestamp(), Integer.valueOf(p.getValue()))).findFirst();

    LatestDevicePropertyValueEntity deviceProperty = entities.getFirst();
    return new RollerPropertyDto(
        deviceProperty.getId().getDevicePropertyId(),
        deviceProperty.getDevicePropertyType(),
        deviceProperty.getDevicePropertyDisplayName(),
        getValueOrNull(state),
        getTimestampOrNull(state),
        getValueOrNull(position),
        getTimestampOrNull(position));
  }
}
