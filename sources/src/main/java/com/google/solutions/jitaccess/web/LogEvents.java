//
// Copyright 2022 Google LLC
//
// Licensed to the Apache Software Foundation (ASF) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The ASF licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.
//

package com.google.solutions.jitaccess.web;

public class LogEvents {
  public static final String API_LIST_ROLES = "api.listEligibleRoles";
  public static final String API_LIST_PEERS = "api.listPeers";
  public static final String API_ACTIVATE_ROLE = "api.activateRole";
  public static final String API_REQUEST_ROLE = "api.requestRole";
  public static final String API_GET_REQUEST = "api.getActivationRequest";
  public static final String RUNTIME_STARTUP = "runtime.startup";
  public static final String PUBLISH_MESSAGE = "pubsub.publish";

  private LogEvents() {
  }
}
