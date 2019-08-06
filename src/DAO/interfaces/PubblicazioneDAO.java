/* metodi contrassegnati con // sono quelli da forse aggiungere nelle altre interface */
/*c'è altra roba da aggiungere*/
package DAO.interfaces;
import java.util.*;
import model.*;
public interface PubblicazioneDAO {
	//Estrazione elenco delle ultime dieci pubblicazioni inserite
	public ArrayList<Pubblicazione> get_ultime_pubblicazioni();
	
	//Estrazione elenco delle pubblicazioni aggiornate di recente (ultimi 30 giorni)
	public ArrayList<Pubblicazione> get_update_recente();
	
	//Estrazione elenco delle pubblicazioni inserite da un utente
	public ArrayList<Pubblicazione> get_pubblicazione_utente(Utente utente);
	
	//Estrazione catalogo, cio� elenco di tutte le pubblicazioni con titolo, autori, editore e anno di pubblicazione, ordinato per titolo
	public ArrayList<Pubblicazione> get_catalogo();
	
	//Estrazione dati complete di una pubblicazione specifica dato il suo ID
	public Pubblicazione get_estrazione_dati(Pubblicazione pubblicazione);
	
	//Ricerca di pubblicazioni per ISBN, titolo, autore, e parole chiave
	public ArrayList<Pubblicazione> get_cerca_pubblicazione(Pubblicazione pubblicazione, Parola_chiave parola);
	
	//Estrazione della lista delle pubblicazioni in catalogo, ognuna con la data dell�ultima ristampa
	public ArrayList<Pubblicazione> get_catalogo_ristampa();
	
	//Data una pubblicazione, restituire tutte le pubblicazioni del catalogo aventi gli stessi autori
	public ArrayList<Pubblicazione> get_catalogo_stessi_autori(Pubblicazione pubblicazione);
	
	//Inserimento di un like relativo a una pubblicazione
	public boolean set_inserimento_like(Pubblicazione pubblicazione);
	
	//Calcolo numero dei like per una pubblicazione
	public Pubblicazione get_likes_totali(Pubblicazione pubblicazione);
	
	//Estrazione log delle modifiche effettuare su una pubblicazione
	public ArrayList<Storico> get_estrazione_modifiche_pubblicazione(Pubblicazione pubblicazione);
	
	//Estrazione elenco delle pubblicazioni per le quali � disponibile un download
	public ArrayList<Pubblicazione> get_elenco_download();
}
