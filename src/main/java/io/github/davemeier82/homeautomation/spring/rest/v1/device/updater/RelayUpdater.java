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

package io.github.davemeier82.homeautomation.spring.rest.v1.device.updater;

import io.github.davemeier82.homeautomation.core.device.DeviceId;
import io.github.davemeier82.homeautomation.core.device.property.DeviceProperty;
import io.github.davemeier82.homeautomation.core.device.property.Relay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class RelayUpdater implements DevicePropertyUpdater {
  private static final Logger log = LoggerFactory.getLogger(RelayUpdater.class);

  @Override
  public boolean isSupported(DeviceProperty deviceProperty) {
    return deviceProperty instanceof Relay;
  }

  @Override
  public void update(DeviceProperty deviceProperty, Map<String, Object> body) {
    Relay relay = (Relay) deviceProperty;
    Boolean on = (Boolean) body.get("on");
    if (on != null) {
      log.debug("switch relay {} with propertyId {} to {}", DeviceId.deviceIdFromDevice(deviceProperty.getDevice()), deviceProperty.getId(), on);
      if (on) {
        relay.turnOn();
      } else {
        relay.turnOff();
      }
    }
  }
}
