package cn.oxo.iworks.cache;

import java.io.Serializable;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.uorm.dao.common.CommonDaoImpl;
import org.uorm.dao.common.ConnectionFactory;
import org.uorm.dao.common.DefaultConnectionFactory;
import org.uorm.dao.common.ICommonDao;

public class DefaultDBCacheSearch implements IDBCacheSearch {

    private Logger logger = LogManager.getLogger(DefaultDBCacheSearch.class);

    private ConnectionFactory connectionFactory = null;

    public DefaultDBCacheSearch(DataSource dataSource) {

        ConnectionFactory connectionFactory = new DefaultConnectionFactory(dataSource);

        setConnectionFactory(connectionFactory);

    }

    private void setConnectionFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Serializable search(Class<Serializable> clazz, String idFields, Serializable idValue) throws Exception {

        ICommonDao dao = new CommonDaoImpl(connectionFactory);
        Serializable iSerializable = dao.queryBusiness(clazz, idFields, idValue);
        return iSerializable;

    }

}
