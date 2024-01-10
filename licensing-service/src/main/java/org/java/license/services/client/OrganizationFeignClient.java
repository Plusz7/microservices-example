package org.java.license.services.client;

import org.java.license.model.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@FeignClient("organization-service")
public interface OrganizationFeignClient {

    @GetMapping(
            value = "/v1/organization/{organizationId}",
            consumes = "application/json")
    Mono<Organization> getOrganization(@PathVariable("organizationId") String organizationId);
}
