package com.ars_vc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
import com.ars.entity.Passenger;
import com.ars.model.PassengerDTO;
import com.ars.service.PassengerService;
import com.ars.serviceImpl.PassengerServiceImpl;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PassengerTest {
	PassengerService passengerService = new PassengerServiceImpl();
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
	@DisplayName("Test Rigistration Passenger")
	void testRegisterAdmin()						//testing for Passenger registration
	{
		Transaction tx=session.beginTransaction();
		Passenger  pass= Passenger.builder().name("Anindya Chakreborty.").phno("9876325405").email("aninadya@gmail.com").password("ani@123").userName("anindya999").role("user").build();
		Integer i=(Integer)session.save(pass);
		tx.commit();
		assertThat(i>0).isTrue();
	}

	
	@Test
	@Order(2)
	@DisplayName("Test for Fetch a Passenger By Id")
	void testGetAdminById()							// testing for get Passenger
	{
		PassengerDTO pdto=passengerService.getPassengerById(13);
		assertThat(pdto.getName()).isEqualTo("Anindya Chakreborty.");
	}

	
	@Test
	@Order(3)
	@DisplayName("Test for Update Passenger By Id")
	void testUpdateAdminById()							//this method is use for update Passenger
	{
		Transaction tx=session.beginTransaction();
		Passenger ps=new Passenger();
		ps.setName("Anindya");
		PassengerDTO pdto=passengerService.updatePassenger(13, ps);
		tx.commit();
		assertThat(pdto.getName()).isEqualTo("Anindya");
	}

	
	@Test
	@Order(4)
	@DisplayName(value="Negetive Test Case")			
	void testDeleteAdmin()								//testing for delete Passenger
	{
		passengerService.deletePassenger(16);
		assertThrows(HibernateException.class,()->passengerService.deletePassenger(16));
	}

}
