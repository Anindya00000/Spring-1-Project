package com.ars.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.PersistenceException;

import org.modelmapper.ModelMapper;

import com.ars.dao.FlightDAO;
import com.ars.daoImpl.FlightDAOImpl;
import com.ars.entity.Flight;
import com.ars.exception.GlobalException;
import com.ars.model.FlightDTO;
import com.ars.service.FlightService;

public class FlightServiceImpl implements FlightService{
FlightDAO flightDAO=new FlightDAOImpl();
	
	// for save flight
	@Override
	public void saveFlight(Flight flight) {
		flightDAO.saveFlight(flight);
		
	}

	//for update Flight
	@Override
	public FlightDTO updateFlight(int id, Flight flight) {
		Flight f=flightDAO.updateFlight(id, flight);
		return new ModelMapper().map(f, FlightDTO.class);
	}

	//for get Flight by id
	@Override
	public FlightDTO getFlight(int id)throws GlobalException {
		Flight flight=flightDAO.getFlight(id);
		if(flight!=null)
		{
		return new ModelMapper().map(flight, FlightDTO.class);
		}
		throw new GlobalException("Flight detalis not exist");
		}
	
	// for delete Flight by id
	@Override
	public void deleteFlight(int id) throws PersistenceException {
	
		flightDAO.deleteFlight(id);
	}

	// for check available flight
	@Override
	public List<Flight> checkFlight(String from, String to, LocalDate date) {
		
		return flightDAO.checkFlight(from, to, date);
	}

}
