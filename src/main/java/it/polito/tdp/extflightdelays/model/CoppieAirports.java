package it.polito.tdp.extflightdelays.model;

public class CoppieAirports {
	int airportId1;
	int airportId2;
	int cnt;
	public CoppieAirports(int airportId1, int airportId2, int cnt) {
		super();
		this.airportId1 = airportId1;
		this.airportId2 = airportId2;
		this.cnt = cnt;
	}
	public int getAirportId1() {
		return airportId1;
	}
	public int getAirportId2() {
		return airportId2;
	}
	public int getCnt() {
		return cnt;
	}
	
	
}
