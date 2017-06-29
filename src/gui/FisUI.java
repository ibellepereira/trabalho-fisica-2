package gui;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class FisUI {
	protected static List<Carga> cargas = new ArrayList<Carga>();
	protected static List<Carga> cargasTeste = new ArrayList<Carga>();
	protected final static int numMaxCargas = 2;
	protected final static int numMaxCargasTeste = 1;
	protected static Double dragDeltax;
	protected static Double dragDeltay;
	protected static boolean isShapeSelected;

	public static Circle criaCarga(Double valorCarga, Double x, Double y){
		if (cargas.size()>=numMaxCargas) return null;
		
		Carga nova = new Carga(valorCarga, x, y, 20.0);
		cargas.add(nova);
		
		UI.atualizaValores();
		return nova.getCirculo();
	}
	
	public static Circle criaCargaTeste(Double x, Double y){
		
		Carga nova = new Carga(1.0, x, y, 5.0);
		cargasTeste.add(nova);
		nova.getCirculo().setFill(Color.BLACK);
		UI.atualizaValores();
		return nova.getCirculo();
	}
}
