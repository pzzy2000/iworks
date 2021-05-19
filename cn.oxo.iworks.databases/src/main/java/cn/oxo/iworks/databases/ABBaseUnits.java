package cn.oxo.iworks.databases;

import org.springframework.beans.factory.annotation.Autowired;

import cn.oxo.iworks.databases.ids.IMySQLCreateIdFactory;

public abstract class ABBaseUnits {

      @Autowired
      protected IMySQLCreateIdFactory createIdFactory;

      // @Resource
      // protected RedisService redisService;

}
