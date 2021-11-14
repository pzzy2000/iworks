package cn.oxo.iworks.builds.testw;

import cn.oxo.iworks.builds.base.IBuildService;
import cn.oxo.iworks.builds.base.TableBean;
import cn.oxo.iworks.builds.db.DBUtils;
import cn.oxo.iworks.builds.db.MariadbDBUtils;
import cn.oxo.iworks.builds.mybatis.MyBatisBuildService;
import cn.oxo.iworks.builds.test.bean.CustomerBean;


public  class CreateDb {

    public static void main(String[] args) throws Exception {

        String basePath = "cn.oxo.iworks.builds.test";
        String outPath = "d:\\charging1";
        String url = "";
        String username = "";
        String password = "";
        IBuildService iIBuildService = new MyBatisBuildService(basePath, false);

        DBUtils iMYSQLDBUtils = new MariadbDBUtils(url, username, password);
        {
            TableBean iTableBean = iIBuildService.init(CustomerBean.class);

//             iIBuildService.createTable(iTableBean, iMYSQLDBUtils);

            iIBuildService.build(iTableBean, outPath);
        }

    }

}