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

import io.github.davemeier82.homeautomation.core.device.Device;
import io.github.davemeier82.homeautomation.core.device.DeviceFactory;
import io.github.davemeier82.homeautomation.core.device.DeviceId;
import io.github.davemeier82.homeautomation.core.device.DeviceType;
import io.github.davemeier82.homeautomation.core.device.DeviceTypeMapper;
import io.github.davemeier82.homeautomation.core.device.property.DeviceProperty;
import io.github.davemeier82.homeautomation.core.repositories.DevicePropertyRepository;
import io.github.davemeier82.homeautomation.core.repositories.DeviceRepository;
import io.github.davemeier82.homeautomation.spring.core.persistence.entity.DevicePropertyId;
import io.github.davemeier82.homeautomation.spring.core.persistence.entity.LatestDevicePropertyValueEntity;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.AddDeviceDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.DeviceDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.UpdateDeviceDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.DevicePropertyDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.DevicePropertyDtoFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static io.github.davemeier82.homeautomation.core.device.DeviceId.deviceIdFromDevice;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;


public class DeviceDtoMapper {

  private final Map<String, DevicePropertyDtoFactory> devicePropertyTypeToDtoFactory;
  private final DevicePropertyRepository devicePropertyRepository;
  private final Set<DeviceFactory> deviceFactories;
  private final DeviceTypeMapper deviceTypeMapper;
  private final DeviceRepository deviceRepository;

  public DeviceDtoMapper(Set<DevicePropertyDtoFactory> devicePropertyDtoFactories,
                         DevicePropertyRepository devicePropertyRepository,
                         Set<DeviceFactory> deviceFactories,
                         DeviceTypeMapper deviceTypeMapper,
                         DeviceRepository deviceRepository
  ) {
    devicePropertyTypeToDtoFactory = devicePropertyDtoFactories.stream().collect(toMap(f -> f.supportedType().getTypeName(), identity()));
    this.devicePropertyRepository = devicePropertyRepository;
    this.deviceFactories = deviceFactories;
    this.deviceTypeMapper = deviceTypeMapper;
    this.deviceRepository = deviceRepository;
  }

  public List<DeviceDto> map(List<LatestDevicePropertyValueEntity> entities) {
    Map<DevicePropertyId, List<LatestDevicePropertyValueEntity>> props = entities.stream().collect(groupingBy(LatestDevicePropertyValueEntity::getId));
    Map<DeviceId, Map<String, String>> identifiersByDeviceId = deviceRepository.getAllCustomIdentifiers();

    Map<DeviceId, List<DevicePropertyDto>> properties = new HashMap<>();
    Map<DeviceId, String> deviceDisplayNames = new HashMap<>();
    props.values().forEach(e -> {
      DevicePropertyDtoFactory devicePropertyDtoFactory = devicePropertyTypeToDtoFactory.get(e.getFirst().getDevicePropertyType());
      DeviceType deviceType = deviceTypeMapper.map(e.getFirst().getId().getDeviceType());
      DeviceId deviceId = new DeviceId(e.getFirst().getId().getDeviceId(), deviceType);
      deviceDisplayNames.putIfAbsent(deviceId, e.getFirst().getDeviceDisplayName());

      if (devicePropertyDtoFactory != null) {
        DevicePropertyDto dto = devicePropertyDtoFactory.map(e);
        ArrayList<DevicePropertyDto> newDtos = new ArrayList<>();
        List<DevicePropertyDto> devicePropertyDtos = properties.putIfAbsent(deviceId, newDtos);
        if (devicePropertyDtos == null) {
          newDtos.add(dto);
        } else {
          devicePropertyDtos.add(dto);
        }
      } else {
        ArrayList<DevicePropertyDto> newDtos = new ArrayList<>();
        properties.putIfAbsent(deviceId, newDtos);
      }
    });

    return properties.entrySet().stream().map(e -> {
      DeviceId deviceId = e.getKey();
      String displayName = deviceDisplayNames.get(deviceId);
      Map<String, String> identifiers = identifiersByDeviceId.get(deviceId);
      return new DeviceDto(deviceId.type().getTypeName(), deviceId.id(), displayName, e.getValue(), Objects.requireNonNullElse(identifiers, Map.of()));
    }).toList();
  }

  public DeviceDto map(Device device) {
    List<DevicePropertyDto> properties = new ArrayList<>();
    DeviceDto deviceDto = new DeviceDto(device.getType().getTypeName(), device.getId(), device.getDisplayName(), properties, device.getCustomIdentifiers());
    List<DeviceProperty> deviceProperties = devicePropertyRepository.findByDeviceId(deviceIdFromDevice(device));

    for (DeviceProperty deviceProperty : deviceProperties) {
      DevicePropertyDtoFactory devicePropertyDtoFactory = devicePropertyTypeToDtoFactory.get(deviceProperty.getType().getTypeName());
      if (devicePropertyDtoFactory != null) {
        properties.add(devicePropertyDtoFactory.map(deviceProperty));
      }
    }
    return deviceDto;
  }

  public Device map(AddDeviceDto dto) {
    DeviceType deviceType = deviceTypeMapper.map(dto.getType());
    DeviceFactory deviceFactory = deviceFactories.stream().filter(f -> f.supportsDeviceType(deviceType)).findFirst().orElseThrow();
    return deviceFactory.createDevice(deviceType, dto.getId(), dto.getDisplayName(), dto.getParameters(), dto.getCustomIdentifiers()).orElseThrow();
  }


  public Device map(DeviceId deviceId, UpdateDeviceDto dto) {
    DeviceFactory deviceFactory = deviceFactories.stream().filter(f -> f.supportsDeviceType(deviceId.type())).findFirst().orElseThrow();
    return deviceFactory.createDevice(deviceId.type(), deviceId.id(), dto.getDisplayName(), dto.getParameters(), dto.getCustomIdentifiers()).orElseThrow();
  }
}
