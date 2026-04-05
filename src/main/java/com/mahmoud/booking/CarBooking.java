package com.mahmoud.booking;

import com.mahmoud.car.Car;
import com.mahmoud.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CarBooking {

    private UUID id;
    private User user;
    private Car car;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal price;
    private BookingStatus status;
    private LocalDateTime bookedAt;

    public CarBooking(){}

    public CarBooking(UUID id, User user, Car car, LocalDateTime startDate, LocalDateTime endDate, BigDecimal price, BookingStatus status, LocalDateTime bookedAt) {
        this.id = id;
        this.user = user;
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.status = status;
        this.bookedAt = bookedAt;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Car getCar() {
        return car;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    @Override
    public String toString() {
        return "CarBooking{" +
                "id=" + id +
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
