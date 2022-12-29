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
import io.github.davemeier82.homeautomation.spring.core.config.device.DeviceConfig;
import io.github.davemeier82.homeautomation.spring.core.config.device.DevicesConfig;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.DeviceDto;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

import static io.github.davemeier82.homeautomation.spring.rest.v1.HomeAutomationRestAutoConfiguration.API_PATH;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * REST Controller to retrieve and update {@link io.github.davemeier82.homeautomation.core.device.Device}'s.
 *
 * @author David Meier
 * @since 0.1.0
 */
@Controller
@ResponseBody
@ConditionalOnMissingBean
@RequestMapping(API_PATH + "devices")
public class DeviceController {

  private final DeviceApiService deviceApiService;

  public DeviceController(DeviceApiService deviceApiService) {
    this.deviceApiService = deviceApiService;
  }

  /**
   * @return all devices
   */
  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public List<DeviceDto> getDevices() {
    return deviceApiService.getDevices();
  }

  /**
   * Updates device properties
   *
   * @param deviceId   the device id
   * @param type       the device type
   * @param propertyId the device property id
   * @param body       the body depends on the property type (see {@link io.github.davemeier82.homeautomation.spring.rest.v1.device.updater.DevicePropertyUpdater})
   */
  @PutMapping(path = "/{deviceId}/{type}/{propertyId}")
  public void updateDevices(@PathVariable String deviceId,
                            @PathVariable String type,
                            @PathVariable long propertyId,
                            @RequestBody Map<String, Object> body
  ) {
    deviceApiService.updateDevice(new DeviceId(deviceId, type), propertyId, body);
  }

  /**
   * @return the config of all devices
   */
  @GetMapping(path = "/config", produces = APPLICATION_JSON_VALUE)
  public DevicesConfig getDevicesConfig() {
    return deviceApiService.getDevicesConfig();
  }

  /**
   * @param deviceId the device id
   * @param type     the device type
   * @return the config of a device
   */
  @GetMapping(path = "/config/{deviceId}/{type}", produces = APPLICATION_JSON_VALUE)
  public DeviceConfig getDeviceConfig(@PathVariable String deviceId,
                                      @PathVariable String type
  ) {
    return deviceApiService.getDeviceConfig(new DeviceId(deviceId, type)).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
  }

  /**
   * Updates the display name and the custom identifiers of a device
   *
   * @param deviceId     the device id
   * @param type         the device type
   * @param deviceConfig the device config
   */
  @PutMapping(path = "/config/{deviceId}/{type}")
  public void setDevicesConfig(@PathVariable String deviceId,
                               @PathVariable String type,
                               @RequestBody DeviceConfig deviceConfig
  ) {
    assert deviceConfig.type().equalsIgnoreCase(type);
    assert deviceConfig.id().equalsIgnoreCase(deviceId);
    deviceApiService.updateDevice(deviceConfig);
  }

  /**
   * Adds a new device.
   *
   * @param deviceConfig the config of the new device
   */
  @PostMapping(path = "/config")
  public void addDevicesConfig(@RequestBody DeviceConfig deviceConfig) {
    deviceApiService.addDevice(deviceConfig);
  }

}
