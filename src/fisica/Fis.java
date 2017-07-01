package fisica;

public final class Fis {
	public final static Double Eo = 8.85418782e-12;
	public final static Double Ko = 1/(4*Math.PI*Eo);
	
	/**
	 * Calcula a intensidade da força elétrica entre duas cargas
	 * @param q1 carga
	 * @param q2 carga
	 */
	public static Double forcaEletrica(Particula q1, Particula q2){
		Double Fel = Ko * q1.getCarga() * q2.getCarga();
		
		Double diffX = Math.abs(q1.getPosX() - q2.getPosX()),
			   diffY = Math.abs(q1.getPosY() - q2.getPosY());
		
		if(diffX == 0.0 && diffY != 0.0){ //cargas no eixo x
			Fel = Fel/Math.pow(diffY, 2);
		}else if(diffX != 0.0 && diffY == 0.0){ //cargas no eixo y
			Fel = Fel/Math.pow(diffX, 2);
		}else{
			Fel = Fel/Math.pow(diffX, 2)* Math.cos(Math.toRadians(angulo(diffX, diffY)));
		}
		
		return Fel;
		
	}
	/**
	 * Calcula o valor do campo elétrico gerado por uma carga num ponto.
	 * @param q carga
	 * @param p ponto
	 */
	public static Double campoEletrico(Particula q, Ponto p){
		Particula qp = new Particula(1.0, p.getPosX(), p.getPosY());
		
		//campoelétrico é a determinaçao da força elétrica sobre uma carga teste de 1C
		
		return forcaEletrica(q, qp);
	}
	
	/**
	 * Calcula o valor do campo eletrico resultante gerado por duas partículas em cima de um ponto.
	 * @param q1 carga
	 * @param q2 carga
	 * @param p ponto
	 */
	public static Double campoEletricoResultante(Particula q1, Particula q2, Ponto p){
		Double Epq1, Epq2;
		
		Epq1 = campoEletrico(q1, p);
		Epq2 = campoEletrico(q2, p);
		
		return Math.abs(Epq2 - Epq1);
	}
	
	/**Calcula o valor do campo eletrico de uma carga em cima de um ponto a uma determinada distância. 
	 * @param q carga 
	 * @param posX distância 
	 * @param angulo ângulo 
	 * */
	public static Double campoEletrico(Particula q, Double posX, Double angulo){
		return Ko*q.getCarga()*Math.cos(Math.toRadians(angulo))/Math.pow(posX, 2);
	}
	
	public static Double angulo(Double x, Double y){
		 return (double) Math.round(Math.toDegrees(Math.atan(y/x)));
	}
	
	/**Calcula a posição em y, de uma carga de teste considerando a interação entre duas partículas a um ângulo theta
	 * @param q1 partícula
	 * @param q2 partícula
	 * @param angulo angulo
	 * @return posição y da carga teste
	 * **/
	public static Double posY(Particula q1, Particula q2, Double posX, Double angulo){
		Double Er;
		
		Er = campoEletrico(q1, posX, angulo)-campoEletrico(q2, Math.abs(q1.getPosX()-q2.getPosX())-posX, angulo);
		
		return Math.sqrt(Math.abs(Math.cos(angulo)*Ko*q1.getCarga()/Er));
	}
}
