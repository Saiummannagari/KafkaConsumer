package com.example.kafkaconsumer1.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.kafkaconsumer1.model.User;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserConsumer {
	
	@KafkaListener(topics = {"UserEventsTopic-1"},containerFactory="kafkaListenerContainerFactory")
    public void consume(@RequestBody User user) {
        System.out.println("Consumed message: " + user);

}
}

