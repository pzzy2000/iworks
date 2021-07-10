/// **
// * Copyright 2010-2016 the original author or authors.
// *
// * Licensed to the Apache Software Foundation (ASF) under one or more
// * contributor license agreements. See the NOTICE file distributed with
// * this work for additional information regarding copyright ownership.
// * The ASF licenses this file to You under the Apache License, Version 2.0
// * (the "License"); you may not use this file except in compliance with
// * the License. You may obtain a copy of the License at
// *
// * http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
// package org.uorm.dao.common;
//
// import java.beans.PropertyVetoException;
// import java.sql.SQLException;
// import java.util.Map;
//
// import javax.sql.DataSource;
//
// import org.uorm.utils.Utils;
//
/// **
// *
// * @author <a href="mailto:xunchangguo@gmail.com">郭训常</a>
// * @version 1.0.0
// * ＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝＝<br/>
// * 修订日期 修订人 描述<br/>
// * 2012-1-18 郭训常 创建<br/>
// */
// public class DataSourceCreator {
//// private static Set<String> driverloaded = new HashSet<String>();
//
// public static DataSource createDatasource(String pooltype, DatasourceConfig datasourcecfg) throws SQLException {
// return genPoolDataSource(pooltype, datasourcecfg);
// }
//
// /**
// * 生成连接池的Datasource
// * @param pooltype
// * @param datasourcecfg
// * @return
// * @throws SQLException
// */
// private static DataSource genPoolDataSource(String pooltype, DatasourceConfig datasourcecfg) throws SQLException {
// DataSource datasource = null;
// if ( DefaultConnectionFactory._POOL_TYPE_C3P0.equalsIgnoreCase(pooltype) ) {
// datasource = createC3p0DataSource(datasourcecfg);
// } else if ( DefaultConnectionFactory._POOL_TYPE_BONECP.equalsIgnoreCase(pooltype) ) {
// datasource = createBoneCPDataSource(datasourcecfg);
// } else if ( DefaultConnectionFactory._POOL_TYPE_DBCP.equalsIgnoreCase(pooltype) ) {
// datasource = createDBCPDataSource(datasourcecfg);
// }else if ( DefaultConnectionFactory._POOL_TYPE_JDBC_POOL.equalsIgnoreCase(pooltype) ) {
// Boolean XA = Utils.str2Boolean(datasourcecfg.getPoolPerperties().get("isXADataSource"));
// if(XA != null){
// datasource = createJdbcPoolDataSource(datasourcecfg, XA);
// }else{
// datasource = createJdbcPoolDataSource(datasourcecfg, false);
// }
// }
// return datasource;
// }
//
//
// }
