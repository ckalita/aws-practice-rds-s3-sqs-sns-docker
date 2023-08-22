package com.c2.springbootawsmysqlrds.controller;

import com.c2.springbootawsmysqlrds.service.AwsS3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/s3")
public class AwsS3Controller {

    @Autowired
    private AwsS3Service awsS3Service;

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return new ResponseEntity<>(awsS3Service.uploadFile(file), HttpStatus.OK);
    }

    @RequestMapping(path = "/{fileName}", method = RequestMethod.GET)
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = awsS3Service.downloadFile(fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @RequestMapping(path = "/{fileName}", method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(awsS3Service.deleteFile(fileName), HttpStatus.OK);
    }

}
