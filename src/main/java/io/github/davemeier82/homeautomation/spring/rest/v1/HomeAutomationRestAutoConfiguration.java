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

import io.github.davemeier82.homeautomation.core.device.DeviceFactory;
import io.github.davemeier82.homeautomation.core.device.DeviceTypeMapper;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyFactory;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyTypeFactory;
import io.github.davemeier82.homeautomation.core.device.property.DevicePropertyValueTypeFactory;
import io.github.davemeier82.homeautomation.core.repositories.DevicePropertyRepository;
import io.github.davemeier82.homeautomation.core.repositories.DeviceRepository;
import io.github.davemeier82.homeautomation.spring.core.HomeAutomationCoreDeviceAutoConfiguration;
import io.github.davemeier82.homeautomation.spring.core.persistence.repository.JpaLatestDevicePropertyValueRepository;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.DeviceApiService;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.DeviceRestController;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.mapper.DeviceDtoMapper;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.dto.mapper.DevicePropertyDtoMapper;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.property.factory.DevicePropertyDtoFactory;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.updater.DevicePropertyUpdater;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@AutoConfigureAfter({HomeAutomationCoreDeviceAutoConfiguration.class, HomeAutomationRestDevicePropertyDtoFactoryAutoConfiguration.class})
public class HomeAutomationRestAutoConfiguration {

  public static final String API_PATH = "api/v1/";

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnBean(DevicePropertyRepository.class)
  DeviceDtoMapper deviceToDtoMapper(Set<DevicePropertyDtoFactory> devicePropertyDtoFactories,
                                    DevicePropertyRepository devicePropertyRepository,
                                    Set<DeviceFactory> deviceFactories,
                                    DeviceTypeMapper deviceTypeMapper,
                                    DeviceRepository deviceRepository
  ) {
    return new DeviceDtoMapper(devicePropertyDtoFactories, devicePropertyRepository, deviceFactories, deviceTypeMapper, deviceRepository);
  }

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnBean({DevicePropertyRepository.class, DevicePropertyTypeFactory.class})
  DevicePropertyDtoMapper devicePropertyDtoMapper(DevicePropertyFactory devicePropertyFactory, DevicePropertyTypeFactory devicePropertyTypeFactory) {
    return new DevicePropertyDtoMapper(devicePropertyFactory, devicePropertyTypeFactory);
  }

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnBean(DeviceRepository.class)
  DeviceApiService deviceApiService(DeviceRepository deviceRepository,
                                    DeviceDtoMapper deviceDtoMapper,
                                    Set<DevicePropertyValueTypeFactory> devicePropertyValueTypeFactories,
                                    DevicePropertyRepository devicePropertyRepository,
                                    JpaLatestDevicePropertyValueRepository latestDevicePropertyValueRepository,
                                    DevicePropertyDtoMapper devicePropertyDtoMapper,
                                    Set<DevicePropertyUpdater> devicePropertyUpdaters
  ) {
    return new DeviceApiService(deviceRepository, deviceDtoMapper, devicePropertyValueTypeFactories, devicePropertyRepository, latestDevicePropertyValueRepository, devicePropertyDtoMapper,
        devicePropertyUpdaters);
  }

  @Bean
  @ConditionalOnMissingBean
  @ConditionalOnBean({DeviceApiService.class, DeviceTypeMapper.class})
  DeviceRestController deviceController(DeviceApiService deviceApiService, DeviceTypeMapper deviceTypeMapper) {
    return new DeviceRestController(deviceApiService, deviceTypeMapper);
  }

}
