package cn.oxo.iworks.tools;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SystemConfig {

    private static Logger logger = LogManager.getLogger(SystemConfig.class);

    @SuppressWarnings("unchecked")
    public static <V> V selSysConfig(String configKey, V defaultValue) {

        String result = SystemConfig.instances().searchConfig(configKey);
        if (StringUtils.isEmpty(result) || defaultValue == null)
            return defaultValue;
        else {
            if (defaultValue.getClass().equals(Integer.class)) {
                return (V)Integer.valueOf(result);
            } else if (defaultValue.getClass().equals(Boolean.class)) {
                return (V)Boolean.valueOf(result);
            } else if (defaultValue.getClass().equals(Double.class)) {
                return (V)Double.valueOf(result);
            } else {
                return (V)result;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <V> V selSysConfig(String configKey, Class<V> clazz) {

        String result = SystemConfig.instances().searchConfig(configKey);
        if (StringUtils.isEmpty(result))
            return null;
        else {
            if (clazz.equals(Integer.class)) {
                return (V)Integer.valueOf(result);
            } else if (clazz.equals(Boolean.class)) {
                return (V)Boolean.valueOf(result);
            } else if (clazz.equals(Double.class)) {
                return (V)Double.valueOf(result);
            } else if (clazz.equals(String.class)) {
                return (V)result;
            } else {
                throw new ToolsUnitsException("class  type  error ! Integer  , Boolean , Double , String ");
            }
        }
    }

    private static String system_config_configs_file = "configs/system.properties";

    public String configName(String configName) {

        if (configName == null)
            return null;
        if (configName.startsWith("{") && configName.endsWith("}")) {
            String configName_ = configName.substring(1, configName.length() - 1);
            configName_ = searchConfig(configName_);
            return configName_;
        } else {
            return configName;
        }
    }

    private static SystemConfig config;

    private Properties properties = new Properties();

    public synchronized static SystemConfig instances() {

        if (config == null) {
            config = new SystemConfig();
            config.loadConfig(system_config_configs_file);
        }
        return config;
    }

    public synchronized static SystemConfig instances(String configfile) {

        if (config == null) {
            config = new SystemConfig();
            config.loadConfig(configfile);
        }
        return config;
    }

    public void loadConfig(String configfile) {
        try {

            String file = configfile;

            logger.info("start load sysconfig file " + file);

            InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
            if (in == null) {
                in = getClass().getClassLoader().getResourceAsStream(file);
            }
            if (in == null) {
                in = this.getClass().getResourceAsStream(file);
            }

            if (in == null) {
                throw new ToolsUnitsException(" not find  load  Config  file : "
                    + SystemConfig.system_config_configs_file + "   " + SystemConfig.system_config_configs_file);
            }
            properties.load(in);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new ToolsUnitsException(e);
        }

    }

    private String searchConfig(String configKey) {
        try {

            String str = properties.getProperty(configKey);
            if (str == null)
                return null;
            String reStr = new String(str.getBytes("ISO-8859-1"), "UTF-8");
            return reStr;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static void main(String[] args) {

    }
}