package DAO.implementations;

import java.util.*;
import java.sql.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

import DAO.MySQLDAOFactory;
import DAO.interfaces.PubblicazioneDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
public class MySQLPubblicazioneDAOImpl implements PubblicazioneDAO {
	private static String ultime_pubblicazioni = "CALL ultime_pubblicazioni";
	private static String update_recente = "CALL update_recente";
	private static String pubblicazione_utente = "CALL pubblicazioni_utente(?)";
	private static String catalogo = "CALL catalogo";
	private static String estrazione_dati = "CALL estrazione_dati(?)";
	private static String cerca = "CALL cerca_pubblicazione(?)";
	private static String catalogo_ristampa="CALL catalogo_ristampa";
	private static String inserimento_like="CALL inserimento_like"; // forse va nel utenteDAOImpl
	private static String estrazione_modifiche_pubblicazione = "CALL estrazione_modifiche_pubblicazione(?)";
	private static String likes_totali="CALL likes_totali";
	private static String elenco_download="CALL elenco_download";
	private static String storico_modifiche="SELECT * FROM storico WHERE descrizione = 'modifica' ";
	private static String inserimento_pubblicazione = "CALL inserimento_pubblicazione(?,?,?,?,?,?,?,?,?,?)";
	
	public ObservableList<Storico> get_storico_modifiche(){
		ObservableList<Storico> storico = FXCollections.observableArrayList();
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		try {
        	conn = MySQLDAOFactory.createConnection();            
    		ps = conn.prepareStatement(storico_modifiche);
    		ps.execute();
    		result = ps.getResultSet();
    		  while (result.next()) {            	
    		   		storico.add(new Storico(
    		   				result.getInt(1),
    		   				result.getInt(2),
    		   				result.getString(3),
    		   				result.getDate(4)));
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
                    ps.close();
                } catch (Exception sse) {
                 	System.out.println("preparedStatement.close();"); 
                }
                try {
                    conn.close();
                } catch (Exception cse) {
                	System.out.println("conn.close();");
                }
            }
		return storico;
	}
	
	@Override
	public ObservableList<Pubblicazione> get_ultime_pubblicazioni(){
		ObservableList<Pubblicazione> pubblicazioni = FXCollections.observableArrayList();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		try {
        	conn = MySQLDAOFactory.createConnection();            
    		ps = conn.prepareStatement(ultime_pubblicazioni);
    		ps.execute();
    		result = ps.getResultSet();
    		  while (result.next()) {            	
    		   		pubblicazioni.add(new Pubblicazione.Builder().withid(result.getInt(1)).withtitolo(result.getString(2)).withmetadati(new Metadati(result.getString(3),result.getString(4))).build());
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
                    ps.close();
                } catch (Exception sse) {
                 	System.out.println("preparedStatement.close();"); 
                }
                try {
                    conn.close();
                } catch (Exception cse) {
                	System.out.println("conn.close();");
                }
            }
	return pubblicazioni;}
	
	@Override
	public ObservableList<Pubblicazione> get_update_recente(){	
		ObservableList<Pubblicazione> pubblicazioni = FXCollections.observableArrayList();
	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet result = null;
	try {
    	conn = MySQLDAOFactory.createConnection();            
		ps = conn.prepareStatement(update_recente);
		ps.execute();
		result = ps.getResultSet();
		  while (result.next()) {            	
		   		pubblicazioni.add(new Pubblicazione.Builder()
		   				.withutente(result.getString(1))
		   				.withid(result.getInt(2))
		   				.withtitolo(result.getString(3))
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
                ps.close();
            } catch (Exception sse) {
             	System.out.println("preparedStatement.close();"); 
            }
            try {
                conn.close();
            } catch (Exception cse) {
            	System.out.println("conn.close();");
            }
        }
return pubblicazioni;}
	
	@Override
	public ObservableList<Pubblicazione> get_pubblicazione_utente(int idUtente){ 
		ObservableList<Pubblicazione> pubblicazioni = FXCollections.observableArrayList();
	PreparedStatement ps = null;
	ResultSet result = null;
	Connection conn = null;
	try {
		conn = MySQLDAOFactory.createConnection();
		ps = conn.prepareStatement(pubblicazione_utente);
		ps.setInt(1,idUtente);
		ps.execute();
		result = ps.getResultSet();
		  while (result.next()) {            	
		   		pubblicazioni.add(new Pubblicazione.Builder().withid(result.getInt(1)).withtitolo(result.getString(2)).build());
        }  
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
	return pubblicazioni;
}
	
	@Override
	public ObservableList<Pubblicazione> get_catalogo(){
		ObservableList<Pubblicazione> pubblicazioni = FXCollections.observableArrayList();
		
		PreparedStatement ps = null;
		ResultSet result = null;
		Connection conn = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(catalogo);
			ps.execute();
			result = ps.getResultSet();
			  while (result.next()) { 
				  ArrayList<Autore> autori = new ArrayList<Autore>();
				  autori.add(new Autore(result.getString(3),result.getString(4)));
				  pubblicazioni.add(new Pubblicazione.Builder().withid(result.getInt(1)).withtitolo(result.getString(2)).withautori(autori).witheditore(result.getString(5)).withdata(result.getString(6)).build());
	        }  
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
		return pubblicazioni;
	}

	@Override
	public Pubblicazione get_estrazione_dati(int id) {
		Pubblicazione pubbl = null;
		PreparedStatement ps = null;
		ResultSet result = null;
		Connection conn = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(estrazione_dati);
			ps.setInt(1,id);
			ps.execute();
			result = ps.getResultSet();
			  while (result.next()) {            	
				  ArrayList<Autore> autori = new ArrayList<Autore>();
				  autori.add(new Autore(result.getString(5),result.getString(6)));
				  Metadati meta = new Metadati(result.getInt(8),result.getString(7), result.getString(9), result.getString(10));
			   		pubbl = new Pubblicazione.Builder()
			   				.withid(result.getInt(1))
			   				.withtitolo(result.getString(2))
			   				.withdescrizione(result.getString(3))
			   				.witheditore(result.getString(4))
			   				.withmetadati(meta)
			   				.withutente(result.getString(11))
			   				.withautori(autori)
			   				.build();
	        }  
		}
		catch(Exception exc) {
		System.out.print("Qualcosa � andato storto!"); }
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
		return pubbl;
	}

	@Override
	public ObservableList<Pubblicazione> get_cerca_pubblicazione(String ricerca) {
		ObservableList<Pubblicazione> pubblicazioni = FXCollections.observableArrayList();
		PreparedStatement ps = null;
		ResultSet result = null;
		Connection conn = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(cerca);
			ps.setString(1,ricerca);
			ps.execute();
			result = ps.getResultSet();
			  while (result.next()) {     
				  	ArrayList<Autore> autori = new ArrayList<Autore>();
				  	autori.add(new Autore(result.getString(3),result.getString(4)));
			   		pubblicazioni.add(new Pubblicazione.Builder()
			   				.withid(result.getInt(1))
			   				.withtitolo(result.getString(2))
			   				.withautori(autori)
			   				.withmetadati(new Metadati(result.getString(5), ""))
			   				.build());
	        }  
		}
		catch(SQLException exc) {
		System.out.print(exc.getMessage()); }
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
		return pubblicazioni;
	}

	@Override
	public ObservableList<Pubblicazione> get_catalogo_ristampa() {
		ObservableList<Pubblicazione> pubblicazioni = FXCollections.observableArrayList();
		
		PreparedStatement ps = null;
		ResultSet result = null;
		Connection conn = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(catalogo_ristampa);
			ps.execute();
			result = ps.getResultSet();
			  while (result.next()) {
				  pubblicazioni.add(new Pubblicazione.Builder()
						  .withid(result.getInt(1))
						  .withtitolo(result.getString(2))
						  .withdescrizione(result.getString(3))
						  .witheditore(result.getString(4))
						  .withdata(result.getString(5))
						  .build());
	        }  
		}
		catch(Exception exc) {
		System.out.print("Qualcosa � andato storto!"); }
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
		return pubblicazioni;
	}
	
	@Override
	public ObservableList<Pubblicazione> get_catalogo_stessi_autori(Pubblicazione pubblicazione) {
		ObservableList<Pubblicazione> pubblicazioni = FXCollections.observableArrayList();
		PreparedStatement ps = null;
		ResultSet result = null;
		Connection conn = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(cerca);
			ps.setInt(1,pubblicazione.getId());
			ps.execute();
			result = ps.getResultSet();
			  while (result.next()) {     
			   		pubblicazioni.add(new Pubblicazione.Builder()
			   				.withid(result.getInt(1))
			   				.withtitolo(result.getString(2))
			   				.withdescrizione(result.getString(3))
			   				.witheditore(result.getString(4))
			   				.build());
	        }  
		}
		catch(SQLException exc) {
		System.out.print(exc.getMessage()); }
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
		return pubblicazioni;
	}
	
	@Override
	public boolean set_inserimento_like(Pubblicazione pubblicazione) {
		boolean fatto = false;
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(inserimento_like);
			ps.setString(1,LibraryUser.getEmail());
			ps.setString(2,LibraryUser.getPassword());
			ps.setInt(3,pubblicazione.getId());
			ps.execute();
			fatto = true;
		}
		catch(SQLException exc) {
		System.out.print(exc.getMessage()); }
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
		return fatto;
	}
	
	@Override
	public Pubblicazione get_likes_totali(Pubblicazione pubblicazione) {
		PreparedStatement ps = null;
		ResultSet result = null;
		Connection conn = null;
		Pubblicazione pubblicazioni = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(likes_totali);
			ps.setInt(1,pubblicazione.getId());
			ps.execute();
			result = ps.getResultSet();
			pubblicazioni = new Pubblicazione.Builder()
			   				.withid(result.getInt(1))
			   				.withtitolo(result.getString(2))
			   				.withlikes_totali(result.getInt(3))
			   				.build();    
		}
		catch(SQLException exc) {
		System.out.print(exc.getMessage()); }
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
		return pubblicazioni;
		
	}
	
	@Override
	public ArrayList<Storico> get_estrazione_modifiche_pubblicazione(Pubblicazione pubblicazione) {
		ArrayList<Storico> storico = new ArrayList<Storico>();
		PreparedStatement ps = null;
		ResultSet result = null;
		Connection conn = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(estrazione_modifiche_pubblicazione);
			ps.setInt(1,pubblicazione.getId());
			ps.execute();
			result = ps.getResultSet();
			  while (result.next()) {            	
				  storico.add(new Storico(
						  result.getInt(1),
						  result.getInt(2), 
						  result.getString(3),
						  result.getDate(4)));
	        }  
		}
		catch(Exception exc) {
		System.out.print("Qualcosa � andato storto!"); }
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
		return storico;
	}

	@Override
	public ObservableList<Pubblicazione> get_elenco_download() {
		ObservableList<Pubblicazione> pubblicazioni = FXCollections.observableArrayList();
		PreparedStatement ps = null;
		ResultSet result = null;
		Connection conn = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(elenco_download);
			ps.execute();
			result = ps.getResultSet();
			  while (result.next()) {            	
				  ArrayList<Sorgente> sorg = new ArrayList<Sorgente>();
				  sorg.add(new Sorgente(result.getString(4)));
			   		pubblicazioni.add(new Pubblicazione.Builder()
			   				.withtitolo(result.getString(1))
			   				.withdescrizione(result.getString(2))
			   				.witheditore(result.getString(3))
			   				.withsorgenti(sorg)
			   				.build());
	        }  
		}
		catch(Exception exc) {
		System.out.print("Qualcosa � andato storto!"); }
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
		return pubblicazioni;
	}
	
	public void inserimento_pubblicazione (Pubblicazione pubblicazione) {
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn= MySQLDAOFactory.createConnection();          
			preparedStatement = conn.prepareStatement(inserimento_pubblicazione);
			preparedStatement.setString(1,LibraryUser.getEmail());
			preparedStatement.setString(2,pubblicazione.getTitolo());
			preparedStatement.setString(3,pubblicazione.getDescrizione());
			preparedStatement.setString(4,pubblicazione.getEditore());
			preparedStatement.setString(5,pubblicazione.getMetadati().getIsbn());
			preparedStatement.setInt(6,pubblicazione.getMetadati().getNrpagine());
			preparedStatement.setString(7,pubblicazione.getMetadati().getLingua());
			preparedStatement.setString(8,pubblicazione.getData());
			preparedStatement.setString(9,pubblicazione.getAutori().get(0).getNome());
			preparedStatement.setString(10,pubblicazione.getAutori().get(0).getCognome());
			preparedStatement.execute();
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
             }
	}
}


