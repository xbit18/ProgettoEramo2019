package controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import DAO.implementations.MySQLPubblicazioneDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.text.*;
import model.*;

// TODO: Auto-generated Javadoc
/**
 * The Class InsertPublicationController.
 */
public class InsertPublicationController {
	
	/** The testo pagina. */
	@FXML private Text testoPagina;
	
	/** The conferma. */
	@FXML private Button conferma;
	
	/** The titolo. */
	@FXML private TextField titolo;
	
	/** The editore. */
	@FXML private TextField editore;
	
	/** The isbn. */
	@FXML private TextField isbn;
	
	/** The nrpagine. */
	@FXML private TextField nrpagine;
	
	/** The cognome autore. */
	@FXML private TextField cognomeAutore;
	
	/** The nome autore. */
	@FXML private TextField nomeAutore;
	
	/** The descrizione. */
	@FXML private TextArea descrizione;
	
	/** The datapubblicazione. */
	@FXML private DatePicker datapubblicazione;
	
	/** The lingua. */
	@FXML private ComboBox<String> lingua;
	
	/** The lista lingue. */
	private List<String> listaLingue;
	
	/** The max chars. */
	final int MAX_CHARS = 500 ;
	
	/** The file lingue. */
	String fileLingue = "src/view/file_di_testo/lingue.txt";
	
	/** The dao. */
	MySQLPubblicazioneDAOImpl dao = new MySQLPubblicazioneDAOImpl();
	
	/**
	 * Initialize.
	 */
	@FXML
	public void initialize() {
	//Limito i caratteri da poter inserire nella TextArea
		descrizione.setTextFormatter(new TextFormatter<String>(change -> 
        	change.getControlNewText().length() <= MAX_CHARS ? change : null));
		
	//Popolo la ComboBox di lingue da un file .txt
		try {
			listaLingue = Files.lines(Paths.get(fileLingue)).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		lingua.setItems(FXCollections.observableArrayList(listaLingue));
	}
	
	/**
	 * Handle conferma button.
	 */
	@FXML
	private void handleConfermaButton() {
		if(check_inserimento()) {
		Autore autore = new Autore(
				nomeAutore.getText(), 
				cognomeAutore.getText()
				);
		
		ArrayList<Autore> autori = new ArrayList<Autore>();
		autori.add(autore);
		
		Metadati metadati = new Metadati(
				Integer.parseInt(nrpagine.getText()), 
				isbn.getText(), 
				lingua.getValue(), 
				datapubblicazione.getValue().toString()
				);
		
		Pubblicazione newPubbl = new Pubblicazione.Builder()
				.withtitolo(titolo.getText())
				.withdescrizione(descrizione.getText())
				.witheditore(editore.getText())
				.withautori(autori)
				.withmetadati(metadati)
				.build();		
				
		dao.inserimento_pubblicazione(newPubbl);		
		} else {
		//c'� stato un errore nel metodo check
		}
	}
	
	/**
	 * Check inserimento.
	 *
	 * @return true, if successful
	 */
	private boolean check_inserimento() { //metodo che controlla tutti i dati inseriti nel form
		boolean all_ok = true;
		
		if(titolo.getText().isBlank()) {
			titolo.setPromptText("Campo obbligatorio");
			all_ok = false;
		}
		
		if(descrizione.getText().isBlank()) {
			descrizione.setPromptText("Campo obbligatorio");
			all_ok = false;
		}
		
		if(descrizione.getText().length() > 500) {
			descrizione.setText(descrizione.getText() + "\n Massimo 500 caratteri");
			all_ok = false;
		}
		
		if(editore.getText().isBlank()) {
			editore.setPromptText("Campo obbligatorio");
			all_ok = false;
		}
		
		if (!(isbn.getText().matches("^[0-9]+$")) || isbn.getText().length() != 13 ) {
			isbn.clear();
			isbn.setPromptText("13 cifre - no lettere o caratteri speciali");
			all_ok = false;
			}
		
		
		
		if (nrpagine.getText().isBlank()) {
			nrpagine.setPromptText("Campo obbligatorio");
			all_ok = false;
		}
		
		if (nomeAutore.getText().isBlank()) {
			nomeAutore.setPromptText("Campo obbligatorio");
			all_ok = false;
		}
		
		if(cognomeAutore.getText().isBlank()) {
			cognomeAutore.setPromptText("Campo obbligatorio");
			all_ok = false;
		}
		
		if(datapubblicazione.getValue() == null) {
			datapubblicazione.setPromptText("Campo obbligatorio");
			all_ok = false;
		}
		
		if(lingua.getValue() == null) {
			lingua.setPromptText("Campo obbligatorio");
			all_ok = false;
		}
		
		return all_ok;
	}
}

   	
