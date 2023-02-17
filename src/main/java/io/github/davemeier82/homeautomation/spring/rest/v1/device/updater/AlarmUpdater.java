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

package io.github.davemeier82.homeautomation.spring.rest.v1.device.updater;

import io.github.davemeier82.homeautomation.core.device.DeviceId;
import io.github.davemeier82.homeautomation.core.device.property.Alarm;
import io.github.davemeier82.homeautomation.core.device.property.AlarmState;
import io.github.davemeier82.homeautomation.core.device.property.DeviceProperty;
import io.github.davemeier82.homeautomation.core.device.property.Roller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Updates the state of a {@link Roller}.
 *
 * @author David Meier
 * @since 0.3.0
 */
public class AlarmUpdater implements DevicePropertyUpdater {
  private static final Logger log = LoggerFactory.getLogger(AlarmUpdater.class);

  @Override
  public boolean isSupported(DeviceProperty deviceProperty) {
    return deviceProperty instanceof Alarm;
  }

  /**
   * Updates the state of a {@link Alarm}.
   *
   * @param deviceProperty the property to update
   * @param body           needs to contain the attribute 'state'
   */
  @Override
  public void update(DeviceProperty deviceProperty, Map<String, Object> body) {
    Alarm alarm = (Alarm) deviceProperty;
    AlarmState state = AlarmState.valueOf((String) body.get("state"));
    log.debug("set alarm state of {} with propertyId {} to {}",
        DeviceId.deviceIdFromDevice(deviceProperty.getDevice()),
        deviceProperty.getId(),
        state);
    alarm.setState(state);
  }
}
