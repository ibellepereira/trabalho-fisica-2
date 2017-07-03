package gui;

import java.util.ArrayList;
import java.util.List;

import fisica.Fis;
import fisica.Ponto;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
		
		//UI.atualizaValores();
		return nova.getCirculo();
	}
	
	public static Circle criaCargaTeste(Double x, Double y){
		
		Carga nova = new Carga(1.0, x, y, 5.0);
		cargasTeste.add(nova);
		nova.getCirculo().setFill(Color.BLACK);
		//UI.atualizaValores();
		if(cargas.size()>0) desenhaCampoEletrico();
		return nova.getCirculo();
	}
	
	public static void desenhaCampoEletrico(){
		Node node = UI.raizCanvas.lookup("#canvasCampoEletrico");
		GraphicsContext gc = ((Canvas) node).getGraphicsContext2D();
		gc.setFill(Color.BLACK);
		gc.clearRect(0, 0, 500, 500);
		//gc.fillRect(0, 0, 500, 500);
		Ponto origem = new Ponto();
		Ponto destino = new Ponto();
		
		Double angulo, anguloProx;
		
		for(Carga carga: cargas){
			for(int i = 0; i < 12; i++){
				angulo = 2*Math.PI/12*i;
				
				origem.setPosX(carga.getCarga().getPosX() +Math.cos(angulo));
				origem.setPosY(carga.getCarga().getPosY() +Math.sin(angulo));
			
				for(int j = 0; j < 8000; j++){
					anguloProx = proxAngulo(origem.getPosX(), origem.getPosY());
					
				//	destino.setPosX(Math.sqrt(Fis.Ko*carga.getCarga().getCarga()/Fis.campoEletrico(carga.getCarga(), origem))+Math.cos(angulo));
				//	destino.setPosY(Math.sqrt(Fis.Ko*carga.getCarga().getCarga()/Fis.campoEletrico(carga.getCarga(), origem))+Math.sin(angulo));
					destino.setPosX(origem.getPosX() + 1*Math.cos(anguloProx));

					destino.setPosY(origem.getPosY() + 1*Math.sin(anguloProx));
					
					gc.strokeLine(origem.getPosX(), origem.getPosY(), destino.getPosX(), destino.getPosY());
					origem = destino;
				}
			}
		}
	}
	
	private static Double proxAngulo(Double antX, Double antY){
		 Double x = 0.0, y = 0.0;
		 Double distancia, campo, angulo;
		 
		 for(Carga carga : cargas){
			 distancia = Math.sqrt(Math.pow((antX - carga.getCarga().getPosX()), 2)+Math.pow((antY - carga.getCarga().getPosY()), 2));
		 
			 campo = Fis.Ko*carga.getCarga().getCarga()/Math.pow(distancia, 2);
			 
			 angulo = Math.atan2(antY-carga.getCarga().getPosY(), antX-carga.getCarga().getPosX());
			 
			 x += campo * Math.cos(angulo);
			 y += campo * Math.sin(angulo);
		 }
		 return Math.atan2(y, x);
	}
	
	public static void limpaCanvas(){
		Node node = UI.raizCanvas.lookup("#canvasCampoEletrico");
		GraphicsContext gc = ((Canvas) node).getGraphicsContext2D();
		gc.clearRect(0, 0, 500, 500);
	}
}
