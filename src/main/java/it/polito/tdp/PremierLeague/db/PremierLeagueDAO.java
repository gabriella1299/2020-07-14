package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.Match;
import it.polito.tdp.PremierLeague.model.Player;
import it.polito.tdp.PremierLeague.model.Team;

public class PremierLeagueDAO {
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Team> listAllTeams(Map<Integer,Team> map){
		String sql = "SELECT * FROM Teams";
		List<Team> result = new ArrayList<Team>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(!map.containsKey(res.getInt("TeamID"))) {
					Team team = new Team(res.getInt("TeamID"), res.getString("Name"));
					result.add(team);
					map.put(res.getInt("TeamID"), team);
				}
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Match> listAllMatches(){
		String sql = "SELECT m.MatchID, m.TeamHomeID, m.TeamAwayID, m.teamHomeFormation, m.teamAwayFormation, m.resultOfTeamHome, m.date, t1.Name, t2.Name   "
				+ "FROM Matches m, Teams t1, Teams t2 "
				+ "WHERE m.TeamHomeID = t1.TeamID AND m.TeamAwayID = t2.TeamID";
		List<Match> result = new ArrayList<Match>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				
				Match match = new Match(res.getInt("m.MatchID"), res.getInt("m.TeamHomeID"), res.getInt("m.TeamAwayID"), res.getInt("m.teamHomeFormation"), 
							res.getInt("m.teamAwayFormation"),res.getInt("m.resultOfTeamHome"), res.getTimestamp("m.date").toLocalDateTime(), res.getString("t1.Name"),res.getString("t2.Name"));
				
				
				result.add(match);

			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void getPareggioFuori(Map<Integer,Team> map) {
		String sql="SELECT m.TeamAwayID, COUNT(m.ResultOfTeamHome) AS tot "
				+ "FROM matches m "
				+ "WHERE m.ResultOfTeamHome=0 "
				+ "GROUP BY m.TeamAwayID";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(map.containsKey(res.getInt("m.TeamAwayID"))) {
					Team t=map.get(res.getInt("m.TeamAwayID"));
					int pareggio=res.getInt("tot");
					t.setPareggioFuori(pareggio);
				}
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public void getPareggioDentro(Map<Integer,Team> map) {
		String sql="SELECT m.TeamHomeID, COUNT(m.ResultOfTeamHome) AS tot "
				+ "FROM matches m "
				+ "WHERE m.ResultOfTeamHome=0 "
				+ "GROUP BY m.TeamHomeID";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(map.containsKey(res.getInt("m.TeamHomeID"))) {
					Team t=map.get(res.getInt("m.TeamHomeID"));
					int pareggio=res.getInt("tot");
					t.setPareggioDentro(pareggio);
				}
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public void getVittorieFuori(Map<Integer,Team> map) {
		String sql="SELECT m.TeamAwayID, SUM(m.ResultOfTeamHome*(-3)) AS tot "
				+ "FROM matches m "
				+ "WHERE m.ResultOfTeamHome=-1 "
				+ "GROUP BY m.TeamAwayID";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(map.containsKey(res.getInt("m.TeamAwayID"))) {
					Team t=map.get(res.getInt("m.TeamAwayID"));
					int vittoria=res.getInt("tot");
					t.setVittoriaFuori(vittoria);
				}
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	public void getVittorieDentro(Map<Integer,Team> map) {
		String sql="SELECT m.TeamHomeID, SUM(m.ResultOfTeamHome*(3)) AS tot "
				+ "FROM matches m "
				+ "WHERE m.ResultOfTeamHome=1 "
				+ "GROUP BY m.TeamHomeID";
		
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(map.containsKey(res.getInt("m.TeamHomeID"))) {
					Team t=map.get(res.getInt("m.TeamHomeID"));
					int vittoria=res.getInt("tot");
					t.setVittoriaDentro(vittoria);
				}
			}
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		
	}
	
	
	
	
	
}
