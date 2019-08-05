package DAO.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import model.*;
import DAO.MySQLDAOFactory;
import DAO.interfaces.RecensioneDAO;

public class MySQLRecensioneDAOImpl implements RecensioneDAO {

	 	private static final String elenco_recensioni = "CALL elenco_recensioni(?)";
	    private static final String elenco_recensioni_attesa = "CALL elenco_recensioni_attesa()";
	    private static final String	inserimento_recensione = "CALL inserimento_recensione(";
	    private static final String rimuovere_recensione = "call inserimento_utente(?,?,?,?,?,?,?,?)";
	    private static final String verifica_recensione = "call rimuovere_utente(?,?)";
		
	    @Override
		public ArrayList<Recensione> get_elenco_recensioni(Pubblicazione pubblicazione) {
	    	ArrayList<Recensione> recensioni = new ArrayList<>();
	    	Connection conn = null;
			PreparedStatement preparedStatement = null;
			ResultSet result = null;
	        try {
	        	conn = MySQLDAOFactory.createConnection();            
	    		preparedStatement = conn.prepareStatement(elenco_recensioni);
	    		preparedStatement.setInt(1,pubblicazione.getId());
	        	preparedStatement.execute();
	            result = preparedStatement.getResultSet();
	        	while (result.next()) {            
	        		recensioni.add(new Recensione(result.getString("email"), result.getString("contenuto"), result.getString("approvazione"), result.getDate("data")));
		}
	        }
	            catch (SQLException e) {
	                e.printStackTrace();
	            } finally {
	                try {
	                    result.close();
	                } catch (Exception rse) {
	                    rse.printStackTrace();
	                }
	                try {
	                    preparedStatement.close();
	                } catch (Exception sse) {
	                    sse.printStackTrace();
	                }
	                try {
	                    conn.close();
	                } catch (Exception cse) {
	                    cse.printStackTrace();
	                }
	            }
		return recensioni;
		
		}
		
		@Override
		public ArrayList<Recensione> get_elenco_recensioni_attesa() {
			ArrayList<Recensione> recensioni_attesa = new ArrayList<>();
	    	Connection conn = null;
			PreparedStatement preparedStatement = null;
			ResultSet result = null;
	        try {
	        	conn = MySQLDAOFactory.createConnection();            
	    		preparedStatement = conn.prepareStatement(elenco_recensioni_attesa);
	        	preparedStatement.execute();
	            result = preparedStatement.getResultSet();
	        	while (result.next()) {            
	        		recensioni_attesa.add(new Recensione(result.getString("titolo"), result.getString("email"), result.getString("contenuto"), result.getString("approvazione"), result.getDate("data")));
		}
	        }
	            catch (SQLException e) {
	                e.printStackTrace();
	            } finally {
	                try {
	                    result.close();
	                } catch (Exception rse) {
	                    rse.printStackTrace();
	                }
	                try {
	                    preparedStatement.close();
	                } catch (Exception sse) {
	                    sse.printStackTrace();
	                }
	                try {
	                    conn.close();
	                } catch (Exception cse) {
	                    cse.printStackTrace();
	                }
	            }
		return recensioni_attesa;
		
		}
		@Override
		public boolean set_inserimento_recensione(Recensione recensione) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean set_verifica_recensione(Utente utente, int ID_pubblicazione, String giudizio) {
			// TODO Auto-generated method stub
			return false;
		}
		@Override
		public boolean set_rimuovere_recensione(Recensione recensione) {
			// TODO Auto-generated method stub
			return false;
		}
	
}
