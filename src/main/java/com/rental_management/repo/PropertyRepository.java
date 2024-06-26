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

    /*@Query(value = "SELECT p.* " +
            "FROM property p " +
            "JOIN promotion pr ON p.property_id = pr.property_id " +
            "WHERE pr.promotion_id = :promotionId " +
            "AND p.property_id = :propertyId", nativeQuery = true)
    Property findPropertyByPromotionId(Long promotionId, Long property);*/

    @Query(value = "SELECT * FROM property p WHERE p.property_id = :propertyId AND EXISTS(SELECT 1 FROM promotion pr WHERE pr.property_property_id = p.property_id AND pr.id = :promotionId)", nativeQuery = true)
    Property findPromotionByPropertyId(@Param("propertyId") Long propertyId,@Param("promotionId") Long promotionId);
}
