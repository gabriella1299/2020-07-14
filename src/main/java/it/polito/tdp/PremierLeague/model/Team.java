package it.polito.tdp.PremierLeague.model;

public class Team {
	private Integer teamID;
	private String name;
	private int pareggioFuori;
	private int pareggioDentro;
	private int vittoriaFuori;
	private int vittoriaDentro;
	
	public Team(Integer teamID, String name) {
		super();
		this.teamID = teamID;
		this.name = name;
	}
	
	public Integer getTeamID() {
		return teamID;
	}
	public void setTeamID(Integer teamID) {
		this.teamID = teamID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((teamID == null) ? 0 : teamID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Team other = (Team) obj;
		if (teamID == null) {
			if (other.teamID != null)
				return false;
		} else if (!teamID.equals(other.teamID))
			return false;
		return true;
	}

	public int getPareggioFuori() {
		return pareggioFuori;
	}

	public void setPareggioFuori(int pareggioFuori) {
		this.pareggioFuori = pareggioFuori;
	}

	public int getPareggioDentro() {
		return pareggioDentro;
	}

	public void setPareggioDentro(int pareggioDentro) {
		this.pareggioDentro = pareggioDentro;
	}

	public int getVittoriaFuori() {
		return vittoriaFuori;
	}

	public void setVittoriaFuori(int vittoriaFuori) {
		this.vittoriaFuori = vittoriaFuori;
	}

	public int getVittoriaDentro() {
		return vittoriaDentro;
	}

	public void setVittoriaDentro(int vittoriaDentro) {
		this.vittoriaDentro = vittoriaDentro;
	}

	
	
}
