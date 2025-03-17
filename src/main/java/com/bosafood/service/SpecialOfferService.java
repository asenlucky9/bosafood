package com.bosafood.service;

import com.bosafood.dto.SpecialOfferDTO;
import com.bosafood.model.MenuItem;
import com.bosafood.model.SpecialOffer;
import com.bosafood.exception.ResourceNotFoundException;
import com.bosafood.repository.MenuItemRepository;
import com.bosafood.repository.SpecialOfferRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SpecialOfferService {

    @Autowired
    private SpecialOfferRepository specialOfferRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    public List<SpecialOfferDTO> getAllSpecialOffers() {
        List<SpecialOffer> offers = specialOfferRepository.findAll();
        return offers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SpecialOfferDTO> getCurrentSpecialOffers() {
        OffsetDateTime now = OffsetDateTime.now();
        List<SpecialOffer> offers = specialOfferRepository.findByStartDateBeforeAndEndDateAfterAndActiveTrue(now, now);
        return offers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SpecialOfferDTO getSpecialOfferById(Long id) {
        SpecialOffer offer = specialOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Special offer not found with id: " + id));
        return convertToDTO(offer);
    }

    @Transactional
    public SpecialOfferDTO createSpecialOffer(SpecialOfferDTO offerDTO) {
        validateDates(offerDTO);
        Set<MenuItem> menuItems = getMenuItems(offerDTO.getMenuItemIds());
        
        SpecialOffer offer = new SpecialOffer();
        updateSpecialOfferFromDTO(offer, offerDTO);
        offer.setMenuItems(menuItems);
        
        SpecialOffer savedOffer = specialOfferRepository.save(offer);
        return convertToDTO(savedOffer);
    }

    @Transactional
    public SpecialOfferDTO updateSpecialOffer(Long id, SpecialOfferDTO offerDTO) {
        validateDates(offerDTO);
        SpecialOffer existingOffer = specialOfferRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Special offer not found with id: " + id));

        Set<MenuItem> menuItems = getMenuItems(offerDTO.getMenuItemIds());
        updateSpecialOfferFromDTO(existingOffer, offerDTO);
        existingOffer.setMenuItems(menuItems);

        SpecialOffer updatedOffer = specialOfferRepository.save(existingOffer);
        return convertToDTO(updatedOffer);
    }

    public void deleteSpecialOffer(Long id) {
        if (!specialOfferRepository.existsById(id)) {
            throw new ResourceNotFoundException("Special offer not found with id: " + id);
        }
        specialOfferRepository.deleteById(id);
    }

    private void validateDates(SpecialOfferDTO offerDTO) {
        if (offerDTO.getStartDate().isAfter(offerDTO.getEndDate())) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
    }

    private Set<MenuItem> getMenuItems(Set<Long> menuItemIds) {
        Set<MenuItem> menuItems = new HashSet<>();
        for (Long menuItemId : menuItemIds) {
            MenuItem menuItem = menuItemRepository.findById(menuItemId)
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item not found with id: " + menuItemId));
            menuItems.add(menuItem);
        }
        return menuItems;
    }

    private void updateSpecialOfferFromDTO(SpecialOffer offer, SpecialOfferDTO dto) {
        offer.setTitle(dto.getTitle());
        offer.setDescription(dto.getDescription());
        offer.setDiscountPercentage(dto.getDiscountPercentage());
        offer.setStartDate(dto.getStartDate());
        offer.setEndDate(dto.getEndDate());
        offer.setActive(dto.isActive());
        offer.setTermsAndConditions(dto.getTermsAndConditions());
        offer.setMinimumOrderValue(dto.getMinimumOrderValue());
        offer.setMaximumDiscount(dto.getMaximumDiscount());
    }

    private SpecialOfferDTO convertToDTO(SpecialOffer offer) {
        SpecialOfferDTO dto = new SpecialOfferDTO();
        dto.setId(offer.getId());
        dto.setTitle(offer.getTitle());
        dto.setDescription(offer.getDescription());
        dto.setDiscountPercentage(offer.getDiscountPercentage());
        dto.setStartDate(offer.getStartDate());
        dto.setEndDate(offer.getEndDate());
        dto.setActive(offer.isActive());
        dto.setTermsAndConditions(offer.getTermsAndConditions());
        dto.setMinimumOrderValue(offer.getMinimumOrderValue());
        dto.setMaximumDiscount(offer.getMaximumDiscount());
        
        Set<Long> menuItemIds = offer.getMenuItems().stream()
                .map(MenuItem::getId)
                .collect(Collectors.toSet());
        dto.setMenuItemIds(menuItemIds);
        
        return dto;
    }
}
