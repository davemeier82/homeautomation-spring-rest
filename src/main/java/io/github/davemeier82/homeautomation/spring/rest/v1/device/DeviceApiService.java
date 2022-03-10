/*
 * Copyright 2021-2021 the original author or authors.
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

package io.github.davemeier82.homeautomation.spring.rest.v1.device;

import io.github.davemeier82.homeautomation.core.device.Device;
import io.github.davemeier82.homeautomation.core.device.DeviceId;
import io.github.davemeier82.homeautomation.core.device.property.DeviceProperty;
import io.github.davemeier82.homeautomation.spring.core.DeviceRegistry;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.DeviceDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.mapper.DeviceToDtoMapper;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.updater.DevicePropertyUpdater;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Comparator.comparing;

public class DeviceApiService {

  private final DeviceRegistry deviceRegistry;
  private final DeviceToDtoMapper deviceToDtoMapper;
  private final Set<DevicePropertyUpdater> devicePropertyUpdaters;

  public DeviceApiService(DeviceRegistry deviceRegistry,
                          DeviceToDtoMapper deviceToDtoMapper,
                          Set<DevicePropertyUpdater> devicePropertyUpdaters
  ) {
    this.deviceRegistry = deviceRegistry;
    this.deviceToDtoMapper = deviceToDtoMapper;
    this.devicePropertyUpdaters = devicePropertyUpdaters;
  }

  public List<DeviceDto> getDevices() {
    return deviceRegistry.getDevices().stream()
        .sorted(comparing(Device::getId))
        .map(deviceToDtoMapper::map)
        .toList();
  }

  public void updateDevice(DeviceId deviceId, long propertyId, Map<String, Object> body) {
    DeviceProperty deviceProperty = deviceRegistry.getByDeviceId(deviceId).orElseThrow().getDeviceProperties()
        .stream().filter(property -> property.getId() == propertyId)
        .findAny().orElseThrow();
    devicePropertyUpdaters.stream()
        .filter(updater -> updater.isSupported(deviceProperty))
        .forEach(updater -> updater.update(deviceProperty, body));
  }

}
