import java.awt.*;

/**
 Esta classe representa um placar no jogo. A classe princial do jogo (Pong)
 instancia dois objeto deste tipo, cada um responsável por gerenciar a pontuação
 de um player, quando a execução é iniciada.
 */

public class Score {

	/**
	 Construtor da classe Score.

	 @param playerId uma string que identifica o player ao qual este placar está associado.
	 */

	String playerId;
	int score = 0;

	String lPlayerColl = "";

	public Score(String playerId){
		this.playerId = playerId;
	}

	/**
	 Método de desenho do placar.
	 */

	public void draw(){
		if(getPlayerId().equals("Player 1")) GameLib.drawText(getPlayerId()+": "+getScore(), 70, GameLib.ALIGN_LEFT);
		else GameLib.drawText(getPlayerId()+": "+getScore(), 70, GameLib.ALIGN_RIGHT);
	}

	/**
	 Método que incrementa em 1 unidade a contagem de pontos.
	 */

	public void inc(){
		/*if (!lastPoint.equals(playerId)){*/
			score++;
			lPlayerColl = playerId;
		//}
	}

	/**
	 Método que devolve a contagem de pontos mantida pelo placar.

	 @return o valor inteiro referente ao total de pontos.
	 */

	public String getPlayerId() {
		return playerId;
	}

	public int getScore(){
		return score;
	}
}
