package fisica;

public class Ponto {
	private Double posX;
	private Double posY;
	
	public Ponto(){
		this.posX = 0.0;
		this.posY = 0.0;
	}
	
	public Ponto(Double x, Double y){
		this.posX = x;
		this.posY = y;
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
