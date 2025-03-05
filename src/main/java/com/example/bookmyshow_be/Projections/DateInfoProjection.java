package com.example.bookmyshow_be.Projections;

import java.time.LocalDate;

public interface DateInfoProjection {
    LocalDate getShowDate();
    String getDayName();
}
