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
package org.uorm.dao.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uorm.dao.transation.TransactionManager;
import org.uorm.orm.mapping.EntityMapCache;
import org.uorm.orm.mapping.IObjectReader;
import org.uorm.utils.Assert;

import cn.oxo.iworks.databases.annotation.Column;
import cn.oxo.iworks.databases.annotation.Table;

/**
 *
 * @author <a href="mailto:xunchangguo@gmail.com">郭训常</a>
 * @version 1.0.0 ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝<br/>
 *          修订日期 修订人 描述<br/>
 *          2012-1-18 郭训常 创建<br/>
 */
public class CommonDaoImpl extends JdbcTemplate implements ICommonDao {
	private static final Logger logger = LoggerFactory.getLogger(CommonDaoImpl.class);
	public static final int _BATCH_SIZE = 50;
	private int batchSize = _BATCH_SIZE;

	public CommonDaoImpl() {
		super();
	}

	/**
	 * @param connectionFactory
	 */
	public CommonDaoImpl(ConnectionFactory connectionFactory) {
		super(connectionFactory);
	}

	/**
	 * @param connectionFactory
	 * @param objectReader
	 */
	public CommonDaoImpl(ConnectionFactory connectionFactory, IObjectReader objectReader) {
		super(connectionFactory, objectReader);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.uorm.dao.common.ICommonDao#beginTransation()
	 */
	@Override
	public void beginTransation() throws SQLException {
		TransactionManager.startManagedConnection(getConnectionFactory(), null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.uorm.dao.common.ICommonDao#commitTransation()
	 */
	@Override
	public void commitTransation() throws SQLException {
		try {
			TransactionManager.commit();
		} finally {
			TransactionManager.closeManagedConnection();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.uorm.dao.common.ICommonDao#rollbackTransation()
	 */
	@Override
	public void rollbackTransation() throws SQLException {
		try {
			TransactionManager.rollback();
		} finally {
			TransactionManager.closeManagedConnection();
		}
	}
	
	private <T>  EntityMapCache xx(Class<T> cls) throws SQLException  {
		
		EntityMapCache iEntityMapCache = getObjectReader().scan(cls);
		return iEntityMapCache;
	
	}

	@Override
	public <T> T queryBusiness(Class<T> cls, String fields, Serializable vals) throws SQLException {
		try {
			Assert.isLengthEq(fields, vals);
			EntityMapCache iEntityMapCache =  xx(cls);
			Table table = iEntityMapCache.getTable();
			String tableName = table.name();
			StringBuffer sql = new StringBuffer("SELECT * FROM " + tableName + " WHERE ");
			SqlParameter[] params = new SqlParameter[1];

			int idx = 0;
			// for (int i = 0; i < fields.length; i++)
			{
				String field = fields;
				List<Field> iFieldIds = iEntityMapCache.getId();
				for (Field iFieldId : iFieldIds) {
					if (iFieldId.getName().equals(field)) {
						Column column = iFieldId.getAnnotation(Column.class);
						params[idx] = new SqlParameter(column.name(), vals);
						if (idx == 0) {
							sql.append(column.name()).append(" = ?");
						} else {
							sql.append(" and ").append(column.name()).append(" = ?");
						}
					}
				}

				List<Field> fieldsList = iEntityMapCache.getFields();
				for (Field iField : fieldsList) {
					if (iField.getName().equals(field)) {
						Column column = iField.getAnnotation(Column.class);
						params[idx] = new SqlParameter(column.name(), vals);
						if (idx == 0) {
							sql.append(column.name()).append(" = ?");
						} else {
							sql.append(" and ").append(column.name()).append(" = ?");
						}
					}
				}

				idx++;
			}
			return queryForObject(cls, sql.toString(), params);
		} catch (Exception e) {
			throw new SQLException(e);
		}
	}

	/**
	 * @return the batchSize
	 */
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * @param batchSize
	 *            the batchSize to set
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

}
