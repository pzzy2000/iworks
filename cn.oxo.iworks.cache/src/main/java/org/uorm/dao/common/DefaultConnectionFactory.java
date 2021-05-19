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

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.uorm.dao.common.pool.IDataSourceCreator;

/**
 *
 * @author <a href="mailto:xunchangguo@gmail.com">郭训常</a>
 * @version 1.0.0 ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝<br/>
 *          修订日期 修订人 描述<br/>
 *          2012-1-21 郭训常 创建<br/>
 */
public class DefaultConnectionFactory implements ConnectionFactory {

      public static final String _POOL_TYPE_DRUID = "druid";
      // private static final Log logger =
      // LogFactory.getLog(DefaultConnectionFactory.class);

      private DatasourceConfig config = null;
      private DataSource dataSource = null;

      /**
      	 * 
      	 */
      public DefaultConnectionFactory() {
            super();
      }

      /**
       * @param dataSource
       */
      public DefaultConnectionFactory(DataSource dataSource) {
            super();
            this.dataSource = dataSource;
      }

      /**
       * @param config
       */
      public DefaultConnectionFactory(DatasourceConfig config) {
            super();
            this.config = config;
      }

      /**
       * @param config
       * @param dataSource
       */
      public DefaultConnectionFactory(DatasourceConfig config, DataSource dataSource) {
            super();
            this.config = config;
            this.dataSource = dataSource;
      }

      /*
       * (non-Javadoc)
       * @see org.uorm.dao.common.ConnectionFactory#openConnection()
       */
      @Override
      public Connection openConnection() throws SQLException {
            // System.out.println("open connection");
            // if (logger.isDebugEnabled()) {
            // logger.debug("open connection.");
            // }
            Connection con = null;
            if (this.dataSource != null) {
                  con = this.dataSource.getConnection();
            } else {
                  IDataSourceCreator creator = getDataSourceCreator();
                  this.dataSource = creator.createDatasource(config);
                  con = this.dataSource.getConnection();
            }

            return con;
      }

      private IDataSourceCreator getDataSourceCreator() {
            IDataSourceCreator creator = new org.uorm.dao.common.pool.DruidDataSourceCreator();
            return creator;
      }

      /*
       * (non-Javadoc)
       * @see org.uorm.dao.common.ConnectionFactory#getConfiguration()
       */
      @Override
      public DatasourceConfig getConfiguration() {
            return this.config;
      }

      /**
       * @return the dataSource
       */
      public DataSource getDataSource() {
            return dataSource;
      }

      /**
       * @param dataSource
       *              the dataSource to set
       */
      public void setDataSource(DataSource dataSource) {
            this.dataSource = dataSource;
      }

      /**
       * @param config
       *              the config to set
       */
      public void setConfig(DatasourceConfig config) {
            this.config = config;
      }

      /*
       * (non-Javadoc)
       * @see java.lang.Object#toString()
       */
      @Override
      public String toString() {
            if (this.config != null) {
                  return String.format("[%s , %s]", this.config.getDriverClass(), this.config.getJdbcUrl());
            }
            return super.toString();
      }

      /*
       * (non-Javadoc)
       * @see java.lang.Object#finalize()
       */
      @Override
      protected void finalize() throws Throwable {
            if (this.dataSource != null) {
                  try {
                        ((com.alibaba.druid.pool.DruidDataSource) this.dataSource).close();
                  } catch (Exception e) { // $codepro.audit.disable
                                          // emptyCatchClause
                        e.printStackTrace();
                  }
                  this.dataSource = null;
            }
            super.finalize();
      }

}
