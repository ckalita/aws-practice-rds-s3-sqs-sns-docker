package com.c2.springbootawsmysqlrds.service;

import com.amazonaws.services.sns.AmazonSNSClient;
import com.c2.springbootawsmysqlrds.entity.Person;
import com.c2.springbootawsmysqlrds.repositories.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class AWSMySQLRDSService {

    public static final String END_POINT = "https://sqs.us-east-1.amazonaws.com/233057369402/chandanawssqs";

    @Autowired
    private PersonRepository repository;

    @Autowired
    private QueueMessagingTemplate queueMessagingTemplate;

    @Autowired
    private AmazonSNSClient snsClient;

    public Person savePerson(Person person) {
        System.out.println("Person added successfully");
        queueMessagingTemplate.send(END_POINT, MessageBuilder.withPayload("Person added successfully").build());
        queueMessagingTemplate.send(END_POINT, MessageBuilder.withPayload(person).build());
        log.info("Person added successfully: {}",person);
        return repository.save(person);
    }

    @SqsListener("chandanawssqs")
    public void loadMessageFromSQS(String message)  {
        log.info("message from SQS Queue : {}",message);
        if(message.equalsIgnoreCase("Person added successfully")){
            log.info("Congratulations");
        }
        if(message.equalsIgnoreCase("getall")){
            List<Person> personList = this.getAllPerson();
            log.info("List is : {}",personList);
        }
    }


    public Person findPerson(int personId) {
        System.out.println("Person fetched successfully");
        return repository.findById(personId).orElseThrow(()-> new RuntimeException("Person Not found"));
    }

    public String deletePerson(Person person) {
        repository.delete(person);
        return "Person removed successfully";
    }

    public String deletePersonById(int personId) {
        repository.deleteById(personId);
        return "Person removed successfully";
    }

    public Person updatePerson(Person person) {
        return repository.save(person);
    }

    @NewSpan("process/all")
    public List<Person> getAllPerson() {
        log.info("inside service");
        return repository.findAll();
    }

}
