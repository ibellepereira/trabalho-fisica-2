package gui;
import fisica.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Carga {
	private Particula carga;
	private Circle shape;
	
	public Carga(Double valorCarga, Double x, Double y, Double raio){
		this.carga = new Particula();
		this.carga.setCarga(valorCarga);
		this.carga.setPosX(x);
		this.carga.setPosY(y);
		
		this.shape = new Circle();
		configShape(x, y, raio);
	}
	
	private void configShape(Double x, Double y, Double raio){
		shape.setRadius(raio);
		shape.setCenterX(x);
		shape.setCenterY(y);
		if(carga.getCarga()>0) shape.setFill(Color.MEDIUMBLUE);
		else shape.setFill(Color.DARKRED);
		
		this.shape.setOnMousePressed(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
			    // record a delta distance for the drag and drop operation.
			    Double x = shape.getCenterX() - mouseEvent.getSceneX();
			    Double y = shape.getCenterY() - mouseEvent.getSceneY();
			    
			    FisUI.dragDeltax = x;
			    FisUI.dragDeltay = y;
			   
			    shape.setCursor(Cursor.MOVE); //desenho do cursor
			    if(shape.getFill() != Color.BLACK) FisUI.limpaCanvas();
			  }
			});
			
			this.shape.setOnMouseReleased(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
				  shape.setCursor(Cursor.HAND);
				  if(shape.getCenterX() > 500 || shape.getCenterX() < 0 ||
			    	 shape.getCenterY() > 500 || shape.getCenterY() < 0 ){
					 shape.setVisible(false);
					 removeCarga();
				  }
				  carga.setPosX(shape.getCenterX());
				  carga.setPosY(shape.getCenterY());
				  if(shape.getFill() != Color.BLACK) FisUI.limpaCanvas(); FisUI.desenhaCampoEletrico();
			  }
			});
			this.shape.setOnMouseDragged(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
				  shape.setCenterX(mouseEvent.getSceneX() + FisUI.dragDeltax);
				  shape.setCenterY(mouseEvent.getSceneY() + FisUI.dragDeltay);
				  
				//  System.out.println(shape.getLayoutX()/5);
				// System.out.println(shape.getLayoutY()/5);
			  }
			});
			
			this.shape.setOnMouseEntered(new EventHandler<MouseEvent>() {
			  @Override public void handle(MouseEvent mouseEvent) {
				  shape.setCursor(Cursor.HAND);
			  }
			});

			this.shape.setOnMouseExited(new EventHandler<MouseEvent>() {
				  @Override public void handle(MouseEvent mouseEvent) {
					  FisUI.isShapeSelected = false;
				  }
				});
			
			
			this.shape.addEventHandler(MouseEvent.MOUSE_PRESSED, 
					new EventHandler<MouseEvent>(){
						@Override
						public void handle(MouseEvent event) {
							if(event.getButton() == MouseButton.SECONDARY){
								FisUI.isShapeSelected = true;
								
								ContextMenu menu = new ContextMenu();
								MenuItem rm = new MenuItem("Remover carga");
								rm.setOnAction(new EventHandler<ActionEvent>(){
									@Override
									public void handle(ActionEvent event) {
										shape.setVisible(false);
										removeCarga();
									}
								});
								menu.getItems().add(rm);
								menu.show(shape, event.getScreenX(), event.getScreenY());
								menu.setAutoHide(true);
							}
						}
			});
	}
	
	private void removeCarga(){
		if(shape.getFill() == Color.BLACK)
			FisUI.cargasTeste.remove(this);
		else FisUI.cargas.remove(this);
	}
	
	public void alteraCarga(Double valorCarga){
		this.carga.setCarga(valorCarga);
		if(valorCarga==0) {shape.setVisible(false); removeCarga();}
		if(valorCarga>0) shape.setFill(Color.MEDIUMBLUE);
		else shape.setFill(Color.DARKRED);
		FisUI.desenhaCampoEletrico();
	}

	public Particula getCarga() {
		return carga;
	}

	public void setCarga(Particula carga) {
		this.carga = carga;
	}

	public Circle getCirculo() {
		return shape;
	}

	public void setCirculo(Circle circulo) {
		this.shape = circulo;
	}
}
