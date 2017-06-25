package gui;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Separator;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.stage.Stage;

public class MainWindow extends Application {

	@Override
	public void start(Stage raiz) throws Exception {
		raiz.setTitle("Simulador de Campo Elétrico");
		raiz.setWidth(800);
		raiz.setHeight(700);
		raiz.setResizable(false);
		
		Group rootGroup = new Group();
        Canvas canvas = new Canvas(300, 250);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Separator separador = new Separator();
        separador.setOrientation(Orientation.VERTICAL);
        separador.setHalignment(100);
        paint(gc);
        rootGroup.getChildren().add(canvas);
        
        raiz.setScene(new Scene(rootGroup));
        raiz.show();
		
	}
	
	public void paint(GraphicsContext gc){
  
	}
	
	
	
	public static void main(String[] args){
		launch(args);
	}
}
