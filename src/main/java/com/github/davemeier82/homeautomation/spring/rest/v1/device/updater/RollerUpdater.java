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

package com.github.davemeier82.homeautomation.spring.rest.v1.device.updater;

import com.github.davemeier82.homeautomation.core.device.property.DeviceProperty;
import com.github.davemeier82.homeautomation.core.device.property.Roller;

import java.util.Map;

public class RollerUpdater implements DevicePropertyUpdater {


  @Override
  public boolean isSupported(DeviceProperty deviceProperty) {
    return deviceProperty instanceof Roller;
  }

  @Override
  public void update(DeviceProperty deviceProperty, Map<String, Object> body) {
    Roller roller = (Roller) deviceProperty;
    String state = (String) body.get("state");
    if (state != null) {
      switch (state.toLowerCase()) {
        case "open":
          roller.open();
          break;
        case "close":
          roller.close();
          break;
        case "stop":
          roller.stop();
          break;
        default:
          // do nothing
      }
    }
    Integer position = (Integer) body.get("position");
    if (position != null) {
      roller.setPosition(position);
    }
  }
}
