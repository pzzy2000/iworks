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
package org.uorm.dao.common;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.uorm.DataBaseType;
import org.uorm.dao.dialect.Dialect;
import org.uorm.dao.dialect.MySQLDialect;
import org.uorm.dao.transation.TransactionManager;
import org.uorm.orm.mapping.EntityMapCache;
import org.uorm.orm.mapping.IObjectReader;
import org.uorm.utils.Assert;
import org.uorm.utils.PropertyHolderUtil;

import com.alibaba.druid.pool.DruidPooledPreparedStatement;

import cn.oxo.iworks.databases.annotation.Column;

/**
 *
 * @author <a href="mailto:xunchangguo@gmail.com">郭训常</a>
 * @version 1.0.0 ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝<br/>
 *          修订日期 修订人 描述<br/>
 *          2012-1-18 郭训常 创建<br/>
 */
public class JdbcTemplate {
	private static final Logger logger = LogManager.getLogger(JdbcTemplate.class);

    private ConnectionFactory connectionFactory;
    private IObjectReader objectReader;
    private Dialect dialect = null;

    /** 默认自动管理事务 */
    protected boolean autoManagerTransaction = true;

    /**
     * 
     */
    public JdbcTemplate() {
        super();
        if ("javassist".equalsIgnoreCase(PropertyHolderUtil.getProperty("uorm.bytecode.provider"))) {
            this.objectReader = new org.uorm.orm.bytecode.javassist.JavassistObjectReader();
        } else {
            this.objectReader = new org.uorm.orm.mapping.ObjectReader();
        }

    }

    /**
     * @param connectionFactory
     */
    public JdbcTemplate(ConnectionFactory connectionFactory) {
        super();
        this.connectionFactory = connectionFactory;
        if ("jdk".equalsIgnoreCase(PropertyHolderUtil.getProperty("uorm.bytecode.provider"))) {
            this.objectReader = new org.uorm.orm.mapping.ObjectReader();
        } else {
            this.objectReader = new org.uorm.orm.bytecode.javassist.JavassistObjectReader();
        }

    }

    /**
     * @param connectionFactory
     * @param objectReader
     */
    public JdbcTemplate(ConnectionFactory connectionFactory, IObjectReader objectReader) {
        super();
        this.connectionFactory = connectionFactory;
        this.objectReader = objectReader;

    }

    public <T> T doExecute(ConnectionCallback<T> action) throws SQLException {
        Assert.notNull(action, "Callback object must not be null");
        Connection connection = TransactionManager.getConnection(connectionFactory);
        try {
            T result = action.doInConnection(connection);
            return result;
        } catch (SQLException ex) {
            throw ex;
        } catch (RuntimeException ex) {
            throw ex;
        } finally {
            TransactionManager.closeConnection(connection);
        }
    }

    protected <T> T doExecuteInTransation(ConnectionCallback<T> action) throws SQLException {
        Assert.notNull(action, "Callback object must not be null");
        TransactionManager.startManagedConnection(connectionFactory, null);
        Connection connection = TransactionManager.getConnection(connectionFactory);
        try {
            T result = action.doInConnection(connection);
            TransactionManager.commit();
            return result;
        } catch (SQLException ex) {
            TransactionManager.rollback();
            throw ex;
        } catch (RuntimeException ex) {
            TransactionManager.rollback();
            throw ex;
        } finally {
            TransactionManager.closeManagedConnection();
        }
    }

    public <T> T doExecute(StatementCallback<T> action) throws SQLException {
        Assert.notNull(action, "Callback object must not be null");
        Connection connection = TransactionManager.getConnection(connectionFactory);
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
            TransactionManager.closeConnection(connection);
        }
    }

    /**
     * 带参数的
     * 
     * @param <T>
     * @param action
     * @param sql
     * @param paramClass:
     *            参数@param params参考的ORM class，参数@param params中定义的优先,如参数@param params中的OrmClass都已经定义，则此参数可给null
     * @param params
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unused")
    protected <T> T doExecute(StatementCallback<T> action, String sql, final Class<?> paramClass,
        SqlParameter... params) throws SQLException {
        Assert.notNull(action, "Callback object must not be null");
        Assert.notNull(sql, "sql must not be null");
        Connection connection = TransactionManager.getConnection(connectionFactory);
        DruidPooledPreparedStatement stmt = null;
        try {
            if (logger.isDebugEnabled()) {
                if (params != null && params.length > 0) {
                    logger.debug("Executing SQL statement [" + sql + "] values: " + Arrays.asList(params));
                } else {
                    logger.debug("Executing SQL statement [" + sql + "]");
                }
            }

            stmt = (DruidPooledPreparedStatement)connection.prepareStatement(sql);
            EntityMapCache iEntityMapCache = this.objectReader.scan(paramClass);
            int idx = 1;
            for (SqlParameter param : params) {
                Field iField = iEntityMapCache.searchByColumnName(param.getName());
                // Column column = iField.getAnnotation(Column.class);
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
            TransactionManager.closeConnection(connection);
        }
    }

    protected <T> T doExecuteInTransation(StatementCallback<T> action) throws SQLException {
        Assert.notNull(action, "Callback object must not be null");
        TransactionManager.startManagedConnection(connectionFactory, null);
        Connection connection = TransactionManager.getConnection(connectionFactory);
        Statement stmt = null;
        try {
            stmt = connection.createStatement();
            T result = action.doInStatement(stmt);
            TransactionManager.commit();
            return result;
        } catch (SQLException ex) {
            TransactionManager.rollback();
            throw ex;
        } catch (RuntimeException ex) {
            TransactionManager.rollback();
            throw ex;
        } finally {
            JdbcUtils.closeStatement(stmt);
            stmt = null;
            TransactionManager.closeManagedConnection();
        }
    }

    /**
     * 事务、带参数的
     * 
     * @param <T>
     * @param action
     * @param sql
     * @param paramClass:
     *            参数@param params参考的ORM class，参数@param params中定义的优先,如参数@param params中的OrmClass都已经定义，则此参数可给null
     * @param params
     * @return
     * @throws SQLException
     */
    @SuppressWarnings("unused")
    protected <T> T doExecuteInTransation(StatementCallback<T> action, String sql, final Class<T> paramClass,
        SqlParameter... params) throws SQLException {
        Assert.notNull(action, "Callback object must not be null");
        Assert.notNull(sql, "sql must not be null");
        TransactionManager.startManagedConnection(connectionFactory, null);
        Connection connection = TransactionManager.getConnection(connectionFactory);
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
            EntityMapCache iEntityMapCache = this.objectReader.scan(paramClass);
            for (SqlParameter param : params) {

                Field iField = iEntityMapCache.searchByColumnName(param.getName());
                Column column = iField.getAnnotation(Column.class);
                Object val = param.getValue();

                stmt.setObject(idx, val);
                idx++;
            }
            T result = action.doInStatement(stmt);
            TransactionManager.commit();
            return result;
        } catch (SQLException ex) {
            TransactionManager.rollback();
            throw ex;
        } catch (Exception ex) {
            TransactionManager.rollback();
            throw new SQLException(ex);
        } finally {
            JdbcUtils.closeStatement(stmt);
            stmt = null;
            TransactionManager.closeManagedConnection();
        }
    }

    /**
     * query
     * 
     * @param <T>
     * @param sql
     * @param rse
     * @param params
     * @return
     * @throws SQLException
     */
    public <T> T query(final String sql, final ResultSetExtractor<T> rse, final SqlParameter... params)
        throws SQLException {
        Assert.notNull(sql, "SQL must not be null");
        Assert.notNull(rse, "ResultSetExtractor must not be null");
        class QueryStatementCallback implements StatementCallback<T> {

            /* (non-Javadoc)
             * @see org.uorm.dao.common.StatementCallback#doInStatement(java.sql.Statement)
             */
            @Override
            public T doInStatement(Statement stmt) throws SQLException {
                ResultSet rs = null;
                try {
                    if (logger.isDebugEnabled()) {
                        if (params != null && params.length > 0) {
                            logger.debug("Executing SQL query [" + sql + "] values: " + Arrays.asList(params));
                        } else {
                            logger.debug("Executing SQL query [" + sql + "]");
                        }
                    }
                    if (stmt instanceof PreparedStatement) {
                        rs = ((PreparedStatement)stmt).executeQuery();
                    } else {
                        rs = stmt.executeQuery(sql);
                    }
                    return rse.extractData(rs);
                } finally {
                    JdbcUtils.closeResultSet(rs);
                }
            }
        }
        if (params == null || params.length == 0) {
            return doExecuteInTransation(new QueryStatementCallback());
        } else {
            return doExecuteInTransation(new QueryStatementCallback(), sql, null, params);
        }
    }

    /**
     * query
     * 
     * @param <T>
     * @param sql
     * @param rse
     * @param paramClass:
     *            参数@param params参考的ORM class，参数@param params中定义的优先
     * @param params
     * @return
     * @throws SQLException
     */
    public <T> T query(final String sql, final ResultSetExtractor<T> rse, Class<?> paramClass,
        final SqlParameter... params) throws SQLException {
        if (params == null || params.length == 0) {
            return query(sql, rse);
        } else {
            Assert.notNull(sql, "SQL must not be null");
            Assert.notNull(rse, "ResultSetExtractor must not be null");
            class QueryStatementCallback implements StatementCallback<T> {

                /* (non-Javadoc)
                 * @see org.uorm.dao.common.StatementCallback#doInStatement(java.sql.Statement)
                 */
                @Override
                public T doInStatement(Statement stmt) throws SQLException {
                    ResultSet rs = null;
                    try {
                        // if (logger.isDebugEnabled()) {
                        // if(params != null && params.length >0){
                        // logger.info("？？？？？？？？？？？？ Executing SQL query [" + sql + "] values: " +
                        // Arrays.asList(params));
                        // }else{
                        // logger.info("？？？？？？？？？、、？Executing SQL query [" + sql + "]");
                        // }
                        // }
                        if (stmt instanceof PreparedStatement) {
                            rs = ((PreparedStatement)stmt).executeQuery();
                        } else {
                            rs = stmt.executeQuery(sql);
                        }
                        return rse.extractData(rs);
                    } finally {
                        JdbcUtils.closeResultSet(rs);
                    }
                }
            }
            return doExecute(new QueryStatementCallback(), sql, paramClass, params);
        }
    }

    /**
     * 查询返回单个对象
     * 
     * @param <T>
     * @param cls
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public <T> T queryForObject(Class<T> cls, String sql, final SqlParameter... params) throws SQLException {
        RowsResultSetExtractor<T> extractor = new RowsResultSetExtractor<T>(objectReader, cls);
        extractor.setMax(1);
        List<T> results = null;
        if (params == null || params.length == 0) {
            results = query(sql, extractor);
        } else {
            results = query(sql, extractor, cls, params);
        }
        if (results != null && results.size() > 0) {
            return results.get(0);
        }
        return null;
    }

    /**
     * @return the connectionFactory
     */
    public ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    /**
     * @param connectionFactory
     *            the connectionFactory to set
     */
    public void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    /**
     * @return the objectReader
     */
    public IObjectReader getObjectReader() {
        return objectReader;
    }

    /**
     * @param objectReader
     *            the objectReader to set
     */
    public void setObjectReader(IObjectReader objectReader) {
        this.objectReader = objectReader;
    }

    /**
     * @return the dialect
     */
    public Dialect getDialect() {
        if (dialect == null) {
            dialect = genDialect(this.connectionFactory.getConfiguration().getDatabasetype(),
                this.connectionFactory.getConfiguration().getDialectClass());
        }
        return dialect;
    }

    /**
     * 是否是oralce
     * 
     * @return
     */
    public boolean isOracle() {
        return DataBaseType.ORACLE == this.connectionFactory.getConfiguration().getDatabasetype();
    }

    /**
     * generate sql dialect
     * 
     * @param databasetype
     * @param dialectClass
     * @return
     */
    private Dialect genDialect(DataBaseType databasetype, String dialectClass) {
        Dialect dialect = null;
        if (dialectClass == null || dialectClass.length() == 0) {
            switch (databasetype) {

                case MYSQL:
                    dialect = new MySQLDialect();
                    break;

                default:
                    dialect = new MySQLDialect();
                    break;
            }
        } else {
            dialect = constructDialect(dialectClass);
        }
        return dialect;
    }

    /**
     * 初始化SQL方言类
     * 
     * @param typeClass
     * @return
     */
    private Dialect constructDialect(String typeClass) {
        Class<?> _class;
        try {
            _class = Class.forName(typeClass);
        } catch (ClassNotFoundException e) {
            logger.error("", e);
            return null;
        }
        try {
            Object insObject = _class.newInstance();
            if (insObject instanceof Dialect) {
                return (Dialect)insObject;
            }
        } catch (InstantiationException e) {
            logger.error("", e);
        } catch (IllegalAccessException e) {
            logger.error("", e);
        }
        return null;
    }

    public boolean isAutoManagerTransaction() {
        return autoManagerTransaction;
    }

    public void setAutoManagerTransaction(boolean autoManagerTransaction) {
        this.autoManagerTransaction = autoManagerTransaction;
    }
}
