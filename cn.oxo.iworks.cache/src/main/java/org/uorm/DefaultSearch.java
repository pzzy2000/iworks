package org.uorm;

import java.io.Serializable;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.uorm.dao.common.CommonDaoImpl;
import org.uorm.dao.common.ConnectionFactory;
import org.uorm.dao.common.DefaultConnectionFactory;
import org.uorm.dao.common.ICommonDao;

public class DefaultSearch implements ISearch {

      private Logger logger = LogManager.getLogger(DefaultSearch.class);

      private ConnectionFactory connectionFactory = null;

      public DefaultSearch(DataSource dataSource) {

            ConnectionFactory connectionFactory = new DefaultConnectionFactory(dataSource);

            setConnectionFactory(connectionFactory);

      }

      private void setConnectionFactory(ConnectionFactory connectionFactory) {
            this.connectionFactory = connectionFactory;
      }

      @Override
      public <V extends Serializable> Serializable search(Class<V> clazz, String idFields, Serializable idValue) throws Exception {
            ICommonDao dao = new CommonDaoImpl(connectionFactory);
            Serializable iSerializable = dao.queryBusiness(clazz, idFields, idValue);
            return iSerializable;
      }

}
