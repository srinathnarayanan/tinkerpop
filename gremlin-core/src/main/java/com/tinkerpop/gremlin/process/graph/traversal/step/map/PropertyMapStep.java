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
package com.tinkerpop.gremlin.process.graph.traversal.step.map;

import com.tinkerpop.gremlin.process.T;
import com.tinkerpop.gremlin.process.Traversal;
import com.tinkerpop.gremlin.process.traverser.TraverserRequirement;
import com.tinkerpop.gremlin.process.traversal.util.TraversalHelper;
import com.tinkerpop.gremlin.structure.Element;
import com.tinkerpop.gremlin.structure.PropertyType;
import com.tinkerpop.gremlin.structure.Vertex;
import com.tinkerpop.gremlin.structure.VertexProperty;
import com.tinkerpop.gremlin.structure.util.ElementHelper;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class PropertyMapStep<E> extends MapStep<Element, Map<String, E>> {

    protected final String[] propertyKeys;
    protected final PropertyType returnType;
    protected final boolean includeTokens;

    public PropertyMapStep(final Traversal.Admin traversal, final boolean includeTokens, final PropertyType propertyType, final String... propertyKeys) {
        super(traversal);
        this.includeTokens = includeTokens;
        this.propertyKeys = propertyKeys;
        this.returnType = propertyType;

        if (this.returnType.forValues()) {
            this.setFunction(traverser -> {
                final Element element = traverser.get();
                final Map map = traverser.get() instanceof Vertex ?
                        (Map) ElementHelper.vertexPropertyValueMap((Vertex) element, propertyKeys) :
                        (Map) ElementHelper.propertyValueMap(element, propertyKeys);
                if (includeTokens) {
                    if (element instanceof VertexProperty) {
                        map.put(T.id, element.id());
                        map.put(T.key, ((VertexProperty) element).key());
                        map.put(T.value, ((VertexProperty) element).value());
                    } else {
                        map.put(T.id, element.id());
                        map.put(T.label, element.label());
                    }
                }
                return map;
            });
        } else {
            this.setFunction(traverser ->
                    traverser.get() instanceof Vertex ?
                            (Map) ElementHelper.vertexPropertyMap((Vertex) traverser.get(), propertyKeys) :
                            (Map) ElementHelper.propertyMap(traverser.get(), propertyKeys));
        }
    }

    public PropertyType getReturnType() {
        return this.returnType;
    }

    public String toString() {
        return TraversalHelper.makeStepString(this, Arrays.asList(this.propertyKeys), this.returnType.name().toLowerCase());
    }

    @Override
    public Set<TraverserRequirement> getRequirements() {
        return Collections.singleton(TraverserRequirement.OBJECT);
    }
}
