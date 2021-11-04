/**
 * Copyright 2010-2016 the original author or authors.
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements. See the NOTICE
 * file distributed with this work for additional information regarding copyright ownership. The ASF licenses this file
 * to You under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.uorm.orm.mapping;

import java.lang.reflect.Field;
import java.sql.JDBCType;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.oxo.iworks.databases.annotation.Column;
import cn.oxo.iworks.databases.annotation.Id;
import cn.oxo.iworks.databases.annotation.Table;

/**
 *
 * @author <a href="mailto:xunchangguo@gmail.com">郭训常</a>
 * @version 1.0.0 ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝<br/>
 *          修订日期 修订人 描述<br/>
 *          2012-1-18 郭训常 创建<br/>
 */
public class ObjectMappingCache {

    private Logger logger = LogManager.getLogger(ObjectMappingCache.class);

    private static ObjectMappingCache _instance = new ObjectMappingCache();

    private Map<Class<?>, EntityMapCache> entityMapCacheMap = new ConcurrentHashMap<Class<?>, EntityMapCache>();

    public static ObjectMappingCache getInstance() {
        return _instance;
    }

    private void validate(Class<?> cls, Field field) {
        Column column = field.getAnnotation(Column.class);

        if (column.columnType().getJdbcType().equals(JDBCType.BINARY)
            || column.columnType().getJdbcType().equals(JDBCType.VARBINARY)
            || column.columnType().getJdbcType().equals(JDBCType.LONGVARBINARY)) {
            // throw new RuntimeException(
            // "this JDBCType : JDBCType.BINARY JDBCType.VARBINARY JDBCType.LONGVARBINARY no
            // support ! ");

            throw new RuntimeException("class " + cls.getName() + "  field : " + field.getName() + " type : "
                + field.getType() + "  no equals  jdbc type " + column.columnType().getJavaType()
                + " this field type : " + column.columnType().getJavaType());

        } else {

            if (field.getType().isEnum() && column.columnType().getJavaType().equals(String.class)) {

            } else if (field.getType().equals(Date.class)
                && column.columnType().getJavaType().equals(Timestamp.class)) {

            } else {
                if (!column.columnType().getJavaType().equals(field.getType())) {
                    throw new RuntimeException("class " + cls.getName() + "  field : " + field.getName() + " type : "
                        + field.getType() + "  no equals  jdbc type " + column.columnType().getJavaType()
                        + " this field type : " + column.columnType().getJavaType());
                }
            }

        }

    }

    // 扫描
    public EntityMapCache scan(Class<?> cls) throws SQLException {

        // logger.info("entity cache sacn class : " + cls.getName());

        EntityMapCache iEntityMapCache = entityMapCacheMap.get(cls);

        if (iEntityMapCache == null) {
            synchronized (cls) {
                iEntityMapCache = entityMapCacheMap.get(cls);

                if (iEntityMapCache == null) {
                    iEntityMapCache = new EntityMapCache();
                    entityMapCacheMap.put(cls, iEntityMapCache);
                    Table table = cls.getAnnotation(Table.class);
                    // logger.info("entity cache sacn class : " + cls.getName() + " table " + table);
                    iEntityMapCache.setTable(table);
                    Field[] flds = FieldUtils.getAllFields(cls);
                    for (Field fld : flds) {
                        if (fld.isAnnotationPresent(Column.class)) {
                            try {
                                validate(cls, fld);
                            } catch (Exception e) {
                                entityMapCacheMap.remove(cls);
                                throw new SQLException(e);
                            }
                            if (fld.isAnnotationPresent(Id.class)) {
                                iEntityMapCache.getId().add(fld);
                            } else {
                                iEntityMapCache.getFields().add(fld);
                            }
                        }
                    }
                    iEntityMapCache.init();
                }
            }
        }
        return iEntityMapCache;

    }

    public Table getTable(Class<?> cls) {
        Table table = entityMapCacheMap.get(cls).getTable();
        return table;
    }

}
