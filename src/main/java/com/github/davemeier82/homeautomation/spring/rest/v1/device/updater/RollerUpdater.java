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

package com.github.davemeier82.homeautomation.spring.rest.v1.device.updater;

import com.github.davemeier82.homeautomation.core.device.DeviceId;
import com.github.davemeier82.homeautomation.core.device.property.DeviceProperty;
import com.github.davemeier82.homeautomation.core.device.property.Roller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RollerUpdater implements DevicePropertyUpdater {
  private static final Logger log = LoggerFactory.getLogger(RollerUpdater.class);

  @Override
  public boolean isSupported(DeviceProperty deviceProperty) {
    return deviceProperty instanceof Roller;
  }

  @Override
  public void update(DeviceProperty deviceProperty, Map<String, Object> body) {
    Roller roller = (Roller) deviceProperty;
    String state = (String) body.get("state");
    if (state != null) {
      log.debug("set roller state of {} with propertyId {} to {}",
          DeviceId.deviceIdFromDevice(deviceProperty.getDevice()),
          deviceProperty.getId(),
          state);
      switch (state.toLowerCase()) {
        case "open" -> roller.open();
        case "close" -> roller.close();
        case "stop" -> roller.stop();
        default -> {
          // do nothing
        }
      }
    }
    Integer position = (Integer) body.get("position");
    if (position != null) {
      log.debug("set roller position of {} with propertyId {} to {}",
          DeviceId.deviceIdFromDevice(deviceProperty.getDevice()),
          deviceProperty.getId(),
          position);
      roller.setPosition(position);
    }
  }
}
