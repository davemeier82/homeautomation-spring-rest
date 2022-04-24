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
import io.github.davemeier82.homeautomation.spring.core.config.device.*;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.DeviceDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.mapper.DeviceToDtoMapper;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.updater.DevicePropertyUpdater;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Comparator.comparing;

/**
 * API Service to handle API requests.
 *
 * @author David Meier
 * @since 0.1.0
 */
public class DeviceApiService {

  private final DeviceRegistry deviceRegistry;
  private final DeviceToDtoMapper deviceToDtoMapper;
  private final Set<DevicePropertyUpdater> devicePropertyUpdaters;
  private final DeviceConfigFactory deviceConfigFactory;
  private final DeviceLoader deviceLoader;
  private final DeviceConfigWriter deviceConfigWriter;

  public DeviceApiService(DeviceRegistry deviceRegistry,
                          DeviceToDtoMapper deviceToDtoMapper,
                          Set<DevicePropertyUpdater> devicePropertyUpdaters,
                          DeviceConfigFactory deviceConfigFactory,
                          DeviceLoader deviceLoader,
                          DeviceConfigWriter deviceConfigWriter
  ) {
    this.deviceRegistry = deviceRegistry;
    this.deviceToDtoMapper = deviceToDtoMapper;
    this.devicePropertyUpdaters = devicePropertyUpdaters;
    this.deviceConfigFactory = deviceConfigFactory;
    this.deviceLoader = deviceLoader;
    this.deviceConfigWriter = deviceConfigWriter;
  }

  /**
   * @return all devices as DTO
   */
  public List<DeviceDto> getDevices() {
    return deviceRegistry.getDevices().stream()
        .sorted(comparing(Device::getId))
        .map(deviceToDtoMapper::map)
        .toList();
  }

  /**
   * Updates a property of a device.
   *
   * @param deviceId   the device id
   * @param propertyId the device property id
   * @param body       the body depends on the property type (see {@link io.github.davemeier82.homeautomation.spring.rest.v1.device.updater.DevicePropertyUpdater})
   */
  public void updateDevice(DeviceId deviceId, long propertyId, Map<String, Object> body) {
    DeviceProperty deviceProperty = deviceRegistry.getByDeviceId(deviceId).orElseThrow().getDeviceProperties()
        .stream().filter(property -> property.getId() == propertyId)
        .findAny().orElseThrow();
    devicePropertyUpdaters.stream()
        .filter(updater -> updater.isSupported(deviceProperty))
        .forEach(updater -> updater.update(deviceProperty, body));
  }

  /**
   * @return the config of all devices
   */
  public DevicesConfig getDevicesConfig() {
    return deviceConfigFactory.createDevicesConfig();
  }

  /**
   * @param deviceId the identifier of the device
   * @return the config to the device
   */
  public Optional<DeviceConfig> getDeviceConfig(DeviceId deviceId) {
    return deviceConfigFactory.createDeviceConfig(deviceId);
  }

  /**
   * Adds a new device
   *
   * @param deviceConfig the config of the new device
   */
  public void addDevice(DeviceConfig deviceConfig) {
    if (deviceRegistry.getByDeviceId(new DeviceId(deviceConfig.id(), deviceConfig.type())).isEmpty()) {
      deviceLoader.load(deviceConfig);
      deviceConfigWriter.save();
    } else {
      throw new IllegalStateException("device with id " + deviceConfig.id() + " and type " + deviceConfig.type() + " already exists");
    }
  }

  /**
   * Updates the display name and the custom identifiers
   *
   * @param deviceConfig the device config
   */
  public void updateDevice(DeviceConfig deviceConfig) {
    Device device = deviceRegistry.getByDeviceId(new DeviceId(deviceConfig.id(), deviceConfig.type())).orElseThrow();
    device.setCustomIdentifiers(deviceConfig.customIdentifiers());
    device.setDisplayName(deviceConfig.displayName());
    deviceConfigWriter.save();
  }
}
