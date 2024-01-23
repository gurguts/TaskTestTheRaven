package com.example.tasktesttheraven.repositories;

import com.example.tasktesttheraven.models.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * This interface, CustomerRepositories, extends CrudRepository for managing CustomerEntity instances.
 * It includes a method, findByIsActiveTrue(), to retrieve active customer entities.
 */
public interface CustomerRepositories extends CrudRepository<CustomerEntity, Long> {
    Iterable<CustomerEntity> findByIsActiveTrue();
}
