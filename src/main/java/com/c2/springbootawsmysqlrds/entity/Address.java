package com.c2.springbootawsmysqlrds.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address implements Serializable {
    private String city;
    private String state;
    private long pinCode;
}