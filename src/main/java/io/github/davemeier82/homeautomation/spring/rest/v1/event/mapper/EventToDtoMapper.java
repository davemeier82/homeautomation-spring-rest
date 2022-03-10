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

package io.github.davemeier82.homeautomation.spring.rest.v1.event.mapper;

import io.github.davemeier82.homeautomation.core.device.Device;
import io.github.davemeier82.homeautomation.core.device.property.DeviceProperty;
import io.github.davemeier82.homeautomation.core.device.property.RollerState;
import io.github.davemeier82.homeautomation.core.event.*;
import io.github.davemeier82.homeautomation.spring.rest.v1.event.EventDto;

public class EventToDtoMapper {

  public EventDto<?> map(DevicePropertyEvent event) {
    DeviceProperty deviceProperty = event.getDeviceProperty();
    Device device = deviceProperty.getDevice();
    if (event instanceof RelayStateChangedEvent relayEvent) {
      DataWithTimestamp<Boolean> previousValue = relayEvent.getPreviousValue();
      DataWithTimestamp<Boolean> on = relayEvent.isOn();
      return toEvent(deviceProperty, device, previousValue, on, "RelayStateChangedEvent");
    } else if (event instanceof TemperatureChangedEvent temperatureChangedEvent) {
      DataWithTimestamp<Float> previousValue = temperatureChangedEvent.getPreviousValue();
      DataWithTimestamp<Float> on = temperatureChangedEvent.getTemperatureInDegree();
      return toEvent(deviceProperty, device, previousValue, on, "TemperatureChangedEvent");
    } else if (event instanceof HumidityChangedEvent humidityChangedEvent) {
      DataWithTimestamp<Float> previousValue = humidityChangedEvent.getPreviousValue();
      DataWithTimestamp<Float> on = humidityChangedEvent.getRelativeHumidityInPercent();
      return toEvent(deviceProperty, device, previousValue, on, "HumidityChangedEvent");
    } else if (event instanceof DimmingLevelChangedEvent dimmingLevelChangedEvent) {
      DataWithTimestamp<Integer> previousValue = dimmingLevelChangedEvent.getPreviousValue();
      DataWithTimestamp<Integer> on = dimmingLevelChangedEvent.getDimmingLevelInPercent();
      return toEvent(deviceProperty, device, previousValue, on, "DimmingLevelChangedEvent");
    } else if (event instanceof IlluminanceChangedEvent humidityChangedEvent) {
      DataWithTimestamp<Integer> previousValue = humidityChangedEvent.getPreviousValue();
      DataWithTimestamp<Integer> on = humidityChangedEvent.getLux();
      return toEvent(deviceProperty, device, previousValue, on, "IlluminanceChangedEvent");
    } else if (event instanceof RollerStateChangedEvent rollerStateChangedEvent) {
      DataWithTimestamp<RollerState> previousValue = rollerStateChangedEvent.getPreviousState();
      DataWithTimestamp<RollerState> on = rollerStateChangedEvent.getState();
      return toEvent(deviceProperty, device, previousValue, on, "RollerStateChangedEvent");
    } else if (event instanceof RollerPositionChangedEvent rollerPositionChangedEvent) {
      DataWithTimestamp<Integer> previousValue = rollerPositionChangedEvent.getPreviousValue();
      DataWithTimestamp<Integer> on = rollerPositionChangedEvent.getPositionInPercent();
      return toEvent(deviceProperty, device, previousValue, on, "RollerPositionChangedEvent");
    } else if (event instanceof BatteryLevelChangedEvent batteryLevelChangedEvent) {
      DataWithTimestamp<Integer> previousValue = batteryLevelChangedEvent.getPreviousValue();
      DataWithTimestamp<Integer> on = batteryLevelChangedEvent.getBatteryLevelInPercent();
      return toEvent(deviceProperty, device, previousValue, on, "BatteryLevelChangedEvent");
    } else if (event instanceof PowerChangedEvent powerChangedEvent) {
      DataWithTimestamp<Double> previousValue = powerChangedEvent.getPreviousValue();
      DataWithTimestamp<Double> on = powerChangedEvent.getWatt();
      return toEvent(deviceProperty, device, previousValue, on, "PowerChangedEvent");
    } else if (event instanceof WindowStateChangedEvent windowStateChangedEvent) {
      DataWithTimestamp<Boolean> previousValue = windowStateChangedEvent.getPreviousValue();
      DataWithTimestamp<Boolean> on = windowStateChangedEvent.isOpen();
      return toEvent(deviceProperty, device, previousValue, on, "WindowStateChangedEvent");
    }

    return null;
  }

  private <T> EventDto<T> toEvent(DeviceProperty deviceProperty,
                                  Device device,
                                  DataWithTimestamp<T> previousValue,
                                  DataWithTimestamp<T> on,
                                  String propertyType
  ) {
    return new EventDto<>(device.getType(),
        device.getId(),
        propertyType,
        deviceProperty.getId(),
        on == null ? null : on.getValue(),
        previousValue == null ? null : previousValue.getValue(),
        on == null ? null : on.getDateTime(),
        previousValue == null ? null : previousValue.getDateTime());
  }
}
