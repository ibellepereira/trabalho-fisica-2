package fisica;

public class Particula {
	private Double carga;
	private Double posX;
	private Double posY;
	
	public Particula(){	
		this.carga = 0.0;
		this.posX = 0.0;
		this.posY = 0.0;
	}
	
	public Particula(Double carga){
		this.carga = carga;
		this.posX = 0.0;
		this.posY = 0.0;
	}
	
	public Particula(Double carga, Double x, Double y){
		this.carga = carga;
		this.posX = x;
		this.posY = y;
	}

	public Double getCarga() {
		return carga;
	}

	public void setCarga(Double carga) {
		this.carga = carga;
	}

	public Double getPosX() {
		return posX;
	}

	public void setPosX(Double posX) {
		this.posX = posX;
	}

	public Double getPosY() {
		return posY;
	}

	public void setPosY(Double posY) {
		this.posY = posY;
	}
	
	
}
