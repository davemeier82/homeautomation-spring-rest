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

import io.github.davemeier82.homeautomation.core.device.DeviceType;
import io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyType;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyId;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyType;
import io.github.davemeier82.homeautomation.core.device.property.RelayDevicePropertyController;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toMap;

public class RelayPropertyUpdater implements DevicePropertyUpdater {

  private final Map<? extends DeviceType, ? extends RelayDevicePropertyController> controllerByDeviceType;

  public RelayPropertyUpdater(Set<? extends RelayDevicePropertyController> relayDevicePropertyControllers) {
    controllerByDeviceType = relayDevicePropertyControllers.stream()
                                                           .flatMap(c -> c.getSupportedDeviceTypes().stream()
                                                                          .map(t -> new AbstractMap.SimpleImmutableEntry<>(t, c)))
                                                           .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  @Override
  public DevicePropertyType getSupportedDevicePropertyType() {
    return DefaultDevicePropertyType.RELAY;
  }

  @Override
  public void update(DevicePropertyId devicePropertyId, Object value) {
    RelayDevicePropertyController relayDevicePropertyController = controllerByDeviceType.get(devicePropertyId.deviceId().type());
    if ((Boolean) value) {
      relayDevicePropertyController.turnOn(devicePropertyId);
    } else {
      relayDevicePropertyController.turnOff(devicePropertyId);
    }
  }
}
