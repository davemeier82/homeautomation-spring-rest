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

package io.github.davemeier82.homeautomation.spring.rest.v1.device.dto;

import java.util.Map;

public class AddDeviceDto {

  private final String type;
  private final String id;
  private final String displayName;
  private final Map<String, String> parameters;
  private final Map<String, String> customIdentifiers;

  public AddDeviceDto(String type, String id, String displayName, Map<String, String> parameters, Map<String, String> customIdentifiers) {
    this.type = type;
    this.id = id;
    this.displayName = displayName;
    this.parameters = parameters;
    this.customIdentifiers = customIdentifiers;
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

  public Map<String, String> getCustomIdentifiers() {
    return customIdentifiers;
  }

  public Map<String, String> getParameters() {
    return parameters;
  }
}
