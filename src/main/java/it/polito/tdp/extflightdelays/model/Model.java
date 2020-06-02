package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	Graph<Airport, DefaultWeightedEdge> grafo;
	Map<Airport, Airport> visita;

	public Graph<Airport, DefaultWeightedEdge> creaGrafo(int minimo) {
		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
		Map<Integer, Airport> airportMap = new HashMap<Integer, Airport>();
		for (Airport a : dao.loadAllAirports())
			airportMap.put(a.getId(), a);

		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

		for (Airport a : dao.loadGraphAirports(minimo))
			grafo.addVertex(airportMap.get(a.getId()));

		List<CoppieAirports> archi = dao.loadGraphFlights(minimo);
		for (CoppieAirports f : archi) {

			DefaultWeightedEdge edge = grafo.getEdge(airportMap.get(f.getAirportId1()),
					airportMap.get(f.getAirportId2()));

			if (edge != null)
				grafo.setEdgeWeight(edge, grafo.getEdgeWeight(edge) + f.getCnt());

			else
				Graphs.addEdge(grafo, airportMap.get(f.getAirportId1()), airportMap.get(f.getAirportId2()), f.getCnt());
		}

		return grafo;
	}

	public Map<Integer, Airport> getGraphVertices(int minimo) {
		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
		Map<Integer, Airport> airportMap = new HashMap<Integer, Airport>();
		for (Airport a : dao.loadGraphAirports(minimo))
			airportMap.put(a.getId(), a);

		return airportMap;

	}

	public List<Airport> getGraphVerticesList(int minimo) {
		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
		List<Airport> airportMap = dao.loadGraphAirports(minimo);

		return airportMap;

	}

	public List<Airport> trovaPercorso(Airport a1, Airport a2) {
		List<Airport> percorso = new ArrayList<Airport>();

		// reinizializzo la mappa della visita
		visita = new HashMap<>();

		BreadthFirstIterator<Airport, DefaultWeightedEdge> it = new BreadthFirstIterator<>(this.grafo, a1);

		// aggiungo la "radice" del mio albero di visita
		visita.put(a1, null);

		it.addTraversalListener(new TraversalListener<Airport, DefaultWeightedEdge>() {

			@Override
			public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void edgeTraversed(EdgeTraversalEvent<DefaultWeightedEdge> e) {
				Airport sorgente = grafo.getEdgeSource(e.getEdge());
				Airport destinazione = grafo.getEdgeTarget(e.getEdge());

				if (!visita.containsKey(destinazione) && visita.containsKey(sorgente))
					visita.put(destinazione, sorgente);
				else if (!visita.containsKey(sorgente) && visita.containsKey(destinazione)) {
					visita.put(sorgente, destinazione);
				}

			}

			@Override
			public void vertexTraversed(VertexTraversalEvent<Airport> e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void vertexFinished(VertexTraversalEvent<Airport> e) {
				// TODO Auto-generated method stub

			}

		});

		while (it.hasNext()) {
			it.next();
		}

		if (!visita.containsKey(a1) || !visita.containsKey(a2)) {
			// i due aeroporti non sono collegati
			return null;
		}

		Airport step = a2;

		while (!step.equals(a1)) {
			percorso.add(step);
			step = visita.get(step);
		}

		percorso.add(a1);

		return percorso;

	}

	public Airport sToAir(String s) {
		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();
		for (Airport a : dao.loadAllAirports())
			if (a.getAirportName().equals(s))
				return a;

		return null;
	}
}
