package com.ars_vc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.ars.config.HibernateUtil;
import com.ars.dao.AirlineDAO;
import com.ars.daoImpl.AirlineDAOImpl;
import com.ars.entity.Airline;
import com.ars.entity.Flight;
import com.ars.service.AirlineService;
import com.ars.service.FlightService;
import com.ars.serviceImpl.AirlineServiceImpl;
import com.ars.serviceImpl.FlightServiceImpl;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AirlineTest {
FlightService flightService=new FlightServiceImpl();
AirlineService airlineService=new AirlineServiceImpl();
AirlineDAO airDao=new AirlineDAOImpl();
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
@DisplayName("Test for Airline")
void oneTomanyRelationshipTest()  // testing for one to many relatioship
{
	Airline airline=Airline.builder().airlineName("Air India").fare(2000).build();
	
	Flight flight1=Flight.builder().airline(airline).avilableSeats(10).totalSeats(125).destination("delhi").source("pune").time("05:30").travellerClass("economy").date(LocalDate.of(2023, 03,20)).build();
	Flight flight2=Flight.builder().airline(airline).avilableSeats(10).totalSeats(120).destination("bangalore").source("mumbai").time("05:30").travellerClass("economy").date(LocalDate.of(2023, 03,22)).build();
	List<Flight> flights=new ArrayList<Flight>();
	flights.add(flight1);
	flights.add(flight2);
	airline.setFlights(flights);
	flightService.saveFlight(flight1);
	flightService.saveFlight(flight2);
	assertThat(flight1.getAirline()).isEqualTo(airline);
	assertThat(flight2.getAirline()).isEqualTo(airline);
	
	assertThat(airline.getFlights().get(0).getFlight_id()).isEqualTo(flight1.getFlight_id());
	
	assertThat(airline.getFlights().get(1).getFlight_id()).isEqualTo(flight2.getFlight_id());


	
	}
	

}
