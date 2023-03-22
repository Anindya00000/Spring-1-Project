package com.ars_vc;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.ars.config.HibernateUtil;
import com.ars.entity.Airline;
import com.ars.entity.Flight;
import com.ars.entity.Passenger;
import com.ars.entity.TicketBooking;
import com.ars.model.TicketBookingDTO;
import com.ars.service.FlightService;
import com.ars.service.TicketService;
import com.ars.serviceImpl.FlightServiceImpl;
import com.ars.serviceImpl.TicketServiceImpl;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicketBookingTest {
	FlightService flightService=new FlightServiceImpl();
	TicketService ticketService = new TicketServiceImpl();
	public static SessionFactory sessionFactory;
	private Session session;
	@BeforeAll
	static void setUp()
	{
		sessionFactory=HibernateUtil.getSessionFactory();
	}
	@BeforeEach
	void openSession()
	{
		session= sessionFactory.openSession();
	}
	@AfterEach
	void closeSession()
	{
		if(session!=null)
			session.close();
		System.out.println("session closed");
	}
	@AfterAll
	static void tearDown()
	{
		if(sessionFactory!=null)
			sessionFactory.close();
		System.out.println("Session factory closed");
	}

	
	@Test
	@Order(1)
	@DisplayName("Test for Book Flight")
	void testBookFlight()							//testing for Book Flight 
	{
		Airline airline=Airline.builder().airlineName("Air India").fare(2000).build();
		Transaction tx=session.beginTransaction();
		Flight flight = Flight.builder().airline(airline).avilableSeats(10).totalSeats(125).destination("delhi").source("pune").time("05:30").travellerClass("economy").date(LocalDate.of(2023,03,20)).build();
		List<Flight> flights=new ArrayList<Flight>();
		flights.add(flight);
		airline.setFlights(flights);
		flightService.saveFlight(flight);
		Passenger pass=Passenger.builder().name("Anindya Chakreborty.").phno("9876325405").email("aninadya@gmail.com").password("ani@123").userName("anindya999").role("user").build();
		LocalDate date=LocalDate.now();
		session.save(pass);
		float fare = (float) 10000.00;
		TicketBooking ticket = TicketBooking.builder().total_fare(fare).no_of_passenger(5).pid(pass).fid(flight).aid(airline).date(date).build();
		Integer i=(Integer)session.save(ticket);
		tx.commit();
		assertThat(i>0).isTrue();
	}
	

	@Test
	@Order(2)
	@DisplayName("Test for Fetch Ticket")
	void testGetAdminById()							// testing for get Ticket
	{
		TicketBookingDTO tdto=ticketService.getTicket(92298);
		assertThat(tdto.getNo_of_passenger()).isEqualTo((5));
		
	}

	
	@Test
	@Order(3)
	@DisplayName("Test for Cancel Booking")			
	void testcancelBooking()								//testing for cancel Booking
	{
		Airline airline=Airline.builder().airlineName("AirAsia").fare(2000).build();
		Transaction tx=session.beginTransaction();
		Flight flight = Flight.builder().airline(airline).avilableSeats(10).totalSeats(125).destination("delhi").source("pune").time("06:30").travellerClass("economy").date(LocalDate.of(2023,03,20)).build();
		List<Flight> flights=new ArrayList<Flight>();
		flights.add(flight);
		airline.setFlights(flights);
		flightService.saveFlight(flight);
		Passenger pass=Passenger.builder().name("A.Chakreborty.").phno("9876325005").email("aninadya999@gmail.com").password("ani123").userName("anindya@999").role("user").build();
		LocalDate date=LocalDate.now();
		session.save(pass);
		float fare = (float) 10000.00;
		TicketBooking ticket = TicketBooking.builder().total_fare(fare).no_of_passenger(5).pid(pass).fid(flight).aid(airline).date(date).build();
		tx.commit();
		ticketService.cancelBooking(ticket.getTicketId());
		
		assertThat(String.valueOf(flights.get(0).getTotalSeats())).isEqualTo(String.valueOf("125"));
	}
	
}
