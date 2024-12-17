/*package com.assignment.customer;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

@Component
public class CustomInfoContributor implements InfoContributor {
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("app", 
                Info.Builder.create()
                    .withDetail("name", "Customer API")
                    .withDetail("description", "RESTful API for managing customers")
                    .withDetail("version", "0.0.1-SNAPSHOT")
                    .withDetail("owner", "ProjectW Development Team")
                    .build());
    }
} */
