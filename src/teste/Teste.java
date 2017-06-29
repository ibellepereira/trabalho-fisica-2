package teste;

import fisica.Fis;
import fisica.Particula;
import fisica.Ponto;

public class Teste {
	public static void main(String[] args) throws Exception{
		Particula q1, q2;
		Ponto p;
		
		q1 = new Particula(-4e-6);
		q2 = new Particula(-4e-6, 1.0, 0.0);
		p = new Ponto(2.0,2.0);
		
		Double y;
		Double[] x = {0.1, 0.5, 0.9};
		
		for (int i = 0; i<x.length; i++){
			System.out.printf("pos %.2f 30º : %.2f  60º: %.2f \n", 
					x[i], /*Math.sqrt(Math.abs(
							Fis.Ko*q2.getCarga()*Math.cos(30.0)/(Fis.campoEletrico(q1, x[i], 30.0)+Fis.campoEletrico(q2, 1.0-x[i], 30.0)))
							)*/
					
					Fis.posY(q1, q2, x[i], 30.0), Fis.posY(q1, q2, x[i], 60.0));
					
	
		}
		
	}
	
		public final static double pos(double y){
			if(y < 0)
				return (-1)*y;
			return y;
		}
			
}
