package com.mahmoud.booking;

import java.io.*;
import java.util.Arrays;
import java.util.UUID;

public class CarBookingFileDataAccessService implements CarBookingDao {

    private final File FILE_PATH;

    public CarBookingFileDataAccessService(String fileName) {
        this.FILE_PATH = new File(fileName);
    }

    @Override
    public void saveBooking(CarBooking carBooking) {

        try {
            CarBooking[] bookings;

            if (FILE_PATH.exists() && FILE_PATH.length() > 0) {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                    bookings = (CarBooking[]) in.readObject();
                }

                bookings = Arrays.copyOf(bookings, bookings.length + 1);
                bookings[bookings.length - 1] = carBooking;
            } else {
                bookings = new CarBooking[]{carBooking};
            }

            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
                out.writeObject(bookings);
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CarBooking[] getAllBookings() {

        try {
            if (FILE_PATH.exists() && FILE_PATH.length() > 0) {
                CarBooking[] bookings;
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                    bookings = (CarBooking[]) in.readObject();
                }

                return bookings;
            }

            return new CarBooking[]{};

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteBooking(UUID bookingId) {

        try {
            if (FILE_PATH.exists() && FILE_PATH.length() > 0) {
                CarBooking[] bookings;
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
                    bookings = (CarBooking[]) in.readObject();
                }

                CarBooking[] updatedBookings = new CarBooking[bookings.length];

                for (int i = 0; i < bookings.length; i++) {
                    if (bookings[i].getId().equals(bookingId)) {
                        bookings[i].setStatus(BookingStatus.CANCELLED);
                    }

                    updatedBookings[i] = bookings[i];
                }

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