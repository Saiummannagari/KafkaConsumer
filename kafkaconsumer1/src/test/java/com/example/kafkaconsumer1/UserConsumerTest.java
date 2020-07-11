package com.example.kafkaconsumer1;

import static org.hamcrest.CoreMatchers.isA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.kafkaconsumer1.listener.UserConsumer;
import com.example.kafkaconsumer1.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;



@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT,
classes=Kafkaconsumer1Application.class)
@EmbeddedKafka
//@EmbeddedKafka(topics = {"Users_List_New"}, partitions = 1)
@TestPropertySource(properties = {"spring.kafka.producer.bootstrap-servers=localhost:9092"
, "spring.kafka.consumer.bootstrap-servers=localhost:9092"})
class UserConsumerTest {
	
	
	    @Autowired
	    EmbeddedKafkaBroker embeddedKafkaBroker;

	    @Autowired
	    KafkaTemplate<String, User> kafkaTemplate;

	    @Autowired
	    KafkaListenerEndpointRegistry endpointRegistry;
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    @SpyBean
	    UserConsumer userConsumer;
	    
	    @BeforeEach
	    void setUp() {

	        for (MessageListenerContainer messageListenerContainer : endpointRegistry.getListenerContainers()){
	            ContainerTestUtils.waitForAssignment(messageListenerContainer, embeddedKafkaBroker.getPartitionsPerTopic());
	        }
	    }

	@Test
	void test() throws JsonMappingException, JsonProcessingException, InterruptedException, ExecutionException {
		//given
        String exampleUserJson = "{\"name\":\"sai\",\"dept\":\"cse\",\"salary\":25000}";
        User user=objectMapper.readValue(exampleUserJson, User.class);
        
        CountDownLatch latch = new CountDownLatch(1);
        latch.await(3, TimeUnit.SECONDS);
        
        kafkaTemplate.sendDefault(user).get();
        
        
        verify(userConsumer).consume(user);
        
	}

}
