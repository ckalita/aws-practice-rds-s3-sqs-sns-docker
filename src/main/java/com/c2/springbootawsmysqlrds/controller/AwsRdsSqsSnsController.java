package com.c2.springbootawsmysqlrds.controller;


import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.c2.springbootawsmysqlrds.constant.Constant;
import com.c2.springbootawsmysqlrds.entity.Person;
import com.c2.springbootawsmysqlrds.service.AWSMySQLRDSService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.annotation.NewSpan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@Slf4j
public class AwsRdsSqsSnsController {

    @Autowired
    private AWSMySQLRDSService awsMySQLRDSService;

    @Autowired
    private AmazonSNSClient snsClient;


    @RequestMapping(path = "/publishMessageToTopic/{message}", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE})
    public List<Person> publishMessageToTopic(@PathVariable String message){
        log.info("Message is {}",message);
        PublishRequest publishRequest = new PublishRequest(Constant.TOPIC_ARN, message, "AMAZON SNS SQSß");
        snsClient.publish(publishRequest);
        return null;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, })
    public String sample() {
        System.out.println("Test end points");
        return "This is Test End Point";
    }

    @RequestMapping(path = "/", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Person savePerson(@RequestBody Person person) {
        return awsMySQLRDSService.savePerson(person);
    }

    @RequestMapping(path = "/{personId}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE, })
    public Person findPerson(@PathVariable int personId) {
        return awsMySQLRDSService.findPerson(personId);
    }

    @DeleteMapping("/")
    public String deletePerson(@RequestBody Person person) {
        return awsMySQLRDSService.deletePerson(person);
    }

    @DeleteMapping("/{personId}")
    public String deletePerson(@PathVariable int personId) {
        return awsMySQLRDSService.deletePersonById(personId);
    }


    @PutMapping("/")
    public Person updatePerson(@RequestBody Person person) {
        return awsMySQLRDSService.updatePerson(person);
    }

    @NewSpan("process/all")
    @RequestMapping(path = "/all", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public List<Person> getAllPerson() {
        return awsMySQLRDSService.getAllPerson();
    }
}
