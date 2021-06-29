package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private Graph<Team,DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer,Team> map;
	
	public Model() {
		this.dao=new PremierLeagueDAO();
		
	}
	
	public void creaGrafo() {
		this.grafo=new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.map=new HashMap<>();
		List<Team> list=this.dao.listAllTeams(map);
		
		Graphs.addAllVertices(this.grafo, list);
		
		for(Team t1: list) {
			for(Team t2:list) {
				if(!t1.getTeamID().equals(t2.getTeamID())) {
					int p1=t1.getPareggioDentro()+t1.getPareggioFuori()+t1.getVittoriaDentro()+t1.getVittoriaFuori();
					int p2=t2.getPareggioDentro()+t2.getPareggioFuori()+t2.getVittoriaDentro()+t2.getVittoriaFuori();
					
						if(p1>p2) {
							if(this.grafo.getEdge(t1, t2)==null) {
								
								Graphs.addEdgeWithVertices(this.grafo, t1, t2, p1-p2);
							}
						}
						else if(p1<p2) {
							if(this.grafo.getEdge(t2, t1)==null) {
								Graphs.addEdgeWithVertices(this.grafo, t2, t1, p2-p1);
							}
						}
					
				}
			}
		}
		
	}

	public int getNVertici() {
		return this.grafo.vertexSet().size();
	}

	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
}
