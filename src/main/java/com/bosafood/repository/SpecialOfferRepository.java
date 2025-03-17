package com.bosafood.repository;

import com.bosafood.model.SpecialOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;

@Repository
public interface SpecialOfferRepository extends JpaRepository<SpecialOffer, Long> {
    List<SpecialOffer> findByStartDateBeforeAndEndDateAfterAndActiveTrue(OffsetDateTime now, OffsetDateTime now2);
    List<SpecialOffer> findByActive(boolean active);
} 