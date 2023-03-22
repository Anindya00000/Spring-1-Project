package com.ars.serviceImpl;

import javax.persistence.PersistenceException;

import org.modelmapper.ModelMapper;

import com.ars.dao.PassengerDao;
import com.ars.daoImpl.PassengerDaoImpl;
import com.ars.entity.Passenger;
import com.ars.exception.GlobalException;
import com.ars.model.PassengerDTO;
import com.ars.service.PassengerService;

public class PassengerServiceImpl implements PassengerService{
PassengerDao pdao=new PassengerDaoImpl();
	
	//for save passenger
	@Override
	public void savePassenger(Passenger passenger) {
		pdao.savePassenger(passenger);
		
	}
	
	// for passenger login
	@Override
	public boolean login(String userName, String password) {
		
		return pdao.login(userName, password);
	}

	//for get Passenger By Id
	@Override
	public PassengerDTO getPassengerById(int id) throws GlobalException {
		Passenger  passenger1=pdao.getPassenger(id);
		
		return new ModelMapper().map(passenger1, 
				PassengerDTO.class);
	}

	//for  update Passenger
	@Override
	public PassengerDTO updatePassenger(int id, Passenger passenger) {
		Passenger p=pdao.updatePassenger(id, passenger);
		return new ModelMapper().map(p, PassengerDTO.class);
	}

	//for delete Passenger by id
	@Override
	public void deletePassenger(int id) throws PersistenceException {
		pdao.deletePassenger(id);
		
		
	}
	

}
