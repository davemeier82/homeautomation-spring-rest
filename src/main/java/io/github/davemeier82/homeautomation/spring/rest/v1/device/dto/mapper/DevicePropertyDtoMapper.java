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

package io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.mapper;

import io.github.davemeier82.homeautomation.core.device.DeviceId;
import io.github.davemeier82.homeautomation.core.device.property.DeviceProperty;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyFactory;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyId;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyType;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyTypeFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.AddDevicePropertyDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.UpdateDevicePropertyDto;

public class DevicePropertyDtoMapper {

  private final DevicePropertyFactory devicePropertyFactory;
  private final DevicePropertyTypeFactory devicePropertyTypeFactory;

  public DevicePropertyDtoMapper(DevicePropertyFactory devicePropertyFactory, DevicePropertyTypeFactory devicePropertyTypeFactory) {
    this.devicePropertyFactory = devicePropertyFactory;
    this.devicePropertyTypeFactory = devicePropertyTypeFactory;
  }


  public DeviceProperty map(DeviceId deviceId, AddDevicePropertyDto dto) {
    DevicePropertyId devicePropertyId = new DevicePropertyId(deviceId, dto.getId());
    return devicePropertyFactory.createDeviceProperty(devicePropertyId, mapType(dto.getType()), dto.getDisplayName());
  }

  private DevicePropertyType mapType(String devicePropertyType) {
    return devicePropertyTypeFactory.createDevicePropertyType(devicePropertyType).orElseThrow();
  }

  public DeviceProperty map(DevicePropertyId devicePropertyId, UpdateDevicePropertyDto dto) {
    return devicePropertyFactory.createDeviceProperty(devicePropertyId, mapType(dto.getType()), dto.getDisplayName());
  }
}
