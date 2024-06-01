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
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.RelayPropertyDto;

import java.util.List;
import java.util.Optional;

import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyType.RELAY;
import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyValueType.RELAY_STATE;
import static io.github.davemeier82.homeautomation.spring.rest.v1.device.property.DataWithTimestampUtil.getTimestampOrNull;
import static io.github.davemeier82.homeautomation.spring.rest.v1.device.property.DataWithTimestampUtil.getValueOrNull;

public class RelayDtoFactory implements DevicePropertyDtoFactory {

  private final DevicePropertyValueRepository devicePropertyValueRepository;

  public RelayDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    this.devicePropertyValueRepository = devicePropertyValueRepository;
  }

  @Override
  public DevicePropertyType supportedType() {
    return RELAY;
  }

  @Override
  public DevicePropertyDto map(DeviceProperty deviceProperty) {
    Optional<DataWithTimestamp<Boolean>> isOn = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), RELAY_STATE, Boolean.class);
    return new RelayPropertyDto(
        deviceProperty.getId().id(),
        deviceProperty.getType().getTypeName(),
        deviceProperty.getDisplayName(),
        getValueOrNull(isOn),
        getTimestampOrNull(isOn)
    );
  }

  @Override
  public DevicePropertyDto map(List<LatestDevicePropertyValueEntity> entities) {
    LatestDevicePropertyValueEntity entity = entities.getFirst();
    return new RelayPropertyDto(
        entity.getId().getDevicePropertyId(),
        entity.getDevicePropertyType(),
        entity.getDevicePropertyDisplayName(),
        entity.getValue() == null ? null : Boolean.valueOf(entity.getValue()),
        entity.getTimestamp());
  }
}
