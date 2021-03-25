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

package com.github.davemeier82.homeautomation.spring.rest.v1.device;

import com.github.davemeier82.homeautomation.core.device.Device;
import com.github.davemeier82.homeautomation.core.device.property.*;
import com.github.davemeier82.homeautomation.core.event.DataWithTimestamp;
import com.github.davemeier82.homeautomation.spring.rest.v1.device.dto.*;

import java.util.ArrayList;
import java.util.List;

public class DeviceToDtoMapper {

  public DeviceDto map(Device device) {
    List<DevicePropertyDto> properties = new ArrayList<>();
    DeviceDto deviceDto = new DeviceDto(device.getType(), device.getId(), device.getDisplayName(), properties);

    for (DeviceProperty property : device.getDeviceProperties()) {
      DevicePropertyDto propertyDto = null;
      if (property instanceof Dimmer) {
        propertyDto = map((Dimmer) property);
      } else if (property instanceof Relay) {
        propertyDto = map((Relay) property);
      } else if (property instanceof WindowSensor) {
        propertyDto = map((WindowSensor) property);
      } else if (property instanceof TemperatureSensor) {
        propertyDto = map((TemperatureSensor) property);
      } else if (property instanceof HumiditySensor) {
        propertyDto = map((HumiditySensor) property);
      } else if (property instanceof MotionSensor) {
        propertyDto = map((MotionSensor) property);
      } else if (property instanceof BatteryStateSensor) {
        propertyDto = map((BatteryStateSensor) property);
      }
      if (propertyDto != null) {
        properties.add(propertyDto);
      }
    }
    return deviceDto;
  }

  public RelayPropertyDto map(Relay relay) {
    return new RelayPropertyDto(
        relay.getId(),
        relay.isOn().map(DataWithTimestamp::getValue).orElse(null),
        relay.isOn().map(DataWithTimestamp::getDateTime).orElse(null)
    );
  }

  public DimmerPropertyDto map(Dimmer dimmer) {
    return new DimmerPropertyDto(
        dimmer.getId(),
        dimmer.getDimmingLevelInPercent().map(DataWithTimestamp::getValue).orElse(null),
        dimmer.getDimmingLevelInPercent().map(DataWithTimestamp::getDateTime).orElse(null));
  }

  public WindowSensorPropertyDto map(WindowSensor sensor) {
    return new WindowSensorPropertyDto(
        sensor.getId(),
        sensor.isOpen().map(DataWithTimestamp::getValue).orElse(null),
        sensor.isOpen().map(DataWithTimestamp::getDateTime).orElse(null),
        sensor.isTiltingSupported(),
        sensor.getTiltAngleInDegree().map(DataWithTimestamp::getValue).orElse(null),
        sensor.getTiltAngleInDegree().map(DataWithTimestamp::getDateTime).orElse(null));
  }

  public HumiditySensorPropertyDto map(HumiditySensor sensor) {
    return new HumiditySensorPropertyDto(
        sensor.getId(),
        sensor.getRelativeHumidityInPercent().map(DataWithTimestamp::getValue).orElse(null),
        sensor.getRelativeHumidityInPercent().map(DataWithTimestamp::getDateTime).orElse(null));
  }

  public TemperatureSensorPropertyDto map(TemperatureSensor sensor) {
    return new TemperatureSensorPropertyDto(
        sensor.getId(),
        sensor.getTemperatureInDegree().map(DataWithTimestamp::getValue).orElse(null),
        sensor.getTemperatureInDegree().map(DataWithTimestamp::getDateTime).orElse(null));
  }

  public BatteryStateSensorPropertyDto map(BatteryStateSensor sensor) {
    return new BatteryStateSensorPropertyDto(
        sensor.getId(),
        sensor.batteryLevelInPercent().map(DataWithTimestamp::getValue).orElse(null),
        sensor.batteryLevelInPercent().map(DataWithTimestamp::getDateTime).orElse(null));
  }

  public MotionSensorPropertyDto map(MotionSensor sensor) {
    return new MotionSensorPropertyDto(sensor.getId(), sensor.getLastMotionDetected().orElse(null));
  }

}
