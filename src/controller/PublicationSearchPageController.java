package controller;

import java.util.ArrayList;

import DAO.implementations.MySQLPubblicazioneDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Autore;
import model.Pubblicazione;

public class PublicationSearchPageController {
	@FXML
	private AnchorPane anchorpane = new AnchorPane();
	@FXML
	private ListView<Button> lista = new ListView<Button>();
	@FXML
	private SplitPane pane = new SplitPane();
	
	@FXML
	private void initialize(){
		AnchorPane.setTopAnchor(lista,0.0);
		AnchorPane.setBottomAnchor(lista,0.0);
		AnchorPane.setLeftAnchor(lista,0.0);
		AnchorPane.setRightAnchor(lista,0.0);
		settalista();
		
	}
	
	@FXML
	private void settatabella() {
		
		
		TableColumn<Pubblicazione, String> colonnaTitolo = new TableColumn<>("Titolo");
		colonnaTitolo.setMinWidth(200);
		colonnaTitolo.setCellValueFactory(new PropertyValueFactory<>("titolo"));
		
		TableColumn<Pubblicazione, ArrayList<Autore>> colonnaAutori = new TableColumn<>("Autori");
		colonnaAutori.setMinWidth(200);
		colonnaAutori.setCellValueFactory(new PropertyValueFactory<>("autori"));
		
		TableColumn<Pubblicazione, String> colonnaEditore = new TableColumn<>("Editore");
		colonnaEditore.setMinWidth(200);
		colonnaEditore.setCellValueFactory(new PropertyValueFactory<>("editore"));
		
		TableColumn<Pubblicazione, String> colonnaData = new TableColumn<>("Data di pubblicazione");
		colonnaData.setMinWidth(200);
		colonnaData.setCellValueFactory(new PropertyValueFactory<>("data"));
		
		
		
		//tabella.getColumns().addAll(colonnaTitolo , colonnaAutori, colonnaEditore, colonnaData);
		
		MySQLPubblicazioneDAOImpl dao = new MySQLPubblicazioneDAOImpl();
		
		ObservableList<Pubblicazione> lista = dao.get_catalogo();
		//tabella.setItems(lista);
		
		
	}
	
	@FXML
	private void settalista() {
		MySQLPubblicazioneDAOImpl dao = new MySQLPubblicazioneDAOImpl();
		ObservableList<Pubblicazione> list = dao.get_catalogo();
		for(Pubblicazione p : list) {
			Image icon = new Image(getClass().getResourceAsStream("/view/immagini/libro.png"));
            ImageView immagine = new ImageView(icon);
            immagine.setFitHeight(30);
            immagine.setPreserveRatio(true);
            Button b = new Button("", immagine);
            b.setPrefWidth(395);
            b.setPrefHeight(46);
            b.setAlignment(Pos.CENTER_LEFT);
            b.setTextFill(Color.web("#375fc6"));
            String autori = "Autori: ";
            int size = p.getAutori().size();
            for (int j = 0; j < size; j++) {
                if (j == p.getAutori().size() - 1)
                    autori = autori + p.getAutori().get(j).getNome() + " " + p.getAutori().get(j).getCognome();
                else {
                    autori = autori + p.getAutori().get(j).getNome() + " " + p.getAutori().get(j).getCognome() + ", ";
                }
            }
            b.setText("  " + p.getTitolo() + "\n  " + autori);
            b.setId("" + p.getId());
            b.setStyle("-fx-background-color: transparent;");
            System.out.println(p.getTitolo() + " " + p.getId());
            lista.getItems().add(b);
            b.setOnMouseEntered(e -> b.setStyle("-fx-background-color: #e8e8e8;"));
            b.setOnMouseExited(e -> b.setStyle("-fx-background-color: transparent;"));
            
            b.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                	b.setStyle("-fx-background-color: #bdbdbd;");
                    try {
                    	int idOpera;
                        idOpera = Integer.parseInt(b.getId());
                        ViewPublicationController.setId(idOpera);
                        FXMLLoader fxmlLoader = new FXMLLoader();
                        fxmlLoader.setLocation(getClass().getResource("/view/ViewPublicationPage.fxml"));
                        Scene scene = new Scene(fxmlLoader.load(), 525, 659);
                        Stage stage = new Stage();
                        stage.setTitle("Visualizza Opera");
                        stage.setScene(scene);
                        stage.show();

                        

                    } catch (Exception ex) {
                        ex.getStackTrace();
                    }

                }
            });
		}
	}
	}