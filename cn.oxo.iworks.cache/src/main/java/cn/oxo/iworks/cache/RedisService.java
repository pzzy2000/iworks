package cn.oxo.iworks.cache;

import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;

@SuppressWarnings("rawtypes")
public class RedisService extends RedisGPSCacheService implements IRedisService {

	private Logger logger = LogManager.getLogger(RedisService.class);

	private RedisTemplate redisTemplate;

	public RedisService(RedisTemplate redisTemplate) {
		super(redisTemplate);
		this.redisTemplate = redisTemplate;

	}

	@PostConstruct
	public void init() {

		Timer timer = new Timer(); // 1. 创建Timer实例，关联线程不能是daemon(守护/后台)线程

		timer.schedule(new TimerTask() {

			@SuppressWarnings("unchecked")
			@Override
			public void run() {

				redisTemplate.opsForValue().set("monter", "1");

			}
		}, 3000L, 2000L); // 3. 通过Timer定时定频率调用fooTimerTask的业务代码
	}

	@Override
	public void clear(Object value) {
		if (value == null)
			return;
		if (value.getClass().isAnnotationPresent(GPSCCache.class) == false) {
			throw new RuntimeException("cache object " + value.getClass() + "  not find @GPSCCache");
		}
		GPSCCache iGPSCCache = value.getClass().getAnnotation(GPSCCache.class);

		for (String field : iGPSCCache.cachefield()) {
			try {
				Field iField = FieldUtils.getField(value.getClass(), field,true);
				//iField.setAccessible(true);
				Object id = iField.get(value);
				if (id == null) {
					throw new RuntimeException("field : " + field + " result ： " + value.getClass() + " value null !");
				} else {
					delete(id.toString(), field, value.getClass());
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}
	}

    

    @SuppressWarnings("unchecked")
    @Override
    public void putByGeneral(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
         
    }

    @Override
    public Object getByGeneral(String key) {
        return redisTemplate.opsForValue().get(key);
    }

}
