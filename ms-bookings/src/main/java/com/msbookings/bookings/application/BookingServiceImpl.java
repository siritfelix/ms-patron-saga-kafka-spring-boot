package com.msbookings.bookings.application;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.msbookings.bookings.domain.Booking;
import com.msbookings.bookings.domain.service.BookingService;
import com.msbookings.bookings.infrastructure.mq.MessageProducer;
import com.msbookings.bookings.infrastructure.repository.BookingRepository;
import com.msbookings.bookings.infrastructure.repository.CarRentalRepository;
import com.msbookings.bookings.infrastructure.repository.FlightReservationRepository;
import com.msbookings.bookings.infrastructure.repository.HotelReservationRepository;
import com.msbookings.bookings.infrastructure.repository.UserEntityRepository;
import com.msbookings.bookings.infrastructure.repository.entity.BookingEntity;
import com.msbookings.bookings.infrastructure.repository.entity.CarRentalEntity;
import com.msbookings.bookings.infrastructure.repository.entity.FlightReservationEntity;
import com.msbookings.bookings.infrastructure.repository.entity.HotelReservationEntity;
import com.msbookings.bookings.infrastructure.repository.entity.UserEntity;
import com.msbookings.bookings.infrastructure.rest.dto.BookingRequestDto;
import com.msbookings.bookings.infrastructure.rest.dto.ResponseDto;
import com.msbookings.bookings.shared.StatusGeneral;
import com.msbookings.bookings.shared.config.KafkaProperties;
import com.msbookings.bookings.shared.exception.NotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {
	private final MessageProducer messageProducer;
	private final UserEntityRepository userEntityRepository;
	private final FlightReservationRepository flightReservationRepository;
	private final HotelReservationRepository hotelReservationRepository;
	private final CarRentalRepository carRentalRepository;
	private final BookingRepository bookingRepository;
	private final KafkaProperties kafkaProperties;

	@Override
	public Booking createBooking(Booking booking) {

		Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(booking.getUser().getEmail());
		UserEntity userEntity;
		BookingEntity bookingEntity;
		if (userEntityOptional.isEmpty()) {
			userEntity = this.buildNewUserEntity(booking);
			bookingEntity = userEntity.getBookings().get(0);
		} else {
			userEntity = userEntityOptional.get();
			bookingEntity = this.buildBookingEntity(booking);
			bookingEntity.setUser(userEntity);
			userEntity.addbooking(bookingEntity);
		}
		log.info(userEntity.toString());
		userEntityRepository.save(userEntity);
		messageProducer.sendMessage(kafkaProperties.getProducer().getTopic(), new BookingRequestDto(bookingEntity));
		return bookingEntity.toBooking();

	}

	@Override
	public Booking getBooking(String id) {
		return bookingRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(ResponseDto.builder().build())).toBooking();

	}

	@Override
	public void upDateBooking(Booking booking) {
		flightReservationRepository.upDateStatus(booking.getFlightReservation().getId(),
				booking.getFlightReservation().getStatus());
		hotelReservationRepository.upDateStatus(booking.getHotelReservation().getId(),
				booking.getHotelReservation().getStatus());
		carRentalRepository.upDateStatus(booking.getCarRental().getId(),
				booking.getCarRental().getStatus());
		String statusBooking = this.getStatusBooking(booking.getFlightReservation().getStatus(),
				booking.getHotelReservation().getStatus(), booking.getCarRental().getStatus());
		bookingRepository.upDateStatus(booking.getId(), statusBooking);
	}

	@Override
	public List<Booking> getAllBookings() {
		log.info("Consulting all");
		return bookingRepository.findAll().stream().map(booking -> booking.toBooking()).collect(Collectors.toList());
	}

	private String getStatusBooking(String statusFligth, String statusHotel, String statusCar) {
		if (StatusGeneral.OK.toString().equals(statusFligth) && StatusGeneral.OK.toString().equals(statusHotel)
				&& StatusGeneral.OK.toString().equals(statusCar)) {
			return StatusGeneral.OK.toString();
		} else {
			return StatusGeneral.CANCELLED.toString();
		}
	}

	private UserEntity buildNewUserEntity(Booking booking) {

		UserEntity userEntity = new UserEntity(UUID.randomUUID().toString(), booking.getUser().getName(),
				booking.getUser().getLastName(), booking.getUser().getDocument(),
				booking.getUser().getEmail(), null);

		BookingEntity bookingEntity = this.buildBookingEntity(booking);
		bookingEntity.setUser(userEntity);
		List<BookingEntity> bookings = new ArrayList<>();
		bookings.add(bookingEntity);
		userEntity.setBookings(bookings);
		return userEntity;
	}

	private BookingEntity buildBookingEntity(Booking booking) {
		BookingEntity bookingEntity = new BookingEntity(UUID.randomUUID().toString(),
				StatusGeneral.PROCESING.toString(), null, null, null, null, LocalDateTime.now(), LocalDateTime.now());

		FlightReservationEntity flightReservationEntity = new FlightReservationEntity(
				UUID.randomUUID().toString(),
				StatusGeneral.PROCESING.toString(), booking.getFlightReservation().getFromDate(),
				booking.getFlightReservation().getToDate());
		HotelReservationEntity hotelReservationEntity = new HotelReservationEntity(UUID.randomUUID().toString(),
				StatusGeneral.PROCESING.toString(), booking.getHotelReservation().getFromDate(),
				booking.getHotelReservation().getToDate(), booking.getHotelReservation().getAddress(),
				booking.getHotelReservation().getReservationType());
		CarRentalEntity carRentalEntity = new CarRentalEntity(UUID.randomUUID().toString(),
				StatusGeneral.PROCESING.toString(), booking.getCarRental().getFromDate(),
				booking.getCarRental().getToDate(), booking.getCarRental().getTypeOfCar());
		bookingEntity.setFlightReservation(flightReservationEntity);
		bookingEntity.setHotelReservation(hotelReservationEntity);
		bookingEntity.setCarRental(carRentalEntity);
		return bookingEntity;
	}
}
