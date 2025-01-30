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
package com.singlestore.gremlin;

import java.util.Iterator;

import org.apache.commons.configuration2.Configuration;
import org.apache.tinkerpop.gremlin.process.computer.GraphComputer;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Graph;
import org.apache.tinkerpop.gremlin.structure.Transaction;
import org.apache.tinkerpop.gremlin.structure.Vertex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleStoreGremlinGraph implements Graph {

    private static final Logger logger = LoggerFactory.getLogger(SingleStoreGremlinGraph.class);
    private final Configuration configuration;

    public SingleStoreGremlinGraph() {
        configuration = null;
        logger.info("SingleStoreGremlinGraph initialized.");
    }

    public static SingleStoreGremlinGraph open(Configuration conf) {
        return new SingleStoreGremlinGraph(conf);
    }

    private SingleStoreGremlinGraph(Configuration conf) {
        this.configuration = conf;
        System.out.println("SingleStoreGremlinGraph initialized with configuration: " + conf);
    }

    @Override
    public void close() {
        System.out.println("SingleStoreGremlinGraph closed.");
    }

    @Override
    public GraphTraversalSource traversal() {
        return new SingleStoreGremlinTraversalSource(this);
    }

    @Override
    public Vertex addVertex(Object... keyValues) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addVertex'");
    }

    @Override
    public <C extends GraphComputer> C compute(Class<C> graphComputerClass) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compute'");
    }

    @Override
    public GraphComputer compute() throws IllegalArgumentException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'compute'");
    }

    @Override
    public Iterator<Vertex> vertices(Object... vertexIds) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'vertices'");
    }

    @Override
    public Iterator<Edge> edges(Object... edgeIds) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'edges'");
    }

    @Override
    public Transaction tx() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'tx'");
    }

    @Override
    public Variables variables() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'variables'");
    }

    @Override
    public Configuration configuration() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'configuration'");
    }
}
