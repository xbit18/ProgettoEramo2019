package DAO.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Pubblicazione;
import model.Utente;
import DAO.MySQLDAOFactory;
import DAO.interfaces.UtenteDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
 
// TODO: Auto-generated Javadoc
/**
 * Implementazione dell'interfaccia CustomerDAO per il database MySQL.
 */
public class MySQLUtenteDAOImpl implements UtenteDAO {
	
	/** The Constant modifica_tipo_utente. */
	/*basic query declaration */
    private static final String modifica_tipo_utente = "CALL modifica_tipo_utente(?,?)";
    
    /** The Constant modifica_livello_utente. */
    private static final String modifica_livello_utente = "CALL modifica_livello_utente(?,?)";
    
    /** The Constant utenti_attivi. */
    private static final String utenti_attivi = "CALL utenti_attivi()";
    
    /** The Constant mostra_email_utente. */
    private static final String	mostra_email_utente = "SELECT email from utente where id= ?";
    
    /** The Constant check_utente. */
    private static final String	check_utente = "SELECT id, email, passwd, livello, nome, cognome from utente where email = ? and passwd = ?";
    
    /** The Constant inserimento_utente. */
    private static final String inserimento_utente = "call inserimento_utente(?,?,?,?,?,?,?,?,?)";
    
    /** The Constant rimuovere_utente. */
    private static final String rimuovere_utente = "call rimuovere_utente(?)";
    
    /** The Constant utenti. */
    private static final String utenti = "SELECT id,email,nome,cognome,data_nascita,tipo,livello FROM utente ORDER BY nome";
    
    /** The Constant utente. */
    private static final String utente = "SELECT * FROM utente WHERE ID = ?";
	
    /**
	 * Modifica il tipo di un utente.
	 *
	 * @param utente the utente
	 * @param tipo the tipo
	 * @return true, if successful
	 */
    public boolean set_modifica_tipo_utente(Utente utente, String tipo) {
    	boolean fine = false;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(modifica_tipo_utente);
			ps.setString(1,utente.getEmail());
			ps.setString(2,tipo);
			ps.execute();
			fine=true;
		}
		catch(Exception exc) {
		System.out.print("something goes wrong!"); }
		finally { 
		try {
             ps.close();
         } catch (Exception sse) {
          	System.out.println("preparedStatement.close();"); 
         }
         try {
             conn.close();
         } catch (Exception cse) {
         	System.out.println("conn.close();");
         }  }
		return fine;
	}
    
    /**
	 * Modifica il livello di un utente 
	 *
	 * @param utente the utente
	 * @param livello the livello
	 * @return true, if successful
	 */
    public boolean set_modifica_livello_utente(Utente utente, String livello) {
    	boolean fine = false;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(modifica_livello_utente);
			ps.setString(1,utente.getEmail());
			ps.setString(2,livello);
			ps.execute();
			fine=true;
		}
		catch(Exception exc) {
		System.out.print("something goes wrong!"); }
		finally { 
		try {
             ps.close();
         } catch (Exception sse) {
          	System.out.println("preparedStatement.close();"); 
         }
         try {
             conn.close();
         } catch (Exception cse) {
         	System.out.println("conn.close();");
         }  }
		return fine;
	}
	
	
    /**
	 * Estrazione elenco degli utenti pi� �collaborativi� (cio� quelli che hanno inserito pi� pubblicazioni)..
	 *
	 * @return the utenti attivi
	 */
	public ObservableList<Utente> get_utenti_attivi() {
		ObservableList<Utente> utenti = FXCollections.observableArrayList();
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
        try {
        	conn = MySQLDAOFactory.createConnection();            
    		preparedStatement = conn.prepareStatement(utenti_attivi);
        	preparedStatement.execute();
            result = preparedStatement.getResultSet();
        	while (result.next()) {
        		Utente u = new Utente.Builder()
        				.withid(result.getInt(1))
        				.withnome(result.getString(2))
        				.withcognome(result.getString(3))
        				.withlibri(result.getInt(4))
        				.build() 
        				;
        		
        		utenti.add(u);
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
	return utenti;
	
	}

	
	/**
	 * Inserisce un utente.
	 *
	 * @param utente the utente
	 * @return true, if successful
	 */
	public boolean set_inserimento_utente(Utente utente) {
		boolean fine = false;
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn= MySQLDAOFactory.createConnection();          
			preparedStatement = conn.prepareStatement(inserimento_utente);
			preparedStatement.setString(1,utente.getEmail());
			preparedStatement.setString(2,utente.getPassword());
			preparedStatement.setString(3,utente.getNome());
			preparedStatement.setString(4,utente.getCognome());
			preparedStatement.setString(5,utente.getSesso());
			preparedStatement.setString(6,utente.getData_di_nascita());
			preparedStatement.setString(7,utente.getLuogo_di_nascita());
			preparedStatement.setString(8,utente.getLivello());
			preparedStatement.setString(9,utente.getPic());
			preparedStatement.execute();
			fine = true;
		}
		 catch (SQLException e) {
         	System.out.println(e.getMessage()); 
         } finally {
             
             try {
                 preparedStatement.close();
             } catch (Exception sse) {
              	System.out.println("preparedStatement.close();"); 
             }
             try {
                 conn.close();
             } catch (Exception cse) {
             	System.out.println("conn.close();");
             } 
             }return fine;}
	
	/**
	 * Rimuove un utente.
	 *
	 * @param utente the utente
	 * @return true, if successful
	 */
	public boolean set_rimuovere_utente(Utente utente) {
		boolean fine = false;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(rimuovere_utente);
			ps.setInt(1,utente.getId());
			ps.execute();
			fine=true;
		}
		catch(Exception exc) {
		System.out.print("somethink goes wrong!"); }
		finally { 
		try {
             ps.close();
         } catch (Exception sse) {
          	System.out.println("preparedStatement.close();"); 
         }
         try {
             conn.close();
         } catch (Exception cse) {
         	System.out.println("conn.close();");
         }  }
		return fine;
	}
	
	/**
	 * Controlla che l'utente esista e i dati inseriti siano giusti.
	 *
	 * @param email the email
	 * @param password the password
	 * @return true, if successful
	 */
	public Utente check_utente(String email, String password) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
        Utente utentelogin = new Utente.Builder()
				  .withmail("")
				  .build();
        try {
        	conn = MySQLDAOFactory.createConnection();            
    		preparedStatement = conn.prepareStatement(check_utente);
    		preparedStatement.setString(1,email);
    		preparedStatement.setString(2,password);
    		preparedStatement.execute();
    		result = preparedStatement.getResultSet();
    		  while (result.next()) {            	
    			  utentelogin = new Utente.Builder()
    					  .withid(result.getInt(1))
    					  .withmail(result.getString(2))
    					  .withpassword(result.getString(3))
    					  .withlivello(result.getString(4))
    					  .withnome(result.getString(5))
    					  .withcognome(result.getString(6))
    					  .build();
              }  
        }
        catch (SQLException e) {
        	System.out.println("qualcosa"); 
        } finally {
            try {
                result.close();
            } catch (Exception rse) {
             	System.out.println("result non chiude"); 
            }
            try {
                preparedStatement.close();
            } catch (Exception sse) {
             	System.out.println("preparedStatement.close();"); 
            }
            try {
                conn.close();
            } catch (Exception cse) {
            	System.out.println("conn.close();");
            }
        }
        return utentelogin;
		}


	/**
	 * Estrae tutti gli utenti del sistema
	 *
	 * @return true, if successful
	 */
	public ObservableList<Utente> get_utenti() {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
        ObservableList<Utente> listaUtenti = FXCollections.observableArrayList();
        try {
        	conn = MySQLDAOFactory.createConnection();            
    		preparedStatement = conn.prepareStatement(utenti);
    		preparedStatement.execute();
    		result = preparedStatement.getResultSet();
    		  while (result.next()) {            	
    			  listaUtenti.add(new Utente.Builder()
    					  .withid(result.getInt(1))
    					  .withmail(result.getString(2))
    					  .withnome(result.getString(3))
    					  .withcognome(result.getString(4))
    					  .withdata_nascita(result.getString(5))
    					  .withtipo(result.getString(6))
    					  .withlivello(result.getString(7))
    					  .build());
              }  
        }
        catch (SQLException e) {
        	System.out.println("qualcosa"); 
        } finally {
            try {
                result.close();
            } catch (Exception rse) {
             	System.out.println("result non chiude"); 
            }
            try {
                preparedStatement.close();
            } catch (Exception sse) {
             	System.out.println("preparedStatement.close();"); 
            }
            try {
                conn.close();
            } catch (Exception cse) {
            	System.out.println("conn.close();");
            }
        }
        return listaUtenti;
		}


	/**
	 * Estrae i dati di un utente.
	 *
	 * @param idUtente the idUtente
	 * @return the utente
	 */
	public Utente get_dati_utente(int idUtente) {
		Utente u = null;
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
        try {
        	conn = MySQLDAOFactory.createConnection();            
    		preparedStatement = conn.prepareStatement(utente);
    		preparedStatement.setInt(1, idUtente);
    		preparedStatement.execute();
    		result = preparedStatement.getResultSet();
    		  while (result.next()) { 
    			  u = new Utente.Builder()
    					  .withid(result.getInt(1))
    					  .withmail(result.getString(2))
    					  .withnome(result.getString(4))
    					  .withcognome(result.getString(5))
    					  .withsesso(result.getString(6))
    					  .withdata_nascita(result.getString(7))
    					  .withluogo_di_nascita(result.getString(8))
    					  .withtipo(result.getString(9))
    					  .withlivello(result.getString(10))
    					  .withpic(result.getString(11))
    					  .build();
    		  }  
        }
        catch (SQLException e) {
        	e.printStackTrace();
        } finally {
            try {
                result.close();
            } catch (Exception rse) {
             	System.out.println("result non chiude"); 
            }
            try {
                preparedStatement.close();
            } catch (Exception sse) {
             	System.out.println("preparedStatement.close();"); 
            }
            try {
                conn.close();
            } catch (Exception cse) {
            	System.out.println("conn.close();");
            }
        }
        return u;
		}
	}
	