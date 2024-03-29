/**
 * Copyright 2010-2016 the original author or authors.
 * 
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.uorm.dao.common;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uorm.dao.transation.OneThreadMultiConnectionTransactionManager;
import org.uorm.orm.mapping.EntityMapCache;
import org.uorm.orm.mapping.IObjectReader;
import org.uorm.utils.Assert;

import cn.oxo.iworks.databases.annotation.Column;

/**
 * 同一个线程同一时刻可以有多个连接共存
 * 
 * @author <a href="mailto:xunchangguo@gmail.com">郭训长</a>
 * @version 1.0.0 ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝<br/>
 *          修订日期 修订人 描述<br/>
 *          2013-1-15 郭训长 创建<br/>
 */
public class OneThreadMultiConnectionCommonDaoImpl extends CommonDaoImpl {
      private static final Logger logger = LoggerFactory.getLogger(OneThreadMultiConnectionCommonDaoImpl.class);

      /**
      	 * 
      	 */
      public OneThreadMultiConnectionCommonDaoImpl() {
            super();
      }

      /**
       * @param connectionFactory
       * @param objectReader
       */
      public OneThreadMultiConnectionCommonDaoImpl(ConnectionFactory connectionFactory, IObjectReader objectReader) {
            super(connectionFactory, objectReader);
      }

      /**
       * @param connectionFactory
       */
      public OneThreadMultiConnectionCommonDaoImpl(ConnectionFactory connectionFactory) {
            super(connectionFactory);
      }

      /*
       * (non-Javadoc)
       * @see org.uorm.dao.common.CommonDaoImpl#beginTransation()
       */
      @Override
      public void beginTransation() throws SQLException {
            OneThreadMultiConnectionTransactionManager.startManagedConnection(getConnectionFactory(), null);
      }

      /*
       * (non-Javadoc)
       * @see org.uorm.dao.common.CommonDaoImpl#commitTransation()
       */
      @Override
      public void commitTransation() throws SQLException {
            try {
                  OneThreadMultiConnectionTransactionManager.commit(getConnectionFactory());
            } finally {
                  OneThreadMultiConnectionTransactionManager.closeManagedConnection(getConnectionFactory());
            }
      }

      /*
       * (non-Javadoc)
       * @see org.uorm.dao.common.CommonDaoImpl#rollbackTransation()
       */
      @Override
      public void rollbackTransation() throws SQLException {
            try {
                  OneThreadMultiConnectionTransactionManager.rollback(getConnectionFactory());
            } finally {
                  OneThreadMultiConnectionTransactionManager.closeManagedConnection(getConnectionFactory());
            }
      }

      @Override
      public <T> T doExecute(ConnectionCallback<T> action) throws SQLException {
            Assert.notNull(action, "Callback object must not be null");
            Connection connection = OneThreadMultiConnectionTransactionManager.getConnection(getConnectionFactory());
            try {
                  T result = action.doInConnection(connection);
                  return result;
            } catch (SQLException ex) {
                  throw ex;
            } catch (RuntimeException ex) {
                  throw ex;
            } finally {
                  OneThreadMultiConnectionTransactionManager.closeConnection(connection, getConnectionFactory());
            }
      }

      @Override
      protected <T> T doExecuteInTransation(ConnectionCallback<T> action) throws SQLException {
            Assert.notNull(action, "Callback object must not be null");
            OneThreadMultiConnectionTransactionManager.startManagedConnection(getConnectionFactory(), null);
            Connection connection = OneThreadMultiConnectionTransactionManager.getConnection(getConnectionFactory());
            try {
                  T result = action.doInConnection(connection);
                  OneThreadMultiConnectionTransactionManager.commit(getConnectionFactory());
                  return result;
            } catch (SQLException ex) {
                  OneThreadMultiConnectionTransactionManager.rollback(getConnectionFactory());
                  throw ex;
            } catch (RuntimeException ex) {
                  OneThreadMultiConnectionTransactionManager.rollback(getConnectionFactory());
                  throw ex;
            } finally {
                  OneThreadMultiConnectionTransactionManager.closeManagedConnection(getConnectionFactory());
            }
      }

      @Override
      public <T> T doExecute(StatementCallback<T> action) throws SQLException {
            Assert.notNull(action, "Callback object must not be null");
            Connection connection = OneThreadMultiConnectionTransactionManager.getConnection(getConnectionFactory());
            Statement stmt = null;
            try {
                  stmt = connection.createStatement();
                  T result = action.doInStatement(stmt);
                  return result;
            } catch (SQLException ex) {
                  throw ex;
            } catch (RuntimeException ex) {
                  throw ex;
            } finally {
                  JdbcUtils.closeStatement(stmt);
                  stmt = null;
                  OneThreadMultiConnectionTransactionManager.closeConnection(connection, getConnectionFactory());
            }
      }

      @Override
      protected <T> T doExecute(StatementCallback<T> action, String sql, final Class<?> paramClass, SqlParameter... params) throws SQLException {
            Assert.notNull(action, "Callback object must not be null");
            Assert.notNull(sql, "sql must not be null");
            Connection connection = OneThreadMultiConnectionTransactionManager.getConnection(getConnectionFactory());
            PreparedStatement stmt = null;
            try {
                  // if (logger.isDebugEnabled()) {
                  // if(params != null && params.length >0){
                  // logger.info("？？？？？？？？？？？？ Executing SQL query [" + sql + "]
                  // values: " + Arrays.asList(params));
                  // }else{
                  // logger.info("？？？？？？？？？、、？Executing SQL query [" + sql +
                  // "]");
                  // }
                  // }
                  stmt = connection.prepareStatement(sql);
                  int idx = 1;
                  EntityMapCache iEntityMapCache = getObjectReader().scan(paramClass);
                  for (SqlParameter param : params) {

                        Field iField = iEntityMapCache.searchByColumnName(param.getName());
                        Column column = iField.getAnnotation(Column.class);
                        Object val = param.getValue();

                        stmt.setObject(idx, val);
                        idx++;
                  }
                  T result = action.doInStatement(stmt);
                  return result;
            } catch (SQLException ex) {
                  throw ex;
            } catch (Exception e) {
                  throw new SQLException(e);
            } finally {
                  JdbcUtils.closeStatement(stmt);
                  stmt = null;
                  OneThreadMultiConnectionTransactionManager.closeConnection(connection, getConnectionFactory());
            }
      }

      @Override
      protected <T> T doExecuteInTransation(StatementCallback<T> action) throws SQLException {
            Assert.notNull(action, "Callback object must not be null");
            OneThreadMultiConnectionTransactionManager.startManagedConnection(getConnectionFactory(), null);
            Connection connection = OneThreadMultiConnectionTransactionManager.getConnection(getConnectionFactory());
            Statement stmt = null;
            try {
                  stmt = connection.createStatement();
                  T result = action.doInStatement(stmt);
                  OneThreadMultiConnectionTransactionManager.commit(getConnectionFactory());
                  return result;
            } catch (SQLException ex) {
                  OneThreadMultiConnectionTransactionManager.rollback(getConnectionFactory());
                  throw ex;
            } catch (RuntimeException ex) {
                  OneThreadMultiConnectionTransactionManager.rollback(getConnectionFactory());
                  throw ex;
            } finally {
                  JdbcUtils.closeStatement(stmt);
                  stmt = null;
                  OneThreadMultiConnectionTransactionManager.closeManagedConnection(getConnectionFactory());
            }
      }

      @Override
      protected <T> T doExecuteInTransation(StatementCallback<T> action, String sql, final Class<T> paramClass, SqlParameter... params) throws SQLException {
            Assert.notNull(action, "Callback object must not be null");
            Assert.notNull(sql, "sql must not be null");
            OneThreadMultiConnectionTransactionManager.startManagedConnection(getConnectionFactory(), null);
            Connection connection = OneThreadMultiConnectionTransactionManager.getConnection(getConnectionFactory());
            PreparedStatement stmt = null;
            try {
                  if (logger.isDebugEnabled()) {
                        if (params != null && params.length > 0) {
                              logger.debug("Executing SQL statement [" + sql + "] values: " + Arrays.asList(params));
                        } else {
                              logger.debug("Executing SQL statement [" + sql + "]");
                        }
                  }
                  stmt = connection.prepareStatement(sql);
                  int idx = 1;
                  EntityMapCache iEntityMapCache = getObjectReader().scan(paramClass);
                  for (SqlParameter param : params) {

                        Field iField = iEntityMapCache.searchByColumnName(param.getName());
                        Column column = iField.getAnnotation(Column.class);
                        Object val = param.getValue();

                        stmt.setObject(idx, val);
                        idx++;
                  }
                  T result = action.doInStatement(stmt);
                  OneThreadMultiConnectionTransactionManager.commit(getConnectionFactory());
                  return result;
            } catch (SQLException ex) {
                  OneThreadMultiConnectionTransactionManager.rollback(getConnectionFactory());
                  throw ex;
            } catch (Exception ex) {
                  OneThreadMultiConnectionTransactionManager.rollback(getConnectionFactory());
                  throw new SQLException(ex);
            } finally {
                  JdbcUtils.closeStatement(stmt);
                  stmt = null;
                  OneThreadMultiConnectionTransactionManager.closeManagedConnection(getConnectionFactory());
            }
      }

}
