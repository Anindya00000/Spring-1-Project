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
import com.ars.entity.Admin;
import com.ars.model.AdminDTO;
import com.ars.service.AdminService;
import com.ars.serviceImpl.AdminServiceImpl;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminTest {
	AdminService adminService=new AdminServiceImpl();
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
	@DisplayName("Test Rigistration Admin")
	void testRegisterAdmin()						//testing for admin registration
	{
		Transaction tx=session.beginTransaction();
		Admin admin=Admin.builder().aName("Anindya Chakreborty.").email("aninadya@gmail.com").UserName("anindya999").password("ani@123").role("admin").build();
		Integer i=(Integer)session.save(admin);
		tx.commit();
		assertThat(i>0).isTrue();
	}

	
	@Test
	@Order(2)
	@DisplayName("Test for Fetch a Admin By Id")
	void testGetAdminById()							// testing for get Admin
	{
		AdminDTO adto=adminService.getAdminById(14);
		assertThat(adto.getAName()).isEqualTo("Anindya Chakreborty.");
	}

	
	@Test
	@Order(3)
	@DisplayName("Test for Update Admin By Id")
	void testUpdateAdminById()							//this method is use for update Admin
	{
		Transaction tx=session.beginTransaction();
		Admin ad=new Admin();
		ad.setAName("Anindya");
		AdminDTO adto=adminService.updateAdmin(14, ad);
		tx.commit();
		assertThat(adto.getAName()).isEqualTo("Anindya");
	}

	
	@Test
	@Order(4)
	@DisplayName(value="Negetive Test Case")			
	void testDeleteAdmin()								//testing for delete admin
	{
		adminService.deleteAdmin(16);
		assertThrows(HibernateException.class,()->adminService.getAdminById(16));
	}

}
