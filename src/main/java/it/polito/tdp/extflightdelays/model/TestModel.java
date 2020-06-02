package it.polito.tdp.extflightdelays.model;

import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		Graph<Airport, DefaultWeightedEdge> grafo = model.creaGrafo(8);
		List<Airport> air = model.getGraphVerticesList(8);
		System.out.println(model.trovaPercorso(air.get(0), air.get(1)));

	}
}
