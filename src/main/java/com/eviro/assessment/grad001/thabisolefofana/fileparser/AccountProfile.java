package com.eviro.assessment.grad001.thabisolefofana.fileparser;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;

import java.net.URI;

@Entity
public class AccountProfile {

    @jakarta.persistence.Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private String httpimagelink;
}
