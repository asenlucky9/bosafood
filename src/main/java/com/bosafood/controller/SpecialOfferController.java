package com.bosafood.controller;

import com.bosafood.dto.SpecialOfferDTO;
import com.bosafood.service.SpecialOfferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/special-offers")
@CrossOrigin(origins = "*")
@Tag(name = "Special Offers", description = "APIs for managing special offers and promotions")
public class SpecialOfferController {

    @Autowired
    private SpecialOfferService specialOfferService;

    @GetMapping
    @Operation(summary = "Get all special offers", description = "Retrieves a list of all special offers")
    public ResponseEntity<List<SpecialOfferDTO>> getAllSpecialOffers() {
        return ResponseEntity.ok(specialOfferService.getAllSpecialOffers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get special offer by ID", description = "Retrieves a specific special offer by its ID")
    public ResponseEntity<SpecialOfferDTO> getSpecialOfferById(@PathVariable Long id) {
        return ResponseEntity.ok(specialOfferService.getSpecialOfferById(id));
    }

    @GetMapping("/current")
    @Operation(summary = "Get current special offers", description = "Retrieves all special offers that are currently valid based on date")
    public ResponseEntity<List<SpecialOfferDTO>> getCurrentSpecialOffers() {
        return ResponseEntity.ok(specialOfferService.getCurrentSpecialOffers());
    }

    @PostMapping
    @Operation(summary = "Create special offer", description = "Creates a new special offer")
    public ResponseEntity<SpecialOfferDTO> createSpecialOffer(@Valid @RequestBody SpecialOfferDTO specialOfferDTO) {
        return ResponseEntity.ok(specialOfferService.createSpecialOffer(specialOfferDTO));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update special offer", description = "Updates an existing special offer")
    public ResponseEntity<SpecialOfferDTO> updateSpecialOffer(
            @PathVariable Long id,
            @Valid @RequestBody SpecialOfferDTO specialOfferDTO) {
        return ResponseEntity.ok(specialOfferService.updateSpecialOffer(id, specialOfferDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete special offer", description = "Deletes a special offer")
    public ResponseEntity<Void> deleteSpecialOffer(@PathVariable Long id) {
        specialOfferService.deleteSpecialOffer(id);
        return ResponseEntity.ok().build();
    }
} 