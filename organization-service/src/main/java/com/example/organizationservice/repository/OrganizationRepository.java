package com.example.organizationservice.repository;

import java.util.Optional;

import com.example.organizationservice.model.Organization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization,String>  {
    public Optional<Organization> findById(String organizationId);
}