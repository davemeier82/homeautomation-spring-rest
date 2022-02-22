/*
 * Copyright 2021-2022 the original author or authors.
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

package com.github.davemeier82.homeautomation.spring.rest.v1.event.mapper;

import com.github.davemeier82.homeautomation.core.device.Device;
import com.github.davemeier82.homeautomation.core.device.property.DeviceProperty;
import com.github.davemeier82.homeautomation.core.event.DataWithTimestamp;
import com.github.davemeier82.homeautomation.core.event.DevicePropertyEvent;
import com.github.davemeier82.homeautomation.core.event.RelayStateChangedEvent;
import com.github.davemeier82.homeautomation.spring.rest.v1.event.EventDto;

public class EventToDtoMapper {

  public EventDto<?> map(DevicePropertyEvent event) {
    DeviceProperty deviceProperty = event.getDeviceProperty();
    Device device = deviceProperty.getDevice();
    if (event instanceof RelayStateChangedEvent relayEvent) {
      DataWithTimestamp<Boolean> previousValue = relayEvent.getPreviousValue();
      DataWithTimestamp<Boolean> on = relayEvent.isOn();
      return new EventDto<>(device.getType(),
          device.getId(),
          "Relay",
          deviceProperty.getId(),
          on == null ? null : on.getValue(),
          previousValue == null ? null : previousValue.getValue(),
          on == null ? null : on.getDateTime(),
          previousValue == null ? null : previousValue.getDateTime());
    }

    return null;
  }
}
