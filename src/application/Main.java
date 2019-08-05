package application;

import DAO.interfaces.UtenteDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

import DAO.MySQLDAOFactory;
import DAO.implementations.*;
import model.*;

public class Main extends Application{
	
	public static Stage stage;
	
	//metodo per avviare la GUI al momento disattivato
	@Override
	public void start(Stage stage) throws Exception {
	        this.stage = stage; // initialize value of stage.
	    Parent root = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
	    Scene scene = new Scene(root);
	    stage.setTitle("Benvenuto nella Biblioteca!");
	    
	    stage.setScene(scene);
	    stage.show();
	}
	
	public static void main(String[] args) {
	//launch(args);
	
	Utente utente = new Utente.Builder().withmail("ciao@cia.ex").withpassword("marks124").build(); // example of utente
	System.out.println(utente.toString()); 
	MySQLUtenteDAOImpl x = new MySQLUtenteDAOImpl();
	System.out.println(x.get_utenti_attivi()); 
	System.out.println(x.set_inserimento_utente(utente)); 
	System.out.println(x.set_rimuovere_utente(utente));
	System.out.println(x.get_mostra_nome_utente(6));  
	
	MySQLPubblicazioneDAOImpl y = new MySQLPubblicazioneDAOImpl();
	ArrayList<Autore> autore = new ArrayList<>();
	autore.add(new Autore("",""));
	Metadati meta = new Metadati("","");
	System.out.println(y.get_cerca_pubblicazione(new Pubblicazione.Builder().withtitolo("").withautori(autore).withmetadati(meta).build(), new Parola_chiave("bambini") ));
	
	
	System.out.println(y.get_pubblicazione_utente(new Utente.Builder().withmail("fulviolapenna@gmail.com").build()));
	
	
}
}