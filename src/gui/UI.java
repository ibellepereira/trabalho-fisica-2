package gui;

import fisica.Fis;
import fisica.Ponto;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public final class UI {
	protected static Node raiz;
	protected static Node raizCanvas;
	

	public static GridPane infoUI(){
		GridPane grid = new GridPane();
		grid.setId("infoPane");
		grid.setVgap(10);
		grid.setHgap(10);
	
		Label q1 = new Label("Carga (C)");
		TextField valorQ1 = new TextField();
		valorQ1.setPrefWidth(50);
		valorQ1.setId("q1");
		valorQ1.setEditable(false);
		valorQ1.textProperty().addListener((observable, oldValue, newValue) -> {
		    if(FisUI.cargas.size() > 0 ){
		    	valorQ1.setEditable(true);
		    	FisUI.cargas.get(0).alteraCarga(Double.parseDouble(newValue));		    	
		    }else{valorQ1.setEditable(false);}
		});
		
		Label q2 = new Label("Carga' (C)");
		TextField valorQ2 = new TextField();
		valorQ2.setId("q2");
		valorQ2.setPrefWidth(50);
		valorQ2.setEditable(false);
		valorQ2.textProperty().addListener((observable, oldValue, newValue) -> {
		    if(FisUI.cargas.size() > 1){
		    	valorQ2.setEditable(true);
		    	FisUI.cargas.get(1).alteraCarga(Double.parseDouble(newValue));		    	
		    }else{valorQ2.setEditable(false);}
		});
		
		Label x = new Label("Distancia\nde carga (m)");
		TextField valorX = new TextField();
		valorX.setId("posX");
		valorX.setPrefWidth(50);
		valorX.setEditable(false);
		
		Label y = new Label("Distancia\nde carga' (m)");
		TextField valorY = new TextField();
		valorY.setId("posY");
		valorY.setPrefWidth(50);
		valorY.setEditable(false);
		
		Label er = new Label("Campo\nElétrico (N/C)");
		TextField valorEr = new TextField();
		valorEr.setId("campoEletrico");
		valorEr.setPrefWidth(50);
		valorEr.setEditable(false);
		
		grid.add(new Label("Cargas"), 0, 0);
		grid.add(new Separator(), 0, 1);
		grid.add(q1, 0, 2);
		grid.add(valorQ1, 1, 2);
		grid.add(q2, 0, 3);
		grid.add(valorQ2, 1, 3);
		//grid.add(distancia, 0, 4);
	//	grid.add(valorDistancia, 1, 4);
		grid.add(new Label("Carga Teste"), 0, 6);
		grid.add(new Separator(), 0, 7);
		grid.add(x, 0, 8);
		grid.add(valorX, 1, 8);
		grid.add(y, 0, 9);
		grid.add(valorY, 1, 9);
		grid.add(er, 0, 15);
		grid.add(valorEr, 1, 15);
		
		return grid;
	}
	
	//controle da área de desenho
	public static StackPane canvasUI(){
		//Empilha os frames
		StackPane raiz = new StackPane(); 
		
		Pane layer0 = new Pane(); //objetos
		Canvas layer1 = new Canvas(500,500); // campoel
		layer1.setId("canvasCampoEletrico");
		Canvas layer2 = new Canvas(500,500); // campoequi
		
		layerCargas(layer0);
		layer1.setPickOnBounds(true);
		
		GraphicsContext gc = layer2.getGraphicsContext2D();
		gc.setFill(Color.WHITE);
		gc.fillRect(0, 0, 500, 500);
		
		//1m equivale a 30px
		gc.setFill(Color.GRAY);
		gc.strokeLine(450, 480, 480, 480 ); // linha de medida
		gc.strokeLine(450, 475, 450 , 485); // início
		gc.strokeLine(480, 475, 480, 485); // térmico
		gc.strokeText("1m", 455, 475);
		
		raiz.getChildren().addAll(layer2, layer1,layer0);

		return raiz;
	}
	
	public static void layerCargas(Pane scene){
	 	//tamanho da cena
	 	scene.setLayoutX(500);
	 	scene.setLayoutY(500);
	 	
	 	//tratamento para caso de clique na área da scene
		scene.setOnMouseClicked(new EventHandler<MouseEvent>(){
			ContextMenu menu = new ContextMenu();
			@Override
			public void handle(MouseEvent event) {
				menuContexto(menu, scene, event);
				if(event.getButton() == MouseButton.SECONDARY && FisUI.isShapeSelected == false){
					menu.show(scene, event.getScreenX(), event.getScreenY());
				}
				else{
					menu.hide();
				}
			}
		});
		scene.setOnMouseMoved(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent event) {
				atualizaValores();
				//FisUI.desenhaCampoEletrico();
			}
		});;	
	 }

	//operações
	private static void menuContexto(ContextMenu menu,  Pane scene, MouseEvent mouse){
		//ContextMenu contextMenu = new ContextMenu();
		menu.setAutoHide(true);
		
		MenuItem addCarga = new MenuItem("Adicionar carga");
		addCarga.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				scene.getChildren().add(FisUI.criaCarga(1.0, mouse.getX(), mouse.getY()));
			}
		});
		
		MenuItem addCargaTeste = new MenuItem("Adicionar carga teste");
		addCargaTeste.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				scene.getChildren().add(FisUI.criaCargaTeste(mouse.getX(), mouse.getY()));
			}
		});
		menu.getItems().remove(0, menu.getItems().size());
		
		if(FisUI.cargas.size()< FisUI.numMaxCargas)
			menu.getItems().add(addCarga);
		
		if(FisUI.cargasTeste.size() < FisUI.numMaxCargasTeste)
			menu.getItems().add(addCargaTeste);
		
	}
	
	public static void atualizaValores(){
		Node node = raiz.lookup("#infoPane");
		TextField field;
		switch(FisUI.cargas.size()){
			case 0: 
				field = (TextField) node.lookup("#q1");
				field.setText("0.0");
				
				field = (TextField) node.lookup("#q2");
				field.setText("0.0");
				
				break;
			case 1:
				field = (TextField) node.lookup("#q1");
				field.setText(FisUI.cargas.get(0).getCarga().getCarga().toString());
				
				field = (TextField) node.lookup("#q2");
				field.setText("0.0");
				
				break;
			case 2:
				field = (TextField) node.lookup("#q1");
				field.setText(FisUI.cargas.get(0).getCarga().getCarga().toString());
				
				field = (TextField) node.lookup("#q2");
				field.setText(FisUI.cargas.get(1).getCarga().getCarga().toString());
				
				break;
		}
		
		if(FisUI.cargasTeste.size()>0){
			
			switch(FisUI.cargas.size()){
				case 0:
					field = (TextField) node.lookup("#posX");
					field.setText("0.0");
					
					field = (TextField) node.lookup("#posY");
					field.setText("0.0");
					
					field = (TextField) node.lookup("#campoEletrico");
					field.setText("0.0");
					break;
					
				case 1:
					field = (TextField) node.lookup("#posX");
					field.setText(Math.sqrt(Math.pow(FisUI.cargas.get(0).getCarga().getPosX() - FisUI.cargasTeste.get(0).getCarga().getPosX(), 2)+Math.pow(FisUI.cargas.get(0).getCarga().getPosY() - FisUI.cargasTeste.get(0).getCarga().getPosY(), 2))/30+"");
					
					//System.out.println(Math.sqrt(Math.pow(FisUI.cargas.get(0).getCarga().getPosX() - FisUI.cargasTeste.get(0).getCarga().getPosX(), 2)+Math.pow(FisUI.cargas.get(0).getCarga().getPosY() - FisUI.cargasTeste.get(0).getCarga().getPosY(), 2))/30);
					
					field = (TextField) node.lookup("#posY");
					field.setText("0.0");
					
					field = (TextField) node.lookup("#campoEletrico");
					field.setText(Fis.forcaEletrica(FisUI.cargas.get(0).getCarga(), FisUI.cargasTeste.get(0).getCarga())/1000000+"");
					break;
				case 2:
					field = (TextField) node.lookup("#posX");
					field.setText(Math.sqrt(Math.pow(FisUI.cargas.get(0).getCarga().getPosX() - FisUI.cargasTeste.get(0).getCarga().getPosX(), 2)+Math.pow(FisUI.cargas.get(0).getCarga().getPosY() - FisUI.cargasTeste.get(0).getCarga().getPosY(), 2))/30+"");
					
					field = (TextField) node.lookup("#posY");
					field.setText(Math.sqrt(Math.pow(FisUI.cargas.get(1).getCarga().getPosX() - FisUI.cargasTeste.get(0).getCarga().getPosX(), 2)+Math.pow(FisUI.cargas.get(1).getCarga().getPosY() - FisUI.cargasTeste.get(0).getCarga().getPosY(), 2))/30+"");
					
					field = (TextField) node.lookup("#campoEletrico");
					field.setText(Fis.campoEletricoResultante(FisUI.cargas.get(0).getCarga(), FisUI.cargas.get(1).getCarga(), new Ponto(FisUI.cargasTeste.get(0).getCarga().getPosX(), FisUI.cargasTeste.get(0).getCarga().getPosY()))/900+"");
					break;
			}
		}
		else{
			field = (TextField) node.lookup("#posX");
			field.setText("0.0");
			
			field = (TextField) node.lookup("#posY");
			field.setText("0.0");
			
			field = (TextField) node.lookup("#campoEletrico");
			field.setText("0.0");
		}
		
	}
	
	public static void setRaiz(Node node){
		raiz = node;
	}
	
	public static void setRaizCanvas(Node node){
		raizCanvas = node;
	}
}
