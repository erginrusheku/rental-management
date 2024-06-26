package com.rental_management.repo;

import com.rental_management.entities.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

    @Query("SELECT p from Property p WHERE p.owner.id = :ownerId AND p.propertyId = :propertyId")
    Property findPropertyByOwnerId(@Param("ownerId") Long ownerId,@Param("propertyId") Long propertyId);
}
