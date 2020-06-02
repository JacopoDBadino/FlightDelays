package it.polito.tdp.extflightdelays.db;

import it.polito.tdp.extflightdelays.model.Airport;

public class TestDAO {

	public static void main(String[] args) {

		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();

		for (Airport a : dao.loadGraphAirports(12))
			System.out.println(a.toString());

	}

}
