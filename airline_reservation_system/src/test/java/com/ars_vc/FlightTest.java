package com.ars_vc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.hibernate.HibernateException;
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
import com.ars.model.FlightDTO;
import com.ars.service.FlightService;
import com.ars.serviceImpl.FlightServiceImpl;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FlightTest {
	FlightService flightService=new FlightServiceImpl();
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
	@DisplayName("Test Rigistration Flight")
	void testRegisterFlight()						//testing for Flight registration
	{
		Airline airline=Airline.builder().airlineName("Air India").fare(2000).build();
		Transaction tx=session.beginTransaction();
		Flight flight = Flight.builder().airline(airline).avilableSeats(10).totalSeats(125).destination("delhi").source("pune").time("05:30").travellerClass("economy").date(LocalDate.of(2023,03,20)).build();
		Integer i=(Integer)session.save(flight);
		tx.commit();
		assertThat(i>0).isTrue();
	}

	
	@Test
	@Order(2)
	@DisplayName("Test for Fetch a Flight By Id")
	void testGetFlightById()							// testing for get Flight
	{
		FlightDTO fdto=flightService.getFlight(20);
		assertThat(fdto.getAirline().getAirlineName()).isEqualTo("Air India");
	}

	
	@Test
	@Order(3)
	@DisplayName("Test for Update Flight By Id")
	void testUpdateFlightById()							//this method is use for update Flight
	{
		Transaction tx=session.beginTransaction();
		Flight ft = new Flight();
		ft.setTravellerClass("business");
		FlightDTO fdto=flightService.updateFlight(20, ft);
		tx.commit();
		assertThat(fdto.getTravellerClass()).isEqualTo("business");
	}

	
	@Test
	@Order(4)
	@DisplayName(value="Negetive Test Case")			
	void testDeleteFlight()								//testing for delete Flight
	{
		flightService.deleteFlight(16);
		assertThrows(HibernateException.class,()->flightService.getFlight(16));
		//assertThrows(GlobalException.class,()->adminService.getAdminById(16));
	}

}
