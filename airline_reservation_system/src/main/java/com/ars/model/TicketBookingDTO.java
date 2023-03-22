package com.ars.model;

import java.time.LocalDate;

import com.ars.entity.Airline;
import com.ars.entity.Flight;
import com.ars.entity.Passenger;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TicketBookingDTO {
	
	private int ticketId;
	private int no_of_passenger;
	private double totalFare;
	private LocalDate date;
	private Flight fid;
	private Airline aid;
	private Passenger pid;

}
