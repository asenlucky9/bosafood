package com.bosafood.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesReportDTO {
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private BigDecimal totalRevenue;
    private int totalOrders;
    private BigDecimal averageOrderValue;
    private Map<String, BigDecimal> revenueByCategory;
    private List<DailySalesDTO> dailySales;
    private List<TopSellerDTO> topSellers;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailySalesDTO {
        private LocalDate date;
        private int orderCount;
        private BigDecimal revenue;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TopSellerDTO {
        private String itemName;
        private int quantity;
        private BigDecimal revenue;
    }
} 