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
// TODO: Auto-generated Javadoc

/**
 * The Class MySQLPubblicazioneDAOImpl.
 */
public class MySQLPubblicazioneDAOImpl implements PubblicazioneDAO {
	
	/** The ultime pubblicazioni. */
	private static String ultime_pubblicazioni = "CALL ultime_pubblicazioni";
	
	/** The update recente. */
	private static String update_recente = "CALL update_recente";
	
	/** The pubblicazione utente. */
	private static String pubblicazione_utente = "CALL pubblicazioni_utente(?)";
	
	/** The catalogo. */
	private static String catalogo = "CALL catalogo()";
	
	/** The estrazione dati. */
	private static String estrazione_dati = "CALL estrazione_dati(?)";
	
	/** The cerca. */
	private static String cerca = "CALL cerca_pubblicazione(?)";
	
	/** The catalogo ristampa. */
	private static String catalogo_ristampa="CALL catalogo_ristampa";
	
	/** The catalogo stessi autori. */
	private static String catalogo_stessi_autori="CALL catalogo_stessi_autori(?)";
	
	/** The inserimento like. */
	private static String inserimento_like="CALL inserimento_like(?,?,?)";
	
	/** The estrazione modifiche pubblicazione. */
	private static String estrazione_modifiche_pubblicazione = "CALL estrazione_modifiche_pubblicazione(?)";
	
	/** The like utente pubblicazione. */
	private static String like_utente_pubblicazione = "SELECT * FROM likes WHERE ID_utente = ? AND ID_pubblicazione = ?";
	
	/** The likes totali. */
	private static String likes_totali="CALL likes_totali(?)";
	
	/** The elenco download. */
	private static String elenco_download="CALL elenco_download";
	
	/** The storico modifiche. */
	private static String storico_modifiche="SELECT * FROM storico WHERE descrizione <> 'inserimento' ";
	
	/** The inserimento pubblicazione. */
	private static String inserimento_pubblicazione = "CALL inserimento_pubblicazione(?,?,?,?,?,?,?,?,?,?)";
	
	/** The inserimento capitolo. */
	private static String inserimento_capitolo = "INSERT INTO capitolo (ID_pubblicazione,numero,titolo) VALUES (?,?,?)";
	
	/** The inserimento sorgente. */
	private static String inserimento_sorgente = "CALL inserimento_sorgente (?,?,?,?,?)";
	
	/** The inserimento ristampa. */
	private static String inserimento_ristampa = "CALL inserimento_ristampa (?,?,?)";
	
	/** The inserimento parola chiave. */
	private static String inserimento_parola_chiave = "CALL inserimento_parola_chiave (?,?)";
	
	/** The inserimento autore. */
	private static String inserimento_autore = "CALL inserimento_autore (?,?,?)";
	
	/** The get sorgenti pubbl. */
	private static String get_sorgenti_pubbl = "SELECT * FROM sorgente WHERE ID_pubblicazione = ?";
	
	/** The get capitoli pubbl. */
	private static String get_capitoli_pubbl = "SELECT * FROM capitolo WHERE ID_pubblicazione = ?";
	
	/** The modifica pubblicazione storico. */
	private static String modifica_pubblicazione_storico = "CALL modifica_pubblicazione_storico (?,?,?)";
	
	/**
	 * Gets the storico modifiche.
	 *
	 * @return the storico modifiche
	 */
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
	
	/**
	 * Gets the ultime pubblicazioni.
	 *
	 * @return the ultime pubblicazioni
	 */
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
	
	/**
	 * Gets the update recente.
	 *
	 * @return the update recente
	 */
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
	
	/**
	 * Gets the pubblicazione utente.
	 *
	 * @param idUtente the id utente
	 * @return the pubblicazione utente
	 */
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
	
	/**
	 * Gets the catalogo.
	 *
	 * @return the catalogo
	 */
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

	/**
	 * Gets the estrazione dati.
	 *
	 * @param id the id
	 * @return the estrazione dati
	 */
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

	/**
	 * Gets the cerca pubblicazione.
	 *
	 * @param ricerca the ricerca
	 * @return the cerca pubblicazione
	 */
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

	/**
	 * Gets the catalogo ristampa.
	 *
	 * @return the catalogo ristampa
	 */
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
	
	/**
	 * Gets the catalogo stessi autori.
	 *
	 * @param pubblicazione the pubblicazione
	 * @return the catalogo stessi autori
	 */
	@Override
	public ObservableList<Pubblicazione> get_catalogo_stessi_autori(Pubblicazione pubblicazione) {
		ObservableList<Pubblicazione> pubblicazioni = FXCollections.observableArrayList();
		PreparedStatement ps = null;
		ResultSet result = null;
		Connection conn = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(catalogo_stessi_autori);
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
	
	/**
	 * Sets the inserimento like.
	 *
	 * @param pubblicazione the pubblicazione
	 * @return true, if successful
	 */
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
	
	/**
	 * Gets the likes totali.
	 *
	 * @param pubblicazione the pubblicazione
	 * @return the likes totali
	 */
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
			while(result.next()) {
			pubblicazioni = new Pubblicazione.Builder()
			   				.withlikes_totali(result.getInt(3))
			   				.build();    
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
	
	/**
	 * Gets the estrazione modifiche pubblicazione.
	 *
	 * @param pubblicazione the pubblicazione
	 * @return the estrazione modifiche pubblicazione
	 */
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

	/**
	 * Gets the elenco download.
	 *
	 * @return the elenco download
	 */
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
	
	/**
	 * Inserimento pubblicazione.
	 *
	 * @param pubblicazione the pubblicazione
	 */
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

	/**
	 * Inserimento capitolo.
	 *
	 * @param capitolo the capitolo
	 * @param pubblicazione the pubblicazione
	 * @return true, if successful
	 */
	public boolean inserimento_capitolo (Capitolo capitolo, Pubblicazione pubblicazione) {
		boolean tutto_ok = true;
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn= MySQLDAOFactory.createConnection();          
			preparedStatement = conn.prepareStatement(inserimento_capitolo);
			preparedStatement.setInt(1,pubblicazione.getId());
			preparedStatement.setInt(2,capitolo.getNumero());
			preparedStatement.setString(3,capitolo.getTitolo());
			preparedStatement.execute();
		}
		 catch (SQLException e) {
			 tutto_ok = false;
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
		return tutto_ok;
	}
	
	/**
	 * Inserimento sorgente.
	 *
	 * @param sorgente the sorgente
	 * @param pubblicazione the pubblicazione
	 * @return true, if successful
	 */
	public boolean inserimento_sorgente (Sorgente sorgente, Pubblicazione pubblicazione) {
		boolean tutto_ok = true;
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn= MySQLDAOFactory.createConnection();          
			preparedStatement = conn.prepareStatement(inserimento_sorgente);
			preparedStatement.setInt(1,pubblicazione.getId());
			preparedStatement.setString(2,sorgente.getURI());
			preparedStatement.setString(2,sorgente.getTipo());
			preparedStatement.setString(2,sorgente.getFormato());
			preparedStatement.setString(2,sorgente.getDescrizione());;
			preparedStatement.execute();
		}
		 catch (SQLException e) {
			 tutto_ok = false;
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
		return tutto_ok;
	}

	
	/**
	 * Inserimento ristampa.
	 *
	 * @param ristampa the ristampa
	 * @param pubblicazione the pubblicazione
	 * @return true, if successful
	 */
	public boolean inserimento_ristampa (Ristampa ristampa, Pubblicazione pubblicazione) {
		boolean tutto_ok = true;
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn= MySQLDAOFactory.createConnection();          
			preparedStatement = conn.prepareStatement(inserimento_ristampa);
			preparedStatement.setInt(1,pubblicazione.getId());
			preparedStatement.setString(2,ristampa.getData());
			preparedStatement.setInt(3,ristampa.getNumero());
			preparedStatement.execute();
		}
		 catch (SQLException e) {
			 tutto_ok = false;
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
		return tutto_ok;
	}

	/**
	 * Inserimento parola chiave.
	 *
	 * @param parola the parola
	 * @param pubblicazione the pubblicazione
	 * @return true, if successful
	 */
	public boolean inserimento_parola_chiave (Parola_chiave parola, Pubblicazione pubblicazione) {
		boolean tutto_ok = true;
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn= MySQLDAOFactory.createConnection();          
			preparedStatement = conn.prepareStatement(inserimento_parola_chiave);
			preparedStatement.setInt(1,pubblicazione.getId());
			preparedStatement.setString(2,parola.getParola());
			preparedStatement.execute();
		}
		 catch (SQLException e) {
			 tutto_ok = false;
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
		return tutto_ok;
	}
	
	/**
	 * Inserimento autore.
	 *
	 * @param autore the autore
	 * @param pubblicazione the pubblicazione
	 * @return true, if successful
	 */
	public boolean inserimento_autore (Autore autore, Pubblicazione pubblicazione) {
		boolean tutto_ok = true;
		Connection conn = null;
		PreparedStatement preparedStatement = null;
		try {
			conn= MySQLDAOFactory.createConnection();          
			preparedStatement = conn.prepareStatement(inserimento_autore);
			preparedStatement.setInt(1,pubblicazione.getId());
			preparedStatement.setString(2,autore.getNome());
			preparedStatement.setString(3,autore.getCognome());
			preparedStatement.execute();
		}
		 catch (SQLException e) {
			 tutto_ok = false;
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
		return tutto_ok;
	}
	
	/**
	 * Check like.
	 *
	 * @param pubblicazione the pubblicazione
	 * @return true, if successful
	 */
	public boolean check_like(Pubblicazione pubblicazione) {
		boolean esiste = false;
		PreparedStatement ps = null;
		ResultSet result = null;
		Connection conn = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(like_utente_pubblicazione);
			ps.setInt(1,LibraryUser.getId());
			ps.setInt(2, pubblicazione.getId());
			ps.execute();
			result = ps.getResultSet();
			  while (result.next()) {            	
				  esiste = true;
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
		return esiste;
	}
	
	/**
	 * Gets the sorgenti pubbl.
	 *
	 * @param pubblicazione the pubblicazione
	 * @return the sorgenti pubbl
	 */
	public ObservableList<Sorgente> get_sorgenti_pubbl(Pubblicazione pubblicazione){
		ObservableList<Sorgente> sorgenti = FXCollections.observableArrayList();
		PreparedStatement ps = null;
		ResultSet result = null;
		Connection conn = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(get_sorgenti_pubbl);
			ps.setInt(1,pubblicazione.getId());
			ps.execute();
			result = ps.getResultSet();
			  while (result.next()) {            	
				  sorgenti.add(new Sorgente(result.getString(3), result.getString(4), result.getString(5), result.getString(6)));
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
		return sorgenti;
	}

	/**
	 * Gets the capitoli pubbl.
	 *
	 * @param pubbl the pubbl
	 * @return the capitoli pubbl
	 */
	public ObservableList<Capitolo> get_capitoli_pubbl(Pubblicazione pubbl) {
		ObservableList<Capitolo> capitoli = FXCollections.observableArrayList();
		PreparedStatement ps = null;
		ResultSet result = null;
		Connection conn = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(get_capitoli_pubbl);
			ps.setInt(1,pubbl.getId());
			ps.execute();
			result = ps.getResultSet();
			  while (result.next()) {            	
				  capitoli.add(new Capitolo(result.getInt(3), result.getString(4)));
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
		return capitoli;
	}
	
	/**
	 * Modifica pubblicazione storico.
	 *
	 * @param pubblicazione the pubblicazione
	 * @param parametro the parametro
	 */
	public void modifica_pubblicazione_storico(Pubblicazione pubblicazione, String parametro) {
		PreparedStatement ps = null;
		ResultSet result = null;
		Connection conn = null;
		try {
			conn = MySQLDAOFactory.createConnection();
			ps = conn.prepareStatement(modifica_pubblicazione_storico);
			ps.setInt(1,LibraryUser.getId());
			ps.setInt(2, pubblicazione.getId());
			ps.setString(3,parametro);
			ps.execute();
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
	}
	
}
	



