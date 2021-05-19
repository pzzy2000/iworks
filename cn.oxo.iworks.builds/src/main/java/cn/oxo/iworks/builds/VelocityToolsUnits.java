package cn.oxo.iworks.builds;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import cn.oxo.iworks.builds.db.DBUtils;

public class VelocityToolsUnits {

      private static Properties properties = new Properties();

      static {
            properties.setProperty("resource.loader", "class");
            properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
            // properties.setProperty("input.encoding", "UTF-8");
            // properties.setProperty("output.encoding", "UTF-8");
            properties.setProperty(RuntimeConstants.ENCODING_DEFAULT, "UTF-8");
            properties.setProperty(RuntimeConstants.INPUT_ENCODING, "UTF-8");
            properties.setProperty(RuntimeConstants.OUTPUT_ENCODING, "UTF-8");

      }

      private static File createPath(String file) {
            File iFile = new File(file);
            if (!iFile.getParentFile().exists()) {
                  iFile.getParentFile().mkdirs();
            }
            return iFile;
      }

      public static void toFile(String vm, Map<String, Object> params, String file) throws Exception {

            // FileWriter iFileWriter = new FileWriter(file);
            StringWriter iFileWriter = new StringWriter();

            VelocityEngine velocityEngine = new VelocityEngine(properties);
            VelocityContext context = new VelocityContext();

            for (Entry<String, Object> param : params.entrySet()) {

                  context.put(param.getKey(), param.getValue());
            }

            velocityEngine.mergeTemplate(vm, "UTF8", context, iFileWriter);

            String cn = iFileWriter.toString().replaceAll(" {1,}", " ");

            // String cn = iFileWriter.toString().replaceAll("\\s{2,}", " ");

            OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(createPath(file)), "UTF-8");

            fileWriter.write(cn);

            fileWriter.flush();

            fileWriter.close();

      }

      public static void toDB(String vm, Map<String, Object> params, DBUtils dbutils) throws Exception {

            // FileWriter iFileWriter = new FileWriter(file);

            StringWriter iFileWriter = new StringWriter();

            VelocityEngine velocityEngine = new VelocityEngine(properties);
            VelocityContext context = new VelocityContext();

            for (Entry<String, Object> param : params.entrySet()) {

                  context.put(param.getKey(), param.getValue());
            }

            velocityEngine.mergeTemplate(vm, "UTF8", context, iFileWriter);

            String sql = iFileWriter.toString().replaceAll(" {1,}", " ");

            System.out.println("create table :  " + sql);

            if (dbutils == null) {
                  System.out.println("dbutils null !  mysql ->MYSQLDBUtils ;  Mariadb - >  MariadbDBUtils ");
            } else {

                  dbutils.exec(sql);

            }

            iFileWriter.flush();

            iFileWriter.close();
      }

}
