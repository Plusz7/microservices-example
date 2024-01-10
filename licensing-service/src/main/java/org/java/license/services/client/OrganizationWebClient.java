package org.java.license.services.client;

import org.java.license.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class OrganizationWebClient {

    @Autowired
    WebClient.Builder builder;

    public Mono<Organization> getOrganization(String organizationId) {
        return builder.build()
                .get()
                .uri("http://organization-service/v1/organization/{organizationId}")
                .retrieve()
                .bodyToMono(Organization.class);
    }

}
