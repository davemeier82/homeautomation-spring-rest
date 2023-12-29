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

/**
 * Maps {@link Device} to {@link DeviceDto}.
 *
 * @author David Meier
 * @since 0.1.0
 */
public class DeviceToDtoMapper {

  public DeviceDto map(Device device) {
    List<DevicePropertyDto> properties = new ArrayList<>();
    DeviceDto deviceDto = new DeviceDto(device.getType(), device.getId(), device.getDisplayName(), properties, device.getCustomIdentifiers());

    for (DeviceProperty property : device.getDeviceProperties()) {
      DevicePropertyDto propertyDto = mapToDto(property);
      if (propertyDto != null) {
        properties.add(propertyDto);
      }
    }
    return deviceDto;
  }

  private DevicePropertyDto mapToDto(DeviceProperty deviceProperty) {
    return switch (deviceProperty) {
      case Dimmer dimmer -> map(dimmer);
      case Relay relay -> map(relay);
      case WindowSensor sensor -> map(sensor);
      case TemperatureSensor sensor -> map(sensor);
      case HumiditySensor sensor -> map(sensor);
      case MotionSensor sensor -> map(sensor);
      case BatteryStateSensor sensor -> map(sensor);
      case Roller sensor -> map(sensor);
      case PowerSensor sensor -> map(sensor);
      case IlluminanceSensor sensor -> map(sensor);
      case SmokeSensor sensor -> map(sensor);
      case Co2Sensor sensor -> map(sensor);
      case Alarm sensor -> map(sensor);
      case PressureSensor sensor -> map(sensor);
      case CloudBaseSensor sensor -> map(sensor);
      case UvSensor sensor -> map(sensor);
      case RainSensor sensor -> map(sensor);
      case WindSensor sensor -> map(sensor);
      default -> null;
    };
  }

  private RollerPropertyDto map(Roller roller) {
    return new RollerPropertyDto(roller.getId(),
        roller.getLabel(),
        getValueOrNull(roller.getState()),
        getTimestampOrNull(roller.getState()),
        getValueOrNull(roller.getPositionInPercent()),
        getTimestampOrNull(roller.getPositionInPercent())
    );
  }

  public RelayPropertyDto map(ReadOnlyRelay relay) {
    return new RelayPropertyDto(
        relay.getId(),
        relay.getLabel(),
        getValueOrNull(relay.isOn()),
        getTimestampOrNull(relay.isOn())
    );
  }

  public DimmerPropertyDto map(Dimmer dimmer) {
    return new DimmerPropertyDto(
        dimmer.getId(),
        dimmer.getLabel(),
        getValueOrNull(dimmer.isOn()),
        getTimestampOrNull(dimmer.isOn()),
        getValueOrNull(dimmer.getDimmingLevelInPercent()),
        getTimestampOrNull(dimmer.getDimmingLevelInPercent()));
  }

  public WindowSensorPropertyDto map(WindowSensor sensor) {
    return new WindowSensorPropertyDto(
        sensor.getId(),
        sensor.getLabel(),
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
        sensor.getLabel(),
        getValueOrNull(sensor.getRelativeHumidityInPercent()),
        getTimestampOrNull(sensor.getRelativeHumidityInPercent())
    );
  }

  public TemperatureSensorPropertyDto map(TemperatureSensor sensor) {
    return new TemperatureSensorPropertyDto(
        sensor.getId(),
        sensor.getLabel(),
        getValueOrNull(sensor.getTemperatureInDegree()),
        getTimestampOrNull(sensor.getTemperatureInDegree())
    );
  }

  public BatteryStateSensorPropertyDto map(BatteryStateSensor sensor) {
    return new BatteryStateSensorPropertyDto(
        sensor.getId(),
        sensor.getLabel(),
        getValueOrNull(sensor.batteryLevelInPercent()),
        getTimestampOrNull(sensor.batteryLevelInPercent())
    );
  }

  public PowerSensorPropertyDto map(PowerSensor sensor) {
    return new PowerSensorPropertyDto(
        sensor.getId(),
        sensor.getLabel(),
        getValueOrNull(sensor.getWatt()),
        getTimestampOrNull(sensor.getWatt())
    );
  }

  public IlluminanceSensorPropertyDto map(IlluminanceSensor sensor) {
    return new IlluminanceSensorPropertyDto(
        sensor.getId(),
        sensor.getLabel(),
        getValueOrNull(sensor.getLux()),
        getTimestampOrNull(sensor.getLux())
    );
  }

  public MotionSensorPropertyDto map(MotionSensor sensor) {
    return new MotionSensorPropertyDto(sensor.getId(),
        sensor.getLabel(),
        getValueOrNull(sensor.getMotionDetected()),
        sensor.getLastMotionDetected().orElse(null),
        getTimestampOrNull(sensor.getMotionDetected()));
  }

  public Co2SensorPropertyDto map(Co2Sensor sensor) {
    return new Co2SensorPropertyDto(
        sensor.getId(),
        sensor.getLabel(),
        getValueOrNull(sensor.getPpm()),
        getTimestampOrNull(sensor.getPpm())
    );
  }

  public SmokeSensorPropertyDto map(SmokeSensor sensor) {
    return new SmokeSensorPropertyDto(
        sensor.getId(),
        sensor.getLabel(),
        getValueOrNull(sensor.isSmokeDetected()),
        getTimestampOrNull(sensor.isSmokeDetected())
    );
  }

  public AlarmPropertyDto map(Alarm alarm) {
    return new AlarmPropertyDto(
        alarm.getId(),
        alarm.getLabel(),
        getValueOrNull(alarm.getState()),
        getTimestampOrNull(alarm.getState())
    );
  }

  public CloudBaseSensorPropertyDto map(CloudBaseSensor sensor) {
    return new CloudBaseSensorPropertyDto(
        sensor.getId(),
        sensor.getLabel(),
        getValueOrNull(sensor.getMeter()),
        getTimestampOrNull(sensor.getMeter())
    );
  }

  public PressureSensorPropertyDto map(PressureSensor sensor) {
    return new PressureSensorPropertyDto(
        sensor.getId(),
        sensor.getLabel(),
        getValueOrNull(sensor.getMillibar()),
        getTimestampOrNull(sensor.getMillibar())
    );
  }

  public UvSensorPropertyDto map(UvSensor sensor) {
    return new UvSensorPropertyDto(
        sensor.getId(),
        sensor.getLabel(),
        getValueOrNull(sensor.getIndex()),
        getTimestampOrNull(sensor.getIndex())
    );
  }

  public WindSensorPropertyDto map(WindSensor sensor) {
    return new WindSensorPropertyDto(
        sensor.getId(),
        sensor.getLabel(),
        getValueOrNull(sensor.getSpeedInKmh()),
        getValueOrNull(sensor.getGustSpeedInKmh()),
        getValueOrNull(sensor.getDirectionInDegree()),
        getValueOrNull(sensor.getGustDirectionInDegree()),
        getValueOrNull(sensor.getIntervalRunInKm()),
        getTimestampOrNull(sensor.getSpeedInKmh()),
        getTimestampOrNull(sensor.getGustSpeedInKmh()),
        getTimestampOrNull(sensor.getDirectionInDegree()),
        getTimestampOrNull(sensor.getGustDirectionInDegree()),
        getTimestampOrNull(sensor.getIntervalRunInKm()));
  }

  public RainSensorPropertyDto map(RainSensor sensor) {
    return new RainSensorPropertyDto(
        sensor.getId(),
        sensor.getLabel(),
        getValueOrNull(sensor.getRateInMmph()),
        getValueOrNull(sensor.getIntervalMm()),
        getValueOrNull(sensor.getTodayInMm()),
        getTimestampOrNull(sensor.getRateInMmph()),
        getTimestampOrNull(sensor.getIntervalMm()),
        getTimestampOrNull(sensor.getTodayInMm()));
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
