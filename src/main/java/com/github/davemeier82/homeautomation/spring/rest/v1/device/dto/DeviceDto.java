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

package com.github.davemeier82.homeautomation.spring.rest.v1.device.dto;

import java.util.List;
import java.util.Map;

public class DeviceDto {

  private final String type;
  private final String id;
  private final String displayName;
  private final List<DevicePropertyDto> properties;
  private final Map<String, String> customIdentifiers;


  public DeviceDto(String type,
                   String id,
                   String displayName,
                   List<DevicePropertyDto> properties,
                   Map<String, String> identifiers
  ) {
    this.type = type;
    this.id = id;
    this.displayName = displayName;
    this.properties = properties;
    customIdentifiers = identifiers;
  }

  public String getType() {
    return type;
  }

  public String getId() {
    return id;
  }

  public String getDisplayName() {
    return displayName;
  }

  public List<DevicePropertyDto> getProperties() {
    return properties;
  }

  public Map<String, String> getCustomIdentifiers() {
    return customIdentifiers;
  }
}
