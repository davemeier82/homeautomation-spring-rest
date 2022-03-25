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

package io.github.davemeier82.homeautomation.spring.rest.v1.event;

import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

import static io.github.davemeier82.homeautomation.spring.rest.v1.HomeAutomationRestAutoConfiguration.API_PATH;

/**
 * REST Controller to stream events (SSE).
 *
 * @author David Meier
 * @since 0.1.0
 */
@ResponseBody
@RequestMapping(API_PATH + "events")
public class EventController {

  private final EventApiService eventApiService;

  public EventController(EventApiService eventApiService) {
    this.eventApiService = eventApiService;
  }

  @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ServerSentEvent<?>> streamEvents() {
    return eventApiService.streamEvents();
  }

}
