/**
 * Copyright 2010-2016 the original author or authors.
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.uorm.orm.bytecode.javassist;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.uorm.orm.bytecode.FastClass;
import org.uorm.orm.mapping.EntityMapCache;
import org.uorm.orm.mapping.ObjectMappingCache;
import org.uorm.orm.mapping.ObjectReader;

/**
 *
 * @author <a href="mailto:xunchangguo@gmail.com">郭训长</a>
 * @version 1.0.0 ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝<br/>
 *          修订日期 修订人 描述<br/>
 *          2013-9-12 郭训长 创建<br/>
 */
public class JavassistObjectReader extends ObjectReader {

    public <T> T read(Class<T> cls, ResultSet result, ResultSetMetaData rsmd) throws Exception {

	EntityMapCache iEntityMapCache = ObjectMappingCache.getInstance().scan(cls);

	int count = rsmd.getColumnCount();

	FastClass fastClass = org.uorm.orm.bytecode.FastClass.create(cls);

	Object instance = fastClass.newInstance();

	for (int i = 1; i <= count; i++) {
	    String columnName = rsmd.getColumnLabel(i);// rsmd.getColumnName(i);
	    if (null == columnName || 0 == columnName.length()) {
		columnName = rsmd.getColumnName(i);
	    }
	    Field iField = iEntityMapCache.searchByColumnName(columnName);
	    if (iField == null)
		continue;
	    Object xx=null;
	    if(iField.getType().equals(Integer.class)) {
	    	 xx= result.getInt(i);
	    }else {
	    	 xx= result.getObject(i);
	    }
	    fastClass.invoke(instance, iField, xx);
	}
	return cls.cast(instance);

    }

}
