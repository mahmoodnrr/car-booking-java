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
    public void saveBooking(CarBooking carBooking) throws IOException, ClassNotFoundException {

        List<CarBooking> carBookingList = new ArrayList<>();

        if (FILE_PATH.exists() && FILE_PATH.length() > 0) {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH));
            carBookingList = (ArrayList<CarBooking>) in.readObject();
            in.close();
        }

        carBookingList.add(carBooking);

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
        out.writeObject(carBookingList);
        out.close();
    }

    @Override
    public CarBooking[] getAllBookings() throws IOException, ClassNotFoundException {

        if (FILE_PATH.exists() && FILE_PATH.length() > 0) {

            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH));
            List<CarBooking> carBookingList = (ArrayList<CarBooking>) in.readObject();
            in.close();

            CarBooking[] bookings = new CarBooking[carBookingList.size()];

            for (int i = 0; i < carBookingList.size(); i++) {
                bookings[i] = carBookingList.get(i);
            }

            return bookings;
        }

        return new CarBooking[]{};
    }

    @Override
    public boolean deleteBooking(UUID bookingId) throws IOException, ClassNotFoundException {

        if (FILE_PATH.exists() && FILE_PATH.length() > 0) {

            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_PATH));
            List<CarBooking> carBookingList = (ArrayList<CarBooking>) in.readObject();
            in.close();

            List<CarBooking> updatedCarBookingList = new ArrayList<>();
            for (CarBooking carBooking : carBookingList) {
                if (carBooking.getId().equals(bookingId)) {
                    carBooking.setStatus(BookingStatus.CANCELLED);
                }
                updatedCarBookingList.add(carBooking);
            }

            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_PATH));
            out.writeObject(updatedCarBookingList);
            out.close();

            return true;
        }

        return false;
    }
}