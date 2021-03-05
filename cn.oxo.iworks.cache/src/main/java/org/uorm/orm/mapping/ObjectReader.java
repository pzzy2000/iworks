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
package org.uorm.orm.mapping;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;

import org.uorm.orm.bytecode.FastClass;

/**
 *
 * @author <a href="mailto:xunchangguo@gmail.com">郭训常</a>
 * @version 1.0.0
 * ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝<br/>
 * 修订日期                 修订人            描述<br/>
 * 2012-1-18       郭训常            创建<br/>
 */
public class ObjectReader implements IObjectReader {
    
    public <T> EntityMapCache  scan(Class<T> cls) throws SQLException {
	return ObjectMappingCache.getInstance().scan(cls);
    }

	/* (non-Javadoc)
	 * @see jetsennet.sqlclient.orm.mapping.IObjectReader#read(java.lang.Class, java.sql.ResultSet, java.sql.ResultSetMetaData)
	 */
//	@SuppressWarnings("unchecked")
	public <T> T read(Class<T> cls, ResultSet result,ResultSetMetaData rsmd) throws Exception {


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
		    result.getObject(i);
		    fastClass.invoke(instance, iField, result.getObject(i));
		}
		return cls.cast(instance);

	    
	    
	}
	
	protected static String capitalize(String s) {
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}
	
	protected byte[] clob2bytes(Clob clob) throws SQLException {
		if(clob != null){
			String date = clob.getSubString(1, (int) clob.length());
			return date.getBytes();
		}
		return null;
	}
	
	protected String clob2String(Clob clob) throws SQLException {
		if(clob != null){
			String date = clob.getSubString(1, (int) clob.length());
			return date;
		}
		return null;
	}
	
	protected byte[] blob2bytes(Blob blob) throws SQLException {
		if(blob != null){
			byte[] data = blob.getBytes(1, (int) blob.length());
			return data;
		}
		return null;
	}
	
	protected Object getValue(ResultSet result, int columnIndex, Class<?> memberType) throws SQLException {
		if(Array.class.equals(memberType))         return result.getArray(columnIndex);
        if(AsciiStream  .class.equals(memberType)) return result.getAsciiStream(columnIndex);
        
        if((Byte[]       .class.equals(memberType)) || (byte[].class.equals(memberType)))   return result.getBytes(columnIndex);
        if((Boolean      .class.equals(memberType)) || (boolean.class.equals(memberType)))  return result.getBoolean(columnIndex);
        if((Byte         .class.equals(memberType)) || (byte.class.equals(memberType)))     return result.getByte(columnIndex);
        if((Double       .class.equals(memberType)) || (double.class.equals(memberType)))   return result.getDouble(columnIndex);
        if((Float        .class.equals(memberType)) || (float.class.equals(memberType)))    return result.getFloat(columnIndex);
        if((Integer      .class.equals(memberType)) || (int.class.equals(memberType)))      return result.getInt(columnIndex);
        if((Long         .class.equals(memberType)) || (long.class.equals(memberType)))     return result.getLong(columnIndex);
        if((Short        .class.equals(memberType)) || (short.class.equals(memberType)))    return result.getShort(columnIndex);

        if(BigInteger   .class.equals(memberType)) {
        	BigDecimal val = result.getBigDecimal(columnIndex);
        	if(val != null){
        		return val.toBigInteger();
        	}else{
        		return null;
        	}
        }
        if(BigDecimal   .class.equals(memberType)) return result.getBigDecimal(columnIndex);
        if(InputStream  .class.equals(memberType)) return result.getBinaryStream(columnIndex);
        if(Blob         .class.equals(memberType)) return result.getBlob(columnIndex);
        if(Reader       .class.equals(memberType)) return result.getCharacterStream(columnIndex);
        if(Clob         .class.equals(memberType)) return result.getClob(columnIndex);
        if(java.sql.Date.class.equals(memberType)) return result.getDate(columnIndex);
        if(java.util.Date.class.equals(memberType)) return result.getTimestamp(columnIndex);
        if(Ref          .class.equals(memberType)) return result.getRef(columnIndex);
        if(String       .class.equals(memberType)) return result.getString(columnIndex);
        if(Time         .class.equals(memberType)) return result.getTime(columnIndex);
        if(Timestamp    .class.equals(memberType)) return result.getTimestamp(columnIndex);
        if(URL          .class.equals(memberType)) return result.getURL(columnIndex);
        if(Object       .class.equals(memberType)) return result.getObject(columnIndex);
        if(SQLXML       .class.equals(memberType)) return result.getSQLXML(columnIndex);
        return result.getObject(columnIndex);
	}


	

	

	
}
