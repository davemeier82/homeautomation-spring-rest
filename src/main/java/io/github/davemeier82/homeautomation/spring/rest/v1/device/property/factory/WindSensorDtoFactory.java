/*
 * Copyright 2021-2024 the original author or authors.
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

package io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory;

import io.github.davemeier82.homeautomation.core.device.property.DeviceProperty;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyType;
import io.github.davemeier82.homeautomation.core.event.DataWithTimestamp;
import io.github.davemeier82.homeautomation.core.repositories.DevicePropertyValueRepository;
import io.github.davemeier82.homeautomation.spring.core.persistence.entity.LatestDevicePropertyValueEntity;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.DevicePropertyDto;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.dto.WindSensorPropertyDto;

import java.util.List;
import java.util.Optional;

import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyType.WIND_SENSOR;
import static io.github.davemeier82.homeautomation.core.device.property.DefaultDevicePropertyValueType.*;
import static io.github.davemeier82.homeautomation.spring.rest.v1.device.property.DataWithTimestampUtil.getTimestampOrNull;
import static io.github.davemeier82.homeautomation.spring.rest.v1.device.property.DataWithTimestampUtil.getValueOrNull;

public class WindSensorDtoFactory implements DevicePropertyDtoFactory {

  private final DevicePropertyValueRepository devicePropertyValueRepository;

  public WindSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    this.devicePropertyValueRepository = devicePropertyValueRepository;
  }

  @Override
  public DevicePropertyType supportedType() {
    return WIND_SENSOR;
  }

  @Override
  public DevicePropertyDto map(DeviceProperty deviceProperty) {
    Optional<DataWithTimestamp<Float>> windSpeed = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), WIND_SPEED, Float.class);
    Optional<DataWithTimestamp<Float>> windGustSpeed = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), WIND_GUST_SPEED, Float.class);
    Optional<DataWithTimestamp<Float>> windDirection = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), WIND_DIRECTION, Float.class);
    Optional<DataWithTimestamp<Float>> windGustDirection = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), WIND_GUST_DIRECTION, Float.class);
    Optional<DataWithTimestamp<Double>> windRun = devicePropertyValueRepository.findLatestValue(deviceProperty.getId(), WIND_RUN, Double.class);
    return new WindSensorPropertyDto(
        deviceProperty.getId().id(),
        deviceProperty.getType().getTypeName(),
        deviceProperty.getDisplayName(),
        getValueOrNull(windSpeed),
        getValueOrNull(windGustSpeed),
        getValueOrNull(windDirection),
        getValueOrNull(windGustDirection),
        getValueOrNull(windRun),
        getTimestampOrNull(windSpeed),
        getTimestampOrNull(windGustSpeed),
        getTimestampOrNull(windDirection),
        getTimestampOrNull(windGustDirection),
        getTimestampOrNull(windRun)
    );
  }

  @Override
  public DevicePropertyDto map(List<LatestDevicePropertyValueEntity> entities) {
    Optional<DataWithTimestamp<Float>> windSpeed = entities.stream().filter(e -> e.getId().getDevicePropertyValueType().equals(WIND_SPEED.getTypeName())).map(p ->
        new DataWithTimestamp<>(p.getTimestamp(), Float.valueOf(p.getValue()))).findFirst();
    Optional<DataWithTimestamp<Float>> windGustSpeed = entities.stream().filter(e -> e.getId().getDevicePropertyValueType().equals(WIND_GUST_SPEED.getTypeName())).map(p ->
        new DataWithTimestamp<>(p.getTimestamp(), Float.valueOf(p.getValue()))).findFirst();
    Optional<DataWithTimestamp<Float>> windDirection = entities.stream().filter(e -> e.getId().getDevicePropertyValueType().equals(WIND_DIRECTION.getTypeName())).map(p ->
        new DataWithTimestamp<>(p.getTimestamp(), Float.valueOf(p.getValue()))).findFirst();
    Optional<DataWithTimestamp<Float>> windGustDirection = entities.stream().filter(e -> e.getId().getDevicePropertyValueType().equals(WIND_GUST_DIRECTION.getTypeName())).map(p ->
        new DataWithTimestamp<>(p.getTimestamp(), Float.valueOf(p.getValue()))).findFirst();
    Optional<DataWithTimestamp<Double>> windRun = entities.stream().filter(e -> e.getId().getDevicePropertyValueType().equals(WIND_RUN.getTypeName())).map(p ->
        new DataWithTimestamp<>(p.getTimestamp(), Double.valueOf(p.getValue()))).findFirst();

    LatestDevicePropertyValueEntity deviceProperty = entities.getFirst();
    return new WindSensorPropertyDto(
        deviceProperty.getId().getDevicePropertyId(),
        deviceProperty.getDevicePropertyType(),
        deviceProperty.getDevicePropertyDisplayName(),
        getValueOrNull(windSpeed),
        getValueOrNull(windGustSpeed),
        getValueOrNull(windDirection),
        getValueOrNull(windGustDirection),
        getValueOrNull(windRun),
        getTimestampOrNull(windSpeed),
        getTimestampOrNull(windGustSpeed),
        getTimestampOrNull(windDirection),
        getTimestampOrNull(windGustDirection),
        getTimestampOrNull(windRun)
    );
  }
}
