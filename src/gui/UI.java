package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.WindowEvent;

public final class UI {
	protected static Node raiz;

	public static GridPane infoUI(){
		GridPane grid = new GridPane();
		grid.setId("infoPane");
		grid.setVgap(10);
		grid.setHgap(10);
	
		Label q1 = new Label("Carga");
		TextField valorQ1 = new TextField();
		valorQ1.setPrefWidth(50);
		valorQ1.setId("q1");
		valorQ1.setEditable(false);
		/*valorQ1.textProperty().addListener((observable, oldValue, newValue) -> {
		    if(FisUI.cargas.size() > 0 ){
		    	valorQ1.setEditable(true);
		    	FisUI.cargas.get(0).alteraCarga(Double.parseDouble(newValue));		    	
		    }else{valorQ1.setEditable(false);}
		});*/
		
		Label q2 = new Label("Carga'");
		TextField valorQ2 = new TextField();
		valorQ2.setId("q2");
		valorQ2.setPrefWidth(50);
		valorQ2.setEditable(false);
	/*	valorQ2.textProperty().addListener((observable, oldValue, newValue) -> {
		    if(FisUI.cargas.size() > 0){
		    	valorQ2.setEditable(true);
		    	FisUI.cargas.get(1).alteraCarga(Double.parseDouble(newValue));		    	
		    }else{valorQ2.setEditable(false);}
		});*/
		
		Label distancia = new Label("Distância");
		TextField valorDistancia = new TextField();
		valorDistancia.setId("distancia");
		valorDistancia.setPrefWidth(50);
		valorDistancia.setEditable(false);
		
		Label x = new Label("Pos X");
		TextField valorX = new TextField();
		valorX.setId("posX");
		valorX.setPrefWidth(50);
		valorX.setEditable(false);
		
		Label y = new Label("Pos Y");
		TextField valorY = new TextField();
		valorY.setId("posY");
		valorY.setPrefWidth(50);
		valorY.setEditable(false);
		
		Label er = new Label("Campo\nElétrico");
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
		Canvas layer2 = new Canvas(500,500); // campoequi
		
		layerCargas(layer0);
		
		GraphicsContext area = layer1.getGraphicsContext2D();
		
		area.setFill(Color.WHITE);
		area.fillRect(0, 0, 500, 500);
		
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
		
	 	
	 }

	public static void layerCampoEletrico(Canvas scene){
		GraphicsContext gc = scene.getGraphicsContext2D();
		
		
		
		
	}

	//operações
	private static void menuContexto(ContextMenu menu,  Pane scene, MouseEvent mouse){
		//ContextMenu contextMenu = new ContextMenu();
		menu.setAutoHide(true);
		
		MenuItem addCarga = new MenuItem("Adicionar carga");
		addCarga.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				scene.getChildren().add(FisUI.criaCarga(-1.0, mouse.getX(), mouse.getY()));
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
			field = (TextField) node.lookup("#posX");
			field.setText(FisUI.cargasTeste.get(0).getCirculo().getLayoutX()/5+"");
			
			field = (TextField) node.lookup("#posY");
			field.setText(FisUI.cargasTeste.get(0).getCirculo().getLayoutY()/5+"");
		}
	}
	
	public static void setRaiz(Node node){
		raiz = node;
	}
	
}