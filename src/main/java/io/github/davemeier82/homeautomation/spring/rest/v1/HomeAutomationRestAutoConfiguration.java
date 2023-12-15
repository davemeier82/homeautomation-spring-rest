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

import io.github.davemeier82.homeautomation.spring.core.DeviceRegistry;
import io.github.davemeier82.homeautomation.spring.core.HomeAutomationCoreAutoConfiguration;
import io.github.davemeier82.homeautomation.spring.core.config.device.DeviceConfigFactory;
import io.github.davemeier82.homeautomation.spring.core.config.device.DeviceConfigWriter;
import io.github.davemeier82.homeautomation.spring.core.config.device.DeviceLoader;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.DeviceApiService;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.DeviceController;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.mapper.DeviceToDtoMapper;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.updater.AlarmUpdater;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.updater.DimmerUpdater;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.updater.RelayUpdater;
import io.github.davemeier82.homeautomation.spring.rest.v1.device.updater.RollerUpdater;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * Auto configuration.
 *
 * @author David Meier
 * @since 0.1.0
 */
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
  DeviceApiService deviceApiService(DeviceRegistry deviceRegistry,
                                    DeviceToDtoMapper deviceToDtoMapper,
                                    DeviceConfigFactory deviceConfigFactory,
                                    DeviceLoader deviceLoader,
                                    DeviceConfigWriter deviceConfigWriter
  ) {
    return new DeviceApiService(deviceRegistry,
        deviceToDtoMapper,
        Set.of(new RelayUpdater(), new RollerUpdater(), new DimmerUpdater(), new AlarmUpdater()),
        deviceConfigFactory,
        deviceLoader,
        deviceConfigWriter);
  }

  @Bean
  @ConditionalOnMissingBean
  DeviceController deviceController(DeviceApiService deviceApiService) {
    return new DeviceController(deviceApiService);
  }

}
