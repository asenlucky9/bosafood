package com.bosafood.mapper;

import org.mapstruct.Mapper;

import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface DateTimeMapper {
    default OffsetDateTime map(OffsetDateTime dateTime) {
        return dateTime;
    }
} 