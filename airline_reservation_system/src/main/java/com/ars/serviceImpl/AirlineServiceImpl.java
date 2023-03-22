package com.ars.serviceImpl;

import javax.persistence.PersistenceException;

import org.modelmapper.ModelMapper;

import com.ars.dao.AirlineDAO;
import com.ars.daoImpl.AirlineDAOImpl;
import com.ars.entity.Airline;
import com.ars.exception.GlobalException;
import com.ars.model.AirlineDTO;
import com.ars.service.AirlineService;

public class AirlineServiceImpl implements AirlineService{
AirlineDAO airlineDAO=new AirlineDAOImpl();
	
	//for save Airline
	@Override
	public void saveAirline(Airline airline) {
		airlineDAO.saveAirline(airline);
		
	}

	//for  assign Airline To Flight
	@Override
	public void assignAirlineToFlight(int flightId, int airId) {
		airlineDAO.assignAirlineToFlight(flightId, airId);
		
	}
	
	//for get Airline By Name
	@Override
	public AirlineDTO getAirlineByName(String name)throws GlobalException {
		Airline airline=airlineDAO.getAirlineByName(name);
		if(airline!=null) {
			return new ModelMapper().map(airline, AirlineDTO.class);
		}
		throw new GlobalException("Airline detalis not exsit!!");
	}

	//for update Airline By Id
	@Override
	public AirlineDTO updateAirlineById(int id, Airline airline) {
	Airline a= airlineDAO.updateAirlineById(id, airline);
		return new ModelMapper().map(a, AirlineDTO.class);
	}

	//for delete Airline by id
	@Override
	public void deleteAirline(int id) throws PersistenceException {
	airlineDAO.deleteAirline(id);
		
	}

}
