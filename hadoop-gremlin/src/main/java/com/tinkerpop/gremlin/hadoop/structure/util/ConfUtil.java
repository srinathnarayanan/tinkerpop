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
package com.tinkerpop.gremlin.hadoop.structure.util;

import org.apache.commons.configuration.BaseConfiguration;
import org.apache.hadoop.conf.Configuration;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class ConfUtil {

    public static org.apache.commons.configuration.Configuration makeApacheConfiguration(final Configuration hadoopConfiguration) {
        final BaseConfiguration apacheConfiguration = new BaseConfiguration();
        hadoopConfiguration.iterator().forEachRemaining(e -> apacheConfiguration.setProperty(e.getKey(), e.getValue()));
        return apacheConfiguration;
    }

    public static Configuration makeHadoopConfiguration(final org.apache.commons.configuration.Configuration apacheConfiguration) {
        final Configuration hadoopConfiguration = new Configuration();
        apacheConfiguration.getKeys().forEachRemaining(key -> {
            final Object object = apacheConfiguration.getProperty(key);
            hadoopConfiguration.set(key, object.toString());
        });
        return hadoopConfiguration;
    }

    public static void mergeApacheIntoHadoopConfiguration(final org.apache.commons.configuration.Configuration apacheConfiguration, final Configuration hadoopConfiguration) {
        apacheConfiguration.getKeys().forEachRemaining(key -> {
            final Object object = apacheConfiguration.getProperty(key);
            hadoopConfiguration.set(key, object.toString());
        });
    }

    /*public static HadoopGraph getOutputGraph(final HadoopGraph hadoopGraph) {
        final BaseConfiguration newConfiguration = new BaseConfiguration();
        newConfiguration.copy(hadoopGraph.configuration());
        if (hadoopGraph.configuration().containsKey(Constants.GREMLIN_HADOOP_OUTPUT_LOCATION)) {
            newConfiguration.setProperty(Constants.GREMLIN_HADOOP_INPUT_LOCATION, hadoopGraph.configuration().getOutputLocation() + "/" + Constants.SYSTEM_G);
            newConfiguration.setProperty(Constants.GREMLIN_HADOOP_OUTPUT_LOCATION, hadoopGraph.configuration().getOutputLocation() + "_");
        }
        if (hadoopGraph.configuration().containsKey(Constants.GREMLIN_HADOOP_GRAPH_OUTPUT_FORMAT)) {
            newConfiguration.setProperty(Constants.GREMLIN_HADOOP_GRAPH_INPUT_FORMAT, hadoopGraph.configuration().getString(Constants.GREMLIN_HADOOP_GRAPH_OUTPUT_FORMAT).replace("OutputFormat", "InputFormat"));
        }
        return HadoopGraph.open(newConfiguration);
    }*/
}
