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

import io.github.davemeier82.homeautomation.core.device.DeviceId;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyId;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyType;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyValueType;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyValueTypeFactory;
import io.github.davemeier82.homeautomation.core.repositories.DevicePropertyRepository;
import io.github.davemeier82.homeautomation.core.repositories.DeviceRepository;
import io.github.davemeier82.homeautomation.spring.core.persistence.repository.JpaLatestDevicePropertyValueRepository;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.AddDeviceDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.DeviceDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.UpdateDeviceDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.mapper.DeviceDtoMapper;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.mapper.DevicePropertyDtoMapper;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.AddDevicePropertyDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.UpdateDevicePropertyDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.value.UpdateDevicePropertyValueDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.updater.DevicePropertyUpdater;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Transactional
public class DeviceApiService {

  private final DeviceRepository deviceRepository;
  private final DeviceDtoMapper deviceDtoMapper;
  private final Set<DevicePropertyValueTypeFactory> devicePropertyValueTypeFactories;
  private final DevicePropertyRepository devicePropertyRepository;
  private final JpaLatestDevicePropertyValueRepository latestDevicePropertyValueRepository;
  private final DevicePropertyDtoMapper devicePropertyDtoMapper;

  private final Map<DevicePropertyType, DevicePropertyUpdater> devicePropertyUpdaterByType;

  public DeviceApiService(DeviceRepository deviceRepository,
                          DeviceDtoMapper deviceDtoMapper,
                          Set<DevicePropertyValueTypeFactory> devicePropertyValueTypeFactories,
                          DevicePropertyRepository devicePropertyRepository,
                          JpaLatestDevicePropertyValueRepository latestDevicePropertyValueRepository,
                          DevicePropertyDtoMapper devicePropertyDtoMapper,
                          Set<DevicePropertyUpdater> devicePropertyUpdaters
  ) {
    this.deviceRepository = deviceRepository;
    this.deviceDtoMapper = deviceDtoMapper;
    this.devicePropertyValueTypeFactories = devicePropertyValueTypeFactories;
    this.devicePropertyRepository = devicePropertyRepository;
    this.latestDevicePropertyValueRepository = latestDevicePropertyValueRepository;
    this.devicePropertyDtoMapper = devicePropertyDtoMapper;
    devicePropertyUpdaterByType = devicePropertyUpdaters.stream().collect(toMap(DevicePropertyUpdater::getSupportedDevicePropertyType, Function.identity()));
  }

  public List<DeviceDto> getDevices() {
    return deviceDtoMapper.map(latestDevicePropertyValueRepository.findAll());
  }

  public Optional<DeviceDto> getDevice(DeviceId deviceId) {
    return deviceRepository.getByDeviceId(deviceId).map(deviceDtoMapper::map);
  }

  public void updateDevice(DeviceId deviceId, UpdateDeviceDto updateDeviceDto) {
    deviceRepository.save(deviceDtoMapper.map(deviceId, updateDeviceDto));
  }

  public void addDevice(AddDeviceDto deviceDto) {
    deviceRepository.save(deviceDtoMapper.map(deviceDto));
  }

  public void deleteDevice(DeviceId deviceId) {
    deviceRepository.delete(deviceId);
  }

  public void addDeviceProperty(DeviceId deviceId, AddDevicePropertyDto dto) {
    devicePropertyRepository.save(devicePropertyDtoMapper.map(deviceId, dto));
  }

  public void updateDeviceProperty(DevicePropertyId devicePropertyId, UpdateDevicePropertyDto dto) {
    devicePropertyRepository.save(devicePropertyDtoMapper.map(devicePropertyId, dto));
  }

  public void deleteDeviceProperty(DevicePropertyId devicePropertyId) {
    devicePropertyRepository.delete(devicePropertyId);
  }

  public void updateDevicePropertyValue(DevicePropertyId devicePropertyId, String valueType, UpdateDevicePropertyValueDto updateDevicePropertyValueDto) {
    DevicePropertyValueType devicePropertyValueType = getDevicePropertyValueType(valueType);
    DevicePropertyUpdater devicePropertyUpdater = devicePropertyUpdaterByType.get(devicePropertyValueType.getDevicePropertyType());
    devicePropertyUpdater.update(devicePropertyId, updateDevicePropertyValueDto.getValue());
  }

  private DevicePropertyValueType getDevicePropertyValueType(String valueType) {
    return devicePropertyValueTypeFactories.stream().map(f -> f.createDevicePropertyValueType(valueType)).filter(Optional::isPresent).map(Optional::get).findAny().orElseThrow();
  }
}
