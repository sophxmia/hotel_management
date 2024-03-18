package com.hotelmanagement.hotel_management.data;

import org.springframework.beans.factory.annotation.Value;

/**
 * Projection for {@link Guest}
 */
public interface GuestInfo {
    Integer getId();
@Value("#{target.firstName + ' ' + target.lastName}")
    String getFullName();
}