package sdp.queuemanager.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;

@Configuration
public class MongoDBConfig {

	@Value("${mongo.db.host}")
	private String hostName;

	@Value("${mongo.db.port}")
	private int port;

	@Value("${mongo.db.database.name}")
	private String databaseName;

	@Bean
	public MongoTemplate mongoTemplate() throws IOException {
		// Embedded Configuration
		EmbeddedMongoFactoryBean factoryBean = new EmbeddedMongoFactoryBean();
		factoryBean.setBindIp(hostName);
		factoryBean.setPort(port);
		
		/*
		 * //External DB Configuration 
		 * MongoClient mongoClient = new MongoClient(MONGO_DB_HOST, MONGO_DB_PORT);
		 */
		
		return new MongoTemplate(factoryBean.getObject(), databaseName);
	}
}
