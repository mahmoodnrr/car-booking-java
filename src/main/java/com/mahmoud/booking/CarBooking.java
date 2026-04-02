package com.mahmoud.booking;

import com.mahmoud.car.Car;
import com.mahmoud.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class CarBooking {

    private UUID Id;
    private User user;
    private Car car;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal price;
    private BookingStatus status;
    private LocalDateTime bookedAt;

    public CarBooking(){}

    public CarBooking(UUID id, User user, Car car, LocalDate startDate, LocalDate endDate, BigDecimal price, BookingStatus status, LocalDateTime bookedAt) {
        Id = id;
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.status = status;
        this.bookedAt = bookedAt;
    }

    public UUID getId() {
        return Id;
    }

    public User getUser() {
        return user;
    }

    public Car getCar() {
        return car;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    @Override
    public String toString() {
        return "CarBooking{" +
                "Id=" + Id +
                ", user=" + user +
                ", car=" + car +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", price=" + price +
                ", status=" + status +
                ", bookedAt=" + bookedAt +
                '}';
    }
}
