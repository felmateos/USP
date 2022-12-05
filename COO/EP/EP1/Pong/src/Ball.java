import java.awt.*;
import java.util.Random;


/**
 Esta classe representa a bola usada no jogo. A classe princial do jogo (Pong)
 instancia um objeto deste tipo quando a execução é iniciada.
 */

public class Ball {

	double cx;
	double cy;
	double width;
	double height;
	Color color;
	double speed;

	boolean onStart = true;
	double xSpeed;
	double ySpeed;
	String lColl = "";

	/**
	 Construtor da classe Ball. Observe que quem invoca o construtor desta classe define a velocidade da bola
	 (em pixels por millisegundo), mas não define a direção deste movimento. A direção do movimento é determinada
	 aleatóriamente pelo construtor.

	 @param cx coordenada x da posição inicial da bola (centro do retangulo que a representa).
	 @param cy coordenada y da posição inicial da bola (centro do retangulo que a representa).
	 @param width largura do retangulo que representa a bola.
	 @param height altura do retangulo que representa a bola.
	 @param color cor da bola.
	 @param speed velocidade da bola (em pixels por millisegundo).
	 */

	public Ball(double cx, double cy, double width, double height, Color color, double speed){
		this.cx = cx;
		this.cy = cy;
		this.width = width;
		this.height = height;
		this.color = color;
		this.speed = speed;
	}

	/**
	 Método chamado sempre que a bola precisa ser (re)desenhada.
	 */

	public void draw(){
		GameLib.setColor(Color.YELLOW);
		GameLib.fillRect(getCx(), getCy(), getWidth(), getWidth());
	}

	/**
	 Método chamado para definir em que direção e esentido a bola quadrada irá se mover
	 */

	public void setDirection() {
		double ySpd = 0;
		double xSpd = 0;
		int factor = -1;
		double yLimit = Math.sqrt(((getSpeed())*(getSpeed()))*3/4);
		Random rand = new Random();
		ySpd = yLimit*rand.nextDouble();
		xSpd = Math.sqrt((getSpeed()*getSpeed())-(ySpd*ySpd));
		factor = rand.nextInt(2);
		if (factor == 0) factor = -1;
		setYSpeed(ySpd*factor);
		factor = rand.nextInt(2);
		if (factor == 0) factor = -1;
		setXSpeed(xSpd*factor);
		onStart = false;
	}

	/**
	 Método chamado quando o estado (posição) da bola precisa ser atualizado.

	 @param delta quantidade de millisegundos que se passou entre o ciclo anterior de atualização do jogo e o atual.
	 */

	public void update(long delta){
		if(onStart) setDirection();
		setCx(getCx()+(delta*xSpeed));
		setCy(getCy()+(delta*ySpeed));
	}

	/**
	 Método chamado quando detecta-se uma colisão da bola com um jogador.

	 @param playerId uma string cujo conteúdo identifica um dos jogadores.
	 */

	public void onPlayerCollision(String playerId){
		if(lColl.equals(playerId)) xSpeed = -xSpeed;
	}

	/**
	 Método chamado quando detecta-se uma colisão da bola com uma parede.

	 @param wallId uma string cujo conteúdo identifica uma das paredes da quadra.
	 */

	public void onWallCollision(String wallId){
		if (wallId.equals("Top") || wallId.equals("Bottom")) setYSpeed(-ySpeed);
		if (wallId.equals("Right") || wallId.equals("Left")) setXSpeed(-xSpeed);
	}

	/**
	 Método que verifica se houve colisão da bola com uma parede.

	 @param wall referência para uma instância de Wall contra a qual será verificada a ocorrência de colisão da bola.
	 @return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	 */

	public boolean checkCollision(Wall wall){
		double wWidthFix = wall.getWidth()/2;
		double wHeightFix = wall.getHeight()/2;
		if (wall.getId().equals("Right")) wWidthFix = -wWidthFix;
		if (wall.getId().equals("Bottom")) wHeightFix = -wHeightFix;
		if ((Math.abs((wall.getCy()+wHeightFix)-(getCy()+getHeight()/2)) <= 6.5
				|| Math.abs((wall.getCy()+wHeightFix)-(getCy()-getHeight()/2)) <= 6.5
				|| Math.abs((wall.getCx()+wWidthFix)-(getCx()+getWidth()/2)) <= 6.5
				|| Math.abs((wall.getCx()+wWidthFix)-(getCx()-getWidth()/2)) <= 6.5)
				&& !lColl.equals(wall.getId())) {
			setLColl(wall.getId());
			return true;
		}
		return false;
	}

	/**
	 Método que verifica se houve colisão da bola com um jogador.

	 @param player referência para uma instância de Player contra o qual será verificada a ocorrência de colisão da bola.
	 @return um valor booleano que indica a ocorrência (true) ou não (false) de colisão.
	 */

	public boolean checkCollision(Player player){
		if (((getCy()-getHeight()/2 <= player.getCy()+player.getHeight()/2)
				&& (getCy()+getHeight()/2 >= player.getCy()+player.getHeight()/2))
				&& (getCx()-getWidth()/2 < player.getCx()+player.getWidth()/2)
				&& (getCx()+getWidth()/2 > player.getCx()-player.getWidth()/2)
				&& !lColl.equals(player.getId())) {
			if (((getCy()+getHeight()/2 >= player.getCy()-player.getHeight()/2)
					&& (getCy()-getHeight()/2 <= player.getCy()-player.getHeight()/2)))
				setYSpeed(-Math.abs(ySpeed));
			else setYSpeed(Math.abs(ySpeed));
			setLColl(player.getId());
			return false;
		}
		else if ((((getCx()+getWidth()/2) >= (player.getCx()-player.getWidth()/2)
				&& (getCx()+getWidth()/2) <= (player.getCx()+player.getWidth()/2))
				|| ((getCx()-getWidth()/2) >= (player.getCx()-player.getWidth()/2)
				&& (getCx()-getWidth()/2) <= (player.getCx()+player.getWidth()/2)))
		        && ((getCy()+getHeight()/2) > player.getCy() - (player.getHeight()/2))
				&& ((getCy()-getHeight()/2) < player.getCy() + (player.getHeight()/2))
				&& !lColl.equals(player.getId())) {
			setLColl(player.getId());
			return true;
		}
		return false;
	}


	/**
	 Método que devolve a coordenada x do centro do retângulo que representa a bola.
	 @return o valor double da coordenada x.
	 */

	public double getCx(){
		return cx;
	}

	/**
	 Método que altera a coordenada x do centro do retângulo que representa a bola.
	 @param cx valor da nova coordenada x.
	 */

	public void setCx(double cx){
		this.cx = cx;
	}

	/**
	 Método que devolve a coordenada y do centro do retângulo que representa a bola.
	 @return o valor double da coordenada y.
	 */

	public double getCy(){
		return cy;
	}

	/**
	 Método que altera a coordenada y do centro do retângulo que representa a bola.
	 @param cy valor da nova coordenada y.
	 */

	public void setCy(double cy){
		this.cy = cy;
	}

	/**
	 Método que devolve o valor da largura da bola.
	 @return o valor double de width.
	 */

	public double getWidth(){
		return width;
	}

	/**
	 Método que devolve o valor da altura da bola.
	 @return o valor double de height.
	 */

	public double getHeight(){
		return height;
	}

	/**
	 Método que devolve a velocidade da bola.
	 @return o valor double da velocidade.
	 */

	public double getSpeed(){
		return speed;
	}

	/**
	 Método que modifica o valor da velocidade horizontal da bola.
	 @param xSpeed o novo valor double da velocidade horizontal.
	 */

	public void setXSpeed(double xSpeed){
		this.xSpeed = xSpeed;
	}

	/**
	 Método que modifica o valor da velocidade horizontal da bola.
	 @param ySpeed o novo valor double da velocidade horizontal.
	 */

	public void setYSpeed(double ySpeed){
		this.ySpeed = ySpeed;
	}

	/**
	 Método que modifica o valor da variavel global lColl.
	 @param lColl o id do ultimo objeto que a bola colidiu.
	 */

	public void setLColl(String lColl) {
		this.lColl = lColl;
	}

}
