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

package com.github.davemeier82.homeautomation.spring.rest.v1.device;

import com.github.davemeier82.homeautomation.core.device.Device;
import com.github.davemeier82.homeautomation.spring.core.DeviceRegistry;
import com.github.davemeier82.homeautomation.spring.rest.v1.device.dto.DeviceDto;

import java.util.List;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

public class DeviceApiService {

  private final DeviceRegistry deviceRegistry;
  private final DeviceToDtoMapper deviceToDtoMapper;

  public DeviceApiService(DeviceRegistry deviceRegistry,
                          DeviceToDtoMapper deviceToDtoMapper
  ) {
    this.deviceRegistry = deviceRegistry;
    this.deviceToDtoMapper = deviceToDtoMapper;
  }

  public List<DeviceDto> getDevices() {
    return deviceRegistry.getDevices().stream()
        .sorted(comparing(Device::getId))
        .map(deviceToDtoMapper::map)
        .collect(toList());
  }

}
