package com.example.kafkaconsumer1.config;


import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.example.kafkaconsumer1.model.User;

@Configuration
@EnableKafka
public class ConsumerConfiguration {
	/* @Bean
	 ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
	            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
	            ConsumerFactory<String, User> kafkaConsumerFactory) {
	        ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory<>();
	     //   configurer.configure(factory, kafkaConsumerFactory);
	        factory.setConsumerFactory(consumerFactory());
	        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
	        return factory;
	    }
	    */
	

	
	 @Bean
	    public ConsumerFactory<String, User> consumerFactory() {
	    	
	    	JsonDeserializer<User> deserializer=new JsonDeserializer<>(User.class);
	    	deserializer.setRemoveTypeHeaders(false);
	    	deserializer.addTrustedPackages("*");
	    	deserializer.setUseTypeMapperForKey(true);
	    	
	        Map<String, Object> config = new HashMap<>();

	        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9093");
	        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
	        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
	        config.put("security.protocol", "SSL");
	        config.put("ssl.truststore.location", "client.truststore.jks");
	        config.put("ssl.truststore.password", "password");
	        config.put("ssl.keystore.location", "client.keystore.jks");
	        config.put("ssl.keystore.password", "password");
	        config.put("ssl.endpoint.identification.algorithm", "");
	        

	        return new DefaultKafkaConsumerFactory<>(config,new StringDeserializer(),deserializer);
	    }


	    @Bean
	    public ConcurrentKafkaListenerContainerFactory<String, User> kafkaListenerContainerFactory() {
	        ConcurrentKafkaListenerContainerFactory<String, User> factory = new ConcurrentKafkaListenerContainerFactory();
	        factory.setConsumerFactory(consumerFactory());
	        return factory;
	    }
}
