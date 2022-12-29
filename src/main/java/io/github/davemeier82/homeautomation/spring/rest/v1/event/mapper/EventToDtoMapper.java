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

import java.util.Optional;

/**
 * Maps {@link DevicePropertyEvent} to {@link EventDto}.
 *
 * @author David Meier
 * @since 0.1.0
 */
public class EventToDtoMapper {

  public EventDto<?> map(DevicePropertyEvent event) {
    DeviceProperty deviceProperty = event.getDeviceProperty();
    Device device = deviceProperty.getDevice();
    if (event instanceof RelayStateChangedEvent relayEvent) {
      DataWithTimestamp<Boolean> on = relayEvent.isOn();
      return toEvent(deviceProperty, device, relayEvent.getPreviousValue(), on, "RelayStateChangedEvent");
    } else if (event instanceof TemperatureChangedEvent temperatureChangedEvent) {
      DataWithTimestamp<Float> on = temperatureChangedEvent.getTemperatureInDegree();
      return toEvent(deviceProperty, device, temperatureChangedEvent.getPreviousValue(), on, "TemperatureChangedEvent");
    } else if (event instanceof HumidityChangedEvent humidityChangedEvent) {
      DataWithTimestamp<Float> on = humidityChangedEvent.getRelativeHumidityInPercent();
      return toEvent(deviceProperty, device, humidityChangedEvent.getPreviousValue(), on, "HumidityChangedEvent");
    } else if (event instanceof DimmingLevelChangedEvent dimmingLevelChangedEvent) {
      DataWithTimestamp<Integer> on = dimmingLevelChangedEvent.getDimmingLevelInPercent();
      return toEvent(deviceProperty, device, dimmingLevelChangedEvent.getPreviousValue(), on, "DimmingLevelChangedEvent");
    } else if (event instanceof IlluminanceChangedEvent humidityChangedEvent) {
      DataWithTimestamp<Integer> on = humidityChangedEvent.getLux();
      return toEvent(deviceProperty, device, humidityChangedEvent.getPreviousValue(), on, "IlluminanceChangedEvent");
    } else if (event instanceof RollerStateChangedEvent rollerStateChangedEvent) {
      DataWithTimestamp<RollerState> on = rollerStateChangedEvent.getState();
      return toEvent(deviceProperty, device, rollerStateChangedEvent.getPreviousValue(), on, "RollerStateChangedEvent");
    } else if (event instanceof RollerPositionChangedEvent rollerPositionChangedEvent) {
      DataWithTimestamp<Integer> on = rollerPositionChangedEvent.getPositionInPercent();
      return toEvent(deviceProperty, device, rollerPositionChangedEvent.getPreviousValue(), on, "RollerPositionChangedEvent");
    } else if (event instanceof BatteryLevelChangedEvent batteryLevelChangedEvent) {
      DataWithTimestamp<Integer> on = batteryLevelChangedEvent.getBatteryLevelInPercent();
      return toEvent(deviceProperty, device, batteryLevelChangedEvent.getPreviousValue(), on, "BatteryLevelChangedEvent");
    } else if (event instanceof PowerChangedEvent powerChangedEvent) {
      DataWithTimestamp<Double> on = powerChangedEvent.getWatt();
      return toEvent(deviceProperty, device, powerChangedEvent.getPreviousValue(), on, "PowerChangedEvent");
    } else if (event instanceof WindowStateChangedEvent windowStateChangedEvent) {
      DataWithTimestamp<Boolean> on = windowStateChangedEvent.isOpen();
      return toEvent(deviceProperty, device, windowStateChangedEvent.getPreviousValue(), on, "WindowStateChangedEvent");
    } else if (event instanceof MotionChangedEvent motionChangedEvent) {
      DataWithTimestamp<Boolean> motionDetected = motionChangedEvent.motionDetected();
      return toEvent(deviceProperty, device, motionChangedEvent.getPreviousValue(), motionDetected, "MotionChangedEvent");
    } else if (event instanceof RelayStateUpdatedEvent relayEvent) {
      DataWithTimestamp<Boolean> on = relayEvent.isOn();
      return toEvent(deviceProperty, device, relayEvent.getPreviousValue(), on, "RelayStateUpdatedEvent");
    } else if (event instanceof TemperatureUpdatedEvent temperatureUpdatedEvent) {
      DataWithTimestamp<Float> on = temperatureUpdatedEvent.getTemperatureInDegree();
      return toEvent(deviceProperty, device, temperatureUpdatedEvent.getPreviousValue(), on, "TemperatureUpdatedEvent");
    } else if (event instanceof HumidityUpdatedEvent humidityUpdatedEvent) {
      DataWithTimestamp<Float> on = humidityUpdatedEvent.getRelativeHumidityInPercent();
      return toEvent(deviceProperty, device, humidityUpdatedEvent.getPreviousValue(), on, "HumidityUpdatedEvent");
    } else if (event instanceof DimmingLevelUpdatedEvent dimmingLevelUpdatedEvent) {
      DataWithTimestamp<Integer> on = dimmingLevelUpdatedEvent.getDimmingLevelInPercent();
      return toEvent(deviceProperty, device, dimmingLevelUpdatedEvent.getPreviousValue(), on, "DimmingLevelUpdatedEvent");
    } else if (event instanceof IlluminanceUpdatedEvent illuminanceUpdatedEvent) {
      DataWithTimestamp<Integer> on = illuminanceUpdatedEvent.getLux();
      return toEvent(deviceProperty, device, illuminanceUpdatedEvent.getPreviousValue(), on, "IlluminanceUpdatedEvent");
    } else if (event instanceof RollerStateUpdatedEvent rollerStateUpdatedEvent) {
      DataWithTimestamp<RollerState> on = rollerStateUpdatedEvent.getState();
      return toEvent(deviceProperty, device, rollerStateUpdatedEvent.getPreviousValue(), on, "RollerStateUpdatedEvent");
    } else if (event instanceof RollerPositionUpdatedEvent rollerPositionUpdatedEvent) {
      DataWithTimestamp<Integer> on = rollerPositionUpdatedEvent.getPositionInPercent();
      return toEvent(deviceProperty, device, rollerPositionUpdatedEvent.getPreviousValue(), on, "RollerPositionUpdatedEvent");
    } else if (event instanceof BatteryLevelUpdatedEvent batteryLevelUpdatedEvent) {
      DataWithTimestamp<Integer> on = batteryLevelUpdatedEvent.getBatteryLevelInPercent();
      return toEvent(deviceProperty, device, batteryLevelUpdatedEvent.getPreviousValue(), on, "BatteryLevelUpdatedEvent");
    } else if (event instanceof PowerUpdatedEvent powerUpdatedEvent) {
      DataWithTimestamp<Double> on = powerUpdatedEvent.getWatt();
      return toEvent(deviceProperty, device, powerUpdatedEvent.getPreviousValue(), on, "PowerUpdatedEvent");
    } else if (event instanceof WindowStateUpdatedEvent windowStateUpdatedEvent) {
      DataWithTimestamp<Boolean> on = windowStateUpdatedEvent.isOpen();
      return toEvent(deviceProperty, device, windowStateUpdatedEvent.getPreviousValue(), on, "WindowStateUpdatedEvent");
    } else if (event instanceof MotionUpdatedEvent motionUpdatedEvent) {
      DataWithTimestamp<Boolean> motionDetected = motionUpdatedEvent.motionDetected();
      return toEvent(deviceProperty, device, motionUpdatedEvent.getPreviousValue(), motionDetected, "MotionUpdatedEvent");
    }

    return null;
  }

  @SuppressWarnings({"OptionalUsedAsFieldOrParameterType", "unchecked"})
  private <T> EventDto<T> toEvent(DeviceProperty deviceProperty,
                                  Device device,
                                  Optional<DataWithTimestamp<?>> previousValue,
                                  DataWithTimestamp<T> value,
                                  String propertyType
  ) {
    return new EventDto<>(device.getType(),
        device.getId(),
        propertyType,
        deviceProperty.getId(),
        value == null ? null : value.getValue(),
        (T) previousValue.map(DataWithTimestamp::getValue).orElse(null),
        value == null ? null : value.getDateTime(),
        previousValue.map(DataWithTimestamp::getDateTime).orElse(null));
  }
}
