/*
 * Copyright 2021-2022 the original author or authors.
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

package com.github.davemeier82.homeautomation.spring.rest.v1.event;

import com.github.davemeier82.homeautomation.core.event.DevicePropertyEvent;
import com.github.davemeier82.homeautomation.spring.rest.v1.event.mapper.EventToDtoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.codec.ServerSentEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;

import static reactor.core.publisher.Sinks.EmitResult.*;

public class EventApiService {
  private static final Logger log = LoggerFactory.getLogger(EventApiService.class);
  private final Sinks.Many<EventDto<?>> devicePropertyEventSink = Sinks.many().multicast().directBestEffort();
  private final EventToDtoMapper eventToDtoMapper;

  public EventApiService(EventToDtoMapper eventToDtoMapper) {
    this.eventToDtoMapper = eventToDtoMapper;
  }

  public Flux<ServerSentEvent<?>> streamEvents() {
    return Flux.merge(devicePropertyEventSink.asFlux()
            .map(dto -> ServerSentEvent.builder(dto).build()),
        getHeartbeat());

  }

  private Flux<ServerSentEvent<?>> getHeartbeat() {
    return Flux.interval(Duration.ofSeconds(15)).map(aLong -> ServerSentEvent.builder().comment("heartbeat").build());
  }

  @EventListener
  public synchronized void handleEvent(DevicePropertyEvent event) {
    EventDto<?> eventDto = eventToDtoMapper.map(event);
    if (eventDto != null) {
      Sinks.EmitResult emitResult = devicePropertyEventSink.tryEmitNext(eventDto);
      if (FAIL_CANCELLED == emitResult || FAIL_TERMINATED == emitResult || FAIL_NON_SERIALIZED == emitResult) {
        log.error("failed to emit event for device {} propertyId {}",
            event.getDeviceProperty().getDevice().getDisplayName(),
            event.getDeviceProperty().getId());
      }
    }
  }
}
