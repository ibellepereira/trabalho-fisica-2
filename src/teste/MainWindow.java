package teste;
import gui.UI;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainWindow extends Application {
	
	@Override
	public void start(Stage raiz) throws Exception {
		raiz.setTitle("Simulador de Campo Elétrico");
		raiz.setResizable(false);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setPadding(new Insets(10,10,10,10));
		
		borderPane.setCenter(UI.canvasUI());
		borderPane.setRight(UI.infoUI());
		
		UI.setRaiz(borderPane.getRight());
		UI.setRaizCanvas(borderPane.getCenter());
		
		BorderPane.setMargin(borderPane.getRight(), new Insets(20,20,20,20));
		
        raiz.setScene(new Scene(borderPane));
        raiz.show();	
	}
	
	public static void main(String[] args){
		launch(args);
	}
}
