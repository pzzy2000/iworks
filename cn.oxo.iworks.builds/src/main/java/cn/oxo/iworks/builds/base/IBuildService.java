package cn.oxo.iworks.builds.base;

import cn.oxo.iworks.builds.db.DBUtils;

public interface IBuildService {

      public TableBean init(Class<?> clazz) throws MyBatisBuildServiceException;

      public void build(TableBean tableBean, String outPath) throws MyBatisBuildServiceException;

      public void createTable(TableBean tableBean, DBUtils dbutils) throws MyBatisBuildServiceException;

}
