package poc.projectmgt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfiguration {

	@Value("${db.redis.port}")
	private int redisPort;
	
	@Value("${db.redis.host}")
	private String redisHost;
	
	@Value("${db.redis.maxConnection}")
	private int maxConnection;
	
	@Value("${db.redis.maxIdleConnection}")
	private int maxIdleConnection;
	
	@Value("${db.redis.minIdleConnection}")
	private int minIdleConnection;
	
	
	@Bean
	JedisConnectionFactory jedisConnectionFactory() {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		  poolConfig.setMaxTotal(maxConnection);
		  poolConfig.setMinIdle(minIdleConnection);
		  poolConfig.setMaxIdle(maxIdleConnection);
		  JedisClientConfiguration clientConfig = JedisClientConfiguration.builder().usePooling().poolConfig(poolConfig).build();
		  return new JedisConnectionFactory(new RedisStandaloneConfiguration(redisHost,redisPort), clientConfig);

	}
	
	@Bean
    public RedisTemplate<String, String> redisTemplate() {
        final RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        return template;
    }
}
