package main.util;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import main.src.CGameMain;
import main.src.Game;


public class KeyInput extends KeyAdapter{
	
	CGameMain game;
	
	public KeyInput(CGameMain cGameMain){
		this.game = cGameMain;
	}
	
	public void keyPressed(KeyEvent e){
		game.keyPressed(e);
	}
	
	public void keyReleased(KeyEvent e){
		game.keyReleased(e);
	}
}
