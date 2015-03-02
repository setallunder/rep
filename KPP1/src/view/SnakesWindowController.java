package view;

import java.awt.event.KeyAdapter;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class SnakesWindowController {
	@FXML
	private AnchorPane field;
	@FXML
	private Circle apple;
	@FXML
	private Rectangle head;
	@FXML
	private MenuItem start;
	@FXML
	private MenuItem showLastGame;
	@FXML
	private MenuItem autoPlay;
	@FXML
	private MenuItem end;
	
	private Main main;
	
	public SnakesWindowController(){
	}
	
	@FXML
	private void initialize(){
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML
	public void StartHandle(ActionEvent e){
		System.out.println("Start!");
	}
	
	@FXML
	public void ShowLastGameHandle(ActionEvent e){
		System.out.println("Show Last Game!");
	}
	
	@FXML
	public void AutoPlayHandle(ActionEvent e){
		System.out.println("Auto play!");
	}
	
	@FXML
	public void EndHandle(ActionEvent e){
		System.out.println("End!");
	}
	
	@FXML
	public void KeyboardHandle(KeyEvent e){
		System.out.println("Key!");
		if(e.getCode() == KeyCode.UP){
			System.out.println("UP!");
		}
		if(e.getCode() == KeyCode.DOWN){
			System.out.println("DOWN!");
		}
		if(e.getCode() == KeyCode.LEFT){
			System.out.println("LEFT!");
		}
		if(e.getCode() == KeyCode.RIGHT){
			System.out.println("RIGHT!");
		}
	}
	
	public void AddTail(Rectangle tail){
		
	}
}
