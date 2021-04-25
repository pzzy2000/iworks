package cn.oxo.iworks.databases.ids;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 创建数据库ID
 * 
 * @author i datacenterId -> 1 -30 machineId -> 1-500
 */
public class MySQLCreateIdFactory implements IMySQLCreateIdFactory
{

    private Logger logger = LogManager.getLogger(MySQLCreateIdFactory.class);

    // private SnowFlake snowFlake = new SnowFlake((long) SQLUilts.getIUniqueId(), (long)
    // SQLUilts.getIUniqueId());

    private SnowflakeIdWorker snowFlake;

    // 构造方法设置机器码：第9个机房的第20台机器
    public MySQLCreateIdFactory(long datacenterId, long machineId)
    {
        logger.info("MySQL Create Id Factory : datacenterId -> " + datacenterId + " machineId  "
                    + machineId);
        SnowflakeIdWorker snowFlake = new SnowflakeIdWorker(datacenterId, machineId);

        setSnowFlake(snowFlake);
    }

    private void setSnowFlake(SnowflakeIdWorker snowFlake)
    {
        this.snowFlake = snowFlake;
    }

    public long createDBId()
    {

        return snowFlake.nextId();
    }

    public static void main(String[] args)
    {
        SnowFlake snowFlake = new SnowFlake(1L, 1l);

        System.out.println("--> " + snowFlake.nextId());
    }

}
