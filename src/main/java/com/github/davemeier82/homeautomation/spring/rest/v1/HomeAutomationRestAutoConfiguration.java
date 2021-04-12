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

package com.github.davemeier82.homeautomation.spring.rest.v1;

import com.github.davemeier82.homeautomation.spring.core.DeviceRegistry;
import com.github.davemeier82.homeautomation.spring.core.HomeAutomationCoreAutoConfiguration;
import com.github.davemeier82.homeautomation.spring.rest.v1.device.DeviceApiService;
import com.github.davemeier82.homeautomation.spring.rest.v1.device.DeviceController;
import com.github.davemeier82.homeautomation.spring.rest.v1.device.mapper.DeviceToDtoMapper;
import com.github.davemeier82.homeautomation.spring.rest.v1.device.updater.RelayUpdater;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
@AutoConfigureAfter(HomeAutomationCoreAutoConfiguration.class)
public class HomeAutomationRestAutoConfiguration {

  public static final String API_PATH = "api/v1/";

  @Bean
  @ConditionalOnMissingBean
  DeviceToDtoMapper deviceToDtoMapper() {
    return new DeviceToDtoMapper();
  }

  @Bean
  @ConditionalOnMissingBean
  DeviceApiService deviceApiService(DeviceRegistry deviceRegistry, DeviceToDtoMapper deviceToDtoMapper) {
    return new DeviceApiService(deviceRegistry, deviceToDtoMapper, Set.of(new RelayUpdater()));
  }

  @Bean
  @ConditionalOnMissingBean
  DeviceController deviceController(DeviceApiService deviceApiService) {
    return new DeviceController(deviceApiService);
  }
}
