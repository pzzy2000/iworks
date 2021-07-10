package cn.oxo.iworks.web.spring.jackson;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class LongObjectMapper extends ObjectMapper {
      /**
      	 * 
      	 */
      private static final long serialVersionUID = -8365212110783780682L;

      private Logger logger = LogManager.getLogger(LongObjectMapper.class);

      public LongObjectMapper() {
            init();
      }

      private void init() {

            SimpleModule simpleModule = new SimpleModule();

            simpleModule.addSerializer(Long.class, ToStringSerializer.instance);

            simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

            registerModule(simpleModule);
            
            this.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
           
            this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

      }

}
