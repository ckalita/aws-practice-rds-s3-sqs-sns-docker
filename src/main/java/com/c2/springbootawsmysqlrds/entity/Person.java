package com.c2.springbootawsmysqlrds.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "person")
public class Person implements Serializable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private int personId;

    private String name;

    private int age;

    private String email;

    private Address address;
}