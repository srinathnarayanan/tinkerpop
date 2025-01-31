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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.DefaultGraphTraversal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleStoreGraphTraversal<S, E> extends DefaultGraphTraversal<S, E> {

    private static final Logger logger = LoggerFactory.getLogger(SingleStoreGraphTraversal.class);

    private final String table;
    private final List<String> filters = new ArrayList<>();
    private final List<String> selectFields = new ArrayList<>();
    private Iterator<E> iterator; // To store query results and iterate over them

    public SingleStoreGraphTraversal(String table, Object... ids) {
        this.table = table;
        this.iterator = Collections.emptyIterator();
    }

    @Override
    public SingleStoreGraphTraversal<S, E> has(String key, Object value) {
        filters.add(key + " = '" + value + "'");
        return this;
    }

    @Override
    public SingleStoreGraphTraversal values(String... propertyNames) {
        // This will select specific properties for the SQL query
        for (String propertyName : propertyNames) {
            selectFields.add(propertyName);
        }
        return this;
    }

    @Override
    public List<E> toList() {
        executeQuery(); // Populate iterator
        List<E> results = new ArrayList<>();
        iterator.forEachRemaining(results::add);
        return results;
    }

    @Override
    public E next() {
        if (!iterator.hasNext()) {
            executeQuery();
        }
        if (!iterator.hasNext()) {
            throw new NoSuchElementException("No more elements in traversal.");
        }
        return iterator.next();
    }

    @Override
    public SingleStoreGraphTraversal<S, E> iterate() {
        executeQuery();
        return this;
    }

    private void executeQuery() {
        List<E> results = new ArrayList<>();
        String sqlQuery = buildSqlQuery(); // Build the final SQL query
        logger.info("sql query being executed: " + sqlQuery);

        try (Statement stmt = SingleStoreGremlinTraversalSource.connection.createStatement()) {
            if (sqlQuery.trim().toLowerCase().startsWith("select")) {
                try (ResultSet rs = stmt.executeQuery(sqlQuery)) {
                    while (rs.next()) {
                        results.add((E) constructGraphElement(rs)); // Convert row to graph element
                    }
                }
            } else {
                stmt.executeUpdate(sqlQuery); // Execute update for non-SELECT queries
            }
        } catch (SQLException e) {
            throw new RuntimeException("SQL Execution Failed: " + sqlQuery, e);
        }
        this.iterator = results.iterator(); // Store the result iterator
    }

    private String buildSqlQuery() {
        StringBuilder queryBuilder = new StringBuilder();

        // Build the SELECT part
        queryBuilder.append("SELECT ");
        if (selectFields.isEmpty()) {
            queryBuilder.append("*"); // Default to all fields
        } else {
            queryBuilder.append(String.join(", ", selectFields));
        }

        // FROM clause with table name
        queryBuilder.append(" FROM ").append(table);

        // Build the WHERE part (filters)
        if (!filters.isEmpty()) {
            queryBuilder.append(" WHERE ");
            queryBuilder.append(String.join(" AND ", filters));
        }

        return queryBuilder.toString();
    }

    private Object constructGraphElement(ResultSet rs) throws SQLException {
        Map<String, Object> element = new HashMap<>();
        ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            element.put(metaData.getColumnName(i), rs.getObject(i));
        }
        return element;
    }
}
