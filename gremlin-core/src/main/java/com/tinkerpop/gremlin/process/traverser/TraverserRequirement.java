/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.tinkerpop.gremlin.process.traverser;

/**
 * A {@link TraverserRequirement} is a list of requirements that a {@link com.tinkerpop.gremlin.process.Traversal} requires of a {@link com.tinkerpop.gremlin.process.Traverser}.
 * The less requirements, the simpler the traverser can be (both in terms of space and time constraints).
 * Every {@link com.tinkerpop.gremlin.process.Step} provides its specific requirements via {@link com.tinkerpop.gremlin.process.Step#getRequirements()}.
 *
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public enum TraverserRequirement {

    OBJECT,
    BULK,
    SINGLE_LOOP,
    NESTED_LOOP,
    PATH,
    PATH_ACCESS,
    SACK,
    SIDE_EFFECTS,
    // NESTED_TRAVERSALS
}
