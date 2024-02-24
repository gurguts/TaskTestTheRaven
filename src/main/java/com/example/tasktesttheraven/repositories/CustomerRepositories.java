package com.example.tasktesttheraven.repositories;

import com.example.tasktesttheraven.models.CustomerEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * This interface, CustomerRepositories, extends CrudRepository for managing CustomerEntity instances.
 * It includes a method, findByIsActiveTrue(), to retrieve active customer entities.
 */
public interface CustomerRepositories extends CrudRepository<CustomerEntity, Long> {
    Iterable<CustomerEntity> findByIsActiveTrue();

    Optional<CustomerEntity> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("update CustomerEntity c set c.isActive = false where c.id = ?1")
    void deactivateCustomer(Long id);
}
