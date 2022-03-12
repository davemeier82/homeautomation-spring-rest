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

package io.github.davemeier82.homeautomation.spring.rest.v1.device.mapper;

import io.github.davemeier82.homeautomation.core.device.Device;
import io.github.davemeier82.homeautomation.core.device.property.*;
import io.github.davemeier82.homeautomation.core.event.DataWithTimestamp;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.*;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeviceToDtoMapper {

  public DeviceDto map(Device device) {
    List<DevicePropertyDto> properties = new ArrayList<>();
    DeviceDto deviceDto = new DeviceDto(device.getType(), device.getId(), device.getDisplayName(), properties, device.getCustomIdentifiers());

    for (DeviceProperty property : device.getDeviceProperties()) {
      DevicePropertyDto propertyDto = null;
      if (property instanceof Dimmer dimmer) {
        propertyDto = map(dimmer);
      } else if (property instanceof Relay relay) {
        propertyDto = map(relay);
      } else if (property instanceof WindowSensor sensor) {
        propertyDto = map(sensor);
      } else if (property instanceof TemperatureSensor sensor) {
        propertyDto = map(sensor);
      } else if (property instanceof HumiditySensor sensor) {
        propertyDto = map(sensor);
      } else if (property instanceof MotionSensor sensor) {
        propertyDto = map(sensor);
      } else if (property instanceof BatteryStateSensor sensor) {
        propertyDto = map(sensor);
      } else if (property instanceof Roller roller) {
        propertyDto = map(roller);
      } else if (property instanceof PowerSensor sensor) {
        propertyDto = map(sensor);
      } else if (property instanceof IlluminanceSensor sensor) {
        propertyDto = map(sensor);
      }
      if (propertyDto != null) {
        properties.add(propertyDto);
      }
    }
    return deviceDto;
  }

  private RollerPropertyDto map(Roller roller) {
    return new RollerPropertyDto(roller.getId(),
        getValueOrNull(roller.getState()),
        getTimestampOrNull(roller.getState()),
        getValueOrNull(roller.getPositionInPercent()),
        getTimestampOrNull(roller.getPositionInPercent())
    );
  }

  public RelayPropertyDto map(ReadOnlyRelay relay) {
    return new RelayPropertyDto(
        relay.getId(),
        getValueOrNull(relay.isOn()),
        getTimestampOrNull(relay.isOn())
    );
  }

  public DimmerPropertyDto map(Dimmer dimmer) {
    return new DimmerPropertyDto(
        dimmer.getId(),
        getValueOrNull(dimmer.isOn()),
        getTimestampOrNull(dimmer.isOn()),
        getValueOrNull(dimmer.getDimmingLevelInPercent()),
        getTimestampOrNull(dimmer.getDimmingLevelInPercent()));
  }

  public WindowSensorPropertyDto map(WindowSensor sensor) {
    return new WindowSensorPropertyDto(
        sensor.getId(),
        getValueOrNull(sensor.isOpen()),
        getTimestampOrNull(sensor.isOpen()),
        sensor.isTiltingSupported(),
        getValueOrNull(sensor.getTiltAngleInDegree()),
        getTimestampOrNull(sensor.getTiltAngleInDegree())
    );
  }

  public HumiditySensorPropertyDto map(HumiditySensor sensor) {
    return new HumiditySensorPropertyDto(
        sensor.getId(),
        getValueOrNull(sensor.getRelativeHumidityInPercent()),
        getTimestampOrNull(sensor.getRelativeHumidityInPercent())
    );
  }

  public TemperatureSensorPropertyDto map(TemperatureSensor sensor) {
    return new TemperatureSensorPropertyDto(
        sensor.getId(),
        getValueOrNull(sensor.getTemperatureInDegree()),
        getTimestampOrNull(sensor.getTemperatureInDegree())
    );
  }

  public BatteryStateSensorPropertyDto map(BatteryStateSensor sensor) {
    return new BatteryStateSensorPropertyDto(
        sensor.getId(),
        getValueOrNull(sensor.batteryLevelInPercent()),
        getTimestampOrNull(sensor.batteryLevelInPercent())
    );
  }

  public PowerSensorPropertyDto map(PowerSensor sensor) {
    return new PowerSensorPropertyDto(
        sensor.getId(),
        getValueOrNull(sensor.getWatt()),
        getTimestampOrNull(sensor.getWatt())
    );
  }

  public IlluminanceSensorPropertyDto map(IlluminanceSensor sensor) {
    return new IlluminanceSensorPropertyDto(
        sensor.getId(),
        getValueOrNull(sensor.getLux()),
        getTimestampOrNull(sensor.getLux())
    );
  }

  public MotionSensorPropertyDto map(MotionSensor sensor) {
    return new MotionSensorPropertyDto(sensor.getId(),
        getValueOrNull(sensor.getMotionDetected()),
        getTimestampOrNull(sensor.getMotionDetected()));
  }

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  private <T> T getValueOrNull(Optional<DataWithTimestamp<T>> dataWithTimestamp) {
    return dataWithTimestamp.map(DataWithTimestamp::getValue).orElse(null);
  }

  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  private <T> ZonedDateTime getTimestampOrNull(Optional<DataWithTimestamp<T>> dataWithTimestamp) {
    return dataWithTimestamp.map(DataWithTimestamp::getDateTime).orElse(null);
  }

}
