package com.mahmoud.booking;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CarBookingFileDataAccessService implements CarBookingDao {

    private final File FILE_PATH;

    public CarBookingFileDataAccessService(String fileName) {
        this.FILE_PATH = new File(fileName);
    }

    @Override
    public void saveBooking(CarBooking carBooking) {

        try {
            List<CarBooking> bookings;

            if (FILE_PATH.exists() && FILE_PATH.length() > 0) {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                    bookings = (List<CarBooking>) in.readObject();
                }

                bookings.add(carBooking);
            } else {
                bookings = new ArrayList<>();
                bookings.add(carBooking);
            }

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
                out.writeObject(bookings);
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<CarBooking> getAllBookings() {

        try {
            if (FILE_PATH.exists() && FILE_PATH.length() > 0) {
                List<CarBooking> bookings;
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                    bookings = (List<CarBooking>) in.readObject();
                }

                return bookings;
            }

            return new ArrayList<>(){};

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteBooking(UUID bookingId) {

        try {
            if (FILE_PATH.exists() && FILE_PATH.length() > 0) {
                List<CarBooking> bookings;
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                    bookings = (List<CarBooking>) in.readObject();
                }

                List<CarBooking> updatedBookings = new ArrayList<>();

                for (CarBooking booking : bookings) {
                    if (booking.getId().equals(bookingId)) {
                        booking.setStatus(BookingStatus.CANCELLED);
                    }
                }
                    updatedBookings = bookings;

                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
                    out.writeObject(updatedBookings);
                }

                return true;
            }

            return false;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}