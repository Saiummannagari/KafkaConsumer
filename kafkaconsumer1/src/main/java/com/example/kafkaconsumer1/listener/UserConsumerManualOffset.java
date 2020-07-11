/*package com.example.kafkaconsumer1.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

import com.example.kafkaconsumer1.model.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserConsumerManualOffset implements AcknowledgingMessageListener<String,User>{
	

	@Override
	@KafkaListener(topics = {"User-events-1"},containerFactory = "kafkaListenerContainerFactory")
	public void onMessage(ConsumerRecord<String, User> data, Acknowledgment acknowledgment) {
		// TODO Auto-generated method stub
		 log.info("ConsumerRecord : {} ", data );
	        acknowledgment.acknowledge();
		
	}
}
*/