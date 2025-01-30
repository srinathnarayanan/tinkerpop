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

import org.apache.tinkerpop.gremlin.jsr223.AbstractGremlinPlugin;
import org.apache.tinkerpop.gremlin.jsr223.DefaultImportCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleStoreGremlinPlugin extends AbstractGremlinPlugin {

    private static final Logger logger = LoggerFactory.getLogger(SingleStoreGremlinPlugin.class);
    private static final SingleStoreGremlinPlugin INSTANCE = new SingleStoreGremlinPlugin();

    public static final String NAME = "com.singlestore.gremlin";

    private static final DefaultImportCustomizer imports = DefaultImportCustomizer.build()
            .addClassImports(SingleStoreGremlinGraph.class,
                    SingleStoreGremlinTraversalSource.class).create();

    public SingleStoreGremlinPlugin() {
        super(NAME, imports);
        logger.info("Singlestore Plugin created");
    }

    public static SingleStoreGremlinPlugin instance() {
        return INSTANCE;
    }

}
