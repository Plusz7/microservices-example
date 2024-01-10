package org.java.license.services.client;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.java.license.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class OrganizationDiscoveryClient {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private WebClient.Builder builder;



    public Mono<Organization> getOrganization(String organizationId) {
        List<ServiceInstance> instances = discoveryClient.getInstances("organization-service");
        String instanceUrl = String.format("%s/v1/organization/%s",instances.get(0).getUri().toString(), organizationId);
        return builder.build()
                .get()
                .uri(instanceUrl)
                .retrieve()
                .bodyToMono(Organization.class);
    }
}
