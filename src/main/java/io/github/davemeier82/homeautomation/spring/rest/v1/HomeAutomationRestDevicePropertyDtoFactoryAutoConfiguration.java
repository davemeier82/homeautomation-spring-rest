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

package io.github.davemeier82.homeautomation.spring.rest.v1;

import io.github.davemeier82.homeautomation.core.device.DeviceTypeMapper;
import io.github.davemeier82.homeautomation.core.repositories.DevicePropertyValueRepository;
import io.github.davemeier82.homeautomation.spring.core.HomeAutomationCorePersistenceAutoConfiguration;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.AlarmDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.BatteryStateSensorDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.CloudBaseSensorDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.Co2SensorDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.DimmerDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.HumiditySensorDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.IlluminanceSensorDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.MotionSensorDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.PowerSensorDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.PressureSensorDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.RainSensorDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.RelayDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.RollerDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.SmokeSensorDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.TemperatureSensorDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.UvIndexSensorDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.WindSensorDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.WindowSensorDtoFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(HomeAutomationCorePersistenceAutoConfiguration.class)
public class HomeAutomationRestDevicePropertyDtoFactoryAutoConfiguration {

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  AlarmDtoFactory alarmDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new AlarmDtoFactory(devicePropertyValueRepository);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  BatteryStateSensorDtoFactory batteryStateSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new BatteryStateSensorDtoFactory(devicePropertyValueRepository);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  CloudBaseSensorDtoFactory cloudBaseSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new CloudBaseSensorDtoFactory(devicePropertyValueRepository);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  Co2SensorDtoFactory co2SensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new Co2SensorDtoFactory(devicePropertyValueRepository);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  DimmerDtoFactory dimmerDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new DimmerDtoFactory(devicePropertyValueRepository);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  HumiditySensorDtoFactory humiditySensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new HumiditySensorDtoFactory(devicePropertyValueRepository);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  IlluminanceSensorDtoFactory illuminanceSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new IlluminanceSensorDtoFactory(devicePropertyValueRepository);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  MotionSensorDtoFactory motionSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository, DeviceTypeMapper deviceTypeMapper) {
    return new MotionSensorDtoFactory(devicePropertyValueRepository, deviceTypeMapper);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  PowerSensorDtoFactory powerSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new PowerSensorDtoFactory(devicePropertyValueRepository);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  PressureSensorDtoFactory pressureSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new PressureSensorDtoFactory(devicePropertyValueRepository);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  RainSensorDtoFactory rainSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new RainSensorDtoFactory(devicePropertyValueRepository);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  RelayDtoFactory relayDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new RelayDtoFactory(devicePropertyValueRepository);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  RollerDtoFactory rollerDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new RollerDtoFactory(devicePropertyValueRepository);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  SmokeSensorDtoFactory smokeSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new SmokeSensorDtoFactory(devicePropertyValueRepository);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  TemperatureSensorDtoFactory temperatureSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new TemperatureSensorDtoFactory(devicePropertyValueRepository);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  UvIndexSensorDtoFactory uvIndexSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new UvIndexSensorDtoFactory(devicePropertyValueRepository);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  WindowSensorDtoFactory windowSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new WindowSensorDtoFactory(devicePropertyValueRepository);
  }

  @Bean
  @ConditionalOnBean(DevicePropertyValueRepository.class)
  @ConditionalOnMissingBean
  WindSensorDtoFactory windSensorDtoFactory(DevicePropertyValueRepository devicePropertyValueRepository) {
    return new WindSensorDtoFactory(devicePropertyValueRepository);
  }

}
