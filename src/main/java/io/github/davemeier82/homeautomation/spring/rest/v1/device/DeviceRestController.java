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
import io.github.davemeier82.homeautomation.core.device.DeviceTypeMapper;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyId;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.AddDeviceDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.DeviceDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.UpdateDeviceDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.AddDevicePropertyDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.UpdateDevicePropertyDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.value.UpdateDevicePropertyValueDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static io.github.davemeier82.homeautomation.spring.rest.v1.HomeAutomationRestAutoConfiguration.API_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@ConditionalOnMissingBean
@RequestMapping(API_PATH + "devices")
public class DeviceRestController {

  private final DeviceApiService deviceApiService;

  private final DeviceTypeMapper deviceTypeMapper;

  public DeviceRestController(DeviceApiService deviceApiService, DeviceTypeMapper deviceTypeMapper) {
    this.deviceApiService = deviceApiService;
    this.deviceTypeMapper = deviceTypeMapper;
  }

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public List<DeviceDto> getDevices() {
    return deviceApiService.getDevices();
  }

  @PostMapping
  public void addDevice(@RequestBody AddDeviceDto deviceDto) {
    deviceApiService.addDevice(deviceDto);
  }

  @GetMapping(path = "/{deviceType}-{deviceId}")
  public Optional<DeviceDto> getDevice(@PathVariable String deviceType, @PathVariable String deviceId
  ) {
    return deviceApiService.getDevice(new DeviceId(deviceId, deviceTypeMapper.map(deviceType)));
  }

  @PutMapping(path = "/{deviceType}-{deviceId}")
  public void updateDevice(@PathVariable String deviceType, @PathVariable String deviceId, @RequestBody UpdateDeviceDto updateDeviceDto
  ) {
    deviceApiService.updateDevice(new DeviceId(deviceId, deviceTypeMapper.map(deviceType)), updateDeviceDto);
  }

  @DeleteMapping(path = "/{deviceType}-{deviceId}")
  public void deleteDevice(@PathVariable String deviceType, @PathVariable String deviceId
  ) {
    deviceApiService.deleteDevice(new DeviceId(deviceId, deviceTypeMapper.map(deviceType)));
  }

  @PostMapping(path = "/{deviceType}-{deviceId}/properties")
  public void addDeviceProperty(@PathVariable String deviceId, @PathVariable String deviceType, @RequestBody AddDevicePropertyDto addDevicePropertyDto
  ) {
    deviceApiService.addDeviceProperty(new DeviceId(deviceId, deviceTypeMapper.map(deviceType)), addDevicePropertyDto);
  }

  @PutMapping(path = "/{deviceType}-{deviceId}/properties/{propertyId}")
  public void updateDeviceProperty(@PathVariable String deviceId, @PathVariable String deviceType, @PathVariable String propertyId, @RequestBody UpdateDevicePropertyDto updateDevicePropertyDto
  ) {
    deviceApiService.updateDeviceProperty(new DevicePropertyId(new DeviceId(deviceId, deviceTypeMapper.map(deviceType)), propertyId), updateDevicePropertyDto);
  }

  @DeleteMapping(path = "/{deviceType}-{deviceId}/properties/{propertyId}")
  public void deleteDeviceProperty(@PathVariable String deviceId, @PathVariable String deviceType, @PathVariable String propertyId
  ) {
    deviceApiService.deleteDeviceProperty(new DevicePropertyId(new DeviceId(deviceId, deviceTypeMapper.map(deviceType)), propertyId));
  }

  @PutMapping(path = "/{deviceType}-{deviceId}/properties/{propertyId}/values/{valueType}")
  public void updateDevicePropertyValue(@PathVariable String deviceId,
                                        @PathVariable String deviceType,
                                        @PathVariable String propertyId,
                                        @PathVariable String valueType,
                                        @RequestBody UpdateDevicePropertyValueDto updateDevicePropertyValueDto
  ) {
    deviceApiService.updateDevicePropertyValue(new DevicePropertyId(new DeviceId(deviceId, deviceTypeMapper.map(deviceType)), propertyId), valueType, updateDevicePropertyValueDto);
  }

}
