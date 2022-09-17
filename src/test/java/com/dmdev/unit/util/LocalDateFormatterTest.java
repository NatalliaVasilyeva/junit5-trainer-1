package com.dmdev.unit.util;

import com.dmdev.util.LocalDateFormatter;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocalDateFormatterTest {

    private final String validDate = "2022-09-14" ;
    private final String invalidDateFormat = "2022/09/14" ;
    private final String nullableDate = null;

    @Test
    void shouldFormatDateFromStringToLocalDateCorrectly() {
        LocalDate actualFormattedDate = LocalDateFormatter.format(validDate);
        LocalDate expectedDate = LocalDate.of(2022, 9, 14);

        assertThat(actualFormattedDate).isEqualTo(expectedDate);
        assertThat(actualFormattedDate.getDayOfMonth()).isEqualTo(14);
        assertThat(actualFormattedDate.getMonth()).isEqualTo(Month.SEPTEMBER);
        assertThat(actualFormattedDate.getYear()).isEqualTo(2022);
    }

    @Test
    void shouldThrowExceptionIfDateIsNull() {
        assertThrowsExactly(NullPointerException.class, () -> LocalDateFormatter.format(null));
    }

    @Test
    void shouldThrowDateTimeParseExceptionIfDateStringUnformatted() {
        assertThrowsExactly(DateTimeParseException.class, () -> LocalDateFormatter.format(invalidDateFormat), "Text '2022/09/14' could not be parsed at index 4");
    }

    @Test
    void shouldReturnTrueIfDateIsValid() {
        assertTrue(LocalDateFormatter.isValid(validDate));
    }

    @Test
    void shouldReturnFalseIfDateIsInvalid() {
        assertFalse(LocalDateFormatter.isValid("invalidDate" ));
    }

    @Test
    void shouldReturnFalseIfDateIsNull() {
        assertFalse(LocalDateFormatter.isValid(nullableDate));
    }
}