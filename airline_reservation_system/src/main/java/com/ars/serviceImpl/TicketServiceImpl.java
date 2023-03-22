package com.ars.serviceImpl;

import java.time.LocalDate;


import org.modelmapper.ModelMapper;

import com.ars.dao.AirlineDAO;
import com.ars.dao.FlightDAO;
import com.ars.dao.PassengerDao;
import com.ars.dao.TicketDAO;
import com.ars.daoImpl.AirlineDAOImpl;
import com.ars.daoImpl.FlightDAOImpl;
import com.ars.daoImpl.PassengerDaoImpl;
import com.ars.daoImpl.TicketDAOImpl;
import com.ars.entity.Airline;
import com.ars.entity.Flight;
import com.ars.entity.Passenger;
import com.ars.entity.TicketBooking;
import com.ars.exception.GlobalException;
import com.ars.model.TicketBookingDTO;
import com.ars.service.TicketService;

public class TicketServiceImpl implements TicketService{
FlightDAO fdao=new FlightDAOImpl();
AirlineDAO adao=new AirlineDAOImpl();
PassengerDao pdao=new PassengerDaoImpl();
TicketDAO tdao=new TicketDAOImpl();
	
//for book flight
@Override
public TicketBookingDTO bookFlight(int flight_id, LocalDate date, String pEmail, int no_of_passenger,
		String airName) {
	Flight flight=fdao.getFlight(flight_id);
	if(flight.getAvilableSeats()>no_of_passenger)
	{
		Passenger p=pdao.getPassengerByEmail(pEmail);
		Airline airline=adao.getAirlineByName(airName);
		float totalfare=airline.getFare()*no_of_passenger;
		int avilable_seat=(flight.getAvilableSeats()-no_of_passenger);
		TicketBooking bookedTicket=tdao.bookFlight(airline, p, date, flight, no_of_passenger, totalfare, avilable_seat);
	return new ModelMapper().map(bookedTicket,TicketBookingDTO.class);
	}
	return null;
}

//for cancel Booking
	@Override
	public void cancelBooking(int id){
		tdao.cancelBooking(id);
		
	}

//for get Ticket by id	
	@Override
	public TicketBookingDTO getTicket(int id) {
		TicketBooking tick=tdao.getTicket(id);
		if(tick!=null)
			return new ModelMapper().map(tick, TicketBookingDTO.class);
	
	
	throw new GlobalException("Passenger details not exist!!!");
	}

	
	

}
