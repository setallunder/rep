package application;

import java.io.IOException;
import java.util.ArrayList;

import model.Sorter;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import view.SnakesWindowController;
import model.AI;
import model.Saver;
import model.JavaNotationSort;
/**
 * �������� ����� ����������
 * ���������� ������
 * @author Sanyadovskiy
 * @version 1.0
 */
public class Main extends Application {
	private Stage primaryStage;	//�������� ����� ��� �������� JavaFX ����������
	private SnakesWindowController controller; //������� � ������-�����������
	private AnchorPane root;	//������� ����� �����
	private ArrayList <Rectangle> parts = new ArrayList<>(); //��������� ��� ��-��� ������
	private StringBuilder notation = new StringBuilder();
	volatile char nextStep;
	volatile char decision;
	volatile int steps;
	volatile boolean killYourself = false;
	
	/**
	 * 
	 * �����-������
	 * ������������ ��������� ��������� �������,
	 * �������� �����-"�������������"
	 * @author Sanyadovskiy
	 *
	 */
	class Server extends Thread{
		@Override
		public void run(){
			Oracle genius = new Oracle();
			genius.setDaemon(true);
			genius.start();
			while(true){
				if(killYourself)
					break;
				switch (controller.GetWhereSnakeGoes()){
				case 0:
					nextStep = 'U';
					break;
				case 1:
					nextStep = 'R';
					break;
				case 2:
					nextStep = 'D';
					break;
				case 3:
					nextStep = 'L';
					break;
				}
			}
		}
	}
	
	/**
	 * 
	 * �����-�������������
	 * @author Sanyadovskiy
	 *
	 */
	class Oracle extends Thread{
		@Override
		public void run() {
			while(true){
				switch (AI.MakeMove(controller.GetAppleX(), controller.GetAppleY(),
								controller.GetHeadX(), controller.GetHeadY(), controller.GetWhereSnakeGoes())){
					case 0: decision = 'U';
						break;
					case 1: decision = 'R';
						break;
					case 2: decision = 'D';
						break;
					case 3: decision = 'L';
						break;
				}
				steps = AI.StepsToApple(controller.GetAppleX(), controller.GetAppleY(), 
											controller.GetHeadX(), controller.GetHeadY());
			}
		}
	}
	
	@Override
	public void start(Stage primaryStage) {
		try {
			this.primaryStage = primaryStage;
			this.primaryStage.setTitle("Snake"); //������ �������� ����
			this.primaryStage.centerOnScreen();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		windowInit(); //�������������� ����
		
		Server serv = new Server();
		serv.setDaemon(true);
		serv.start();
	}
	
	/**
	 * ������������� ����
	 */
	public void windowInit(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/SnakesWindow.fxml"));//�������� ������ �� FXML �����
			root = (AnchorPane) loader.load(); //������������� ����������� ������ � ������� ������� ������� �����
			
	        primaryStage.setScene(new Scene(root, 600, 400)); //������� ����� �������� ��������
	        
	        primaryStage.show(); //���������� ����
	        
	        controller = loader.getController(); //�������� ���������� ����
	        controller.setMain(this); //���� ����������� �������� ����� � main �������
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * �������� ���� ���� 
	 * �� ������ ������, ���������� �� ������-�������,
	 * ������������ ��������� ��������� �������.
	 * �������� ��.
	 * ������������� ��������� ����
	 */
	public void GameCicle(){
		controller.SetFuture(new Character(decision).toString());
		controller.SetSteps(new Integer(steps).toString());
		//��������������� �� ��������� ����
		if(!controller.isRetrospective()){
			//������������ � �������
			if(controller.GetAppleX() - controller.GetHeadX() >= -5 &&
					controller.GetAppleX() - controller.GetHeadX() <= 10 &&
					controller.GetAppleY() - controller.GetHeadY() >= -5 &&
					controller.GetAppleY() - controller.GetHeadY() <= 10){
				controller.RelocateApple();
				notation.append('A');
				controller.SetText("A");
				notation.append((int)controller.GetAppleX());
				notation.append(',');
				notation.append((int)controller.GetAppleY());
				parts.add(new Rectangle(10, 10, Paint.valueOf("DODGERBLUE"))); //�������� ����� ������ ������
				parts.get(parts.size()-1).setStroke(Paint.valueOf("BLACK")); //������� � ������
				//������� ��������� ����� ������
				if(parts.size()==1){
					parts.get(0).setLayoutX(controller.GetHeadX());
					parts.get(0).setLayoutY(controller.GetHeadY());
				}
				else{
					parts.get(parts.size()-1).setLayoutX(parts.get(parts.size()-2).getLayoutX());
					parts.get(parts.size()-1).setLayoutY(parts.get(parts.size()-2).getLayoutY());
				}
				root.getChildren().add(parts.get(parts.size()-1)); //��������� ������ � ����
			}
			//������������ � ��������� ��� �������
			if(!CollisionCheck()){
				MoveParts(); //�������� ����� ������
				//��������� �� ���� � ������������� ������
				if(!controller.isAuto()){
					switch (nextStep){
					case 'U':
						notation.append('U'); 		//��������� ��� � �������
						controller.SetText("U");
						controller.MoveHead(0, -10);//�������� �����
						break;
					case 'R':
						notation.append('R'); 		//��������� ��� � �������
						controller.SetText("R");
						controller.MoveHead(10, 0); //�������� ������
						break;
					case 'D':
						notation.append('D'); 		//��������� ��� � �������
						controller.SetText("D");
						controller.MoveHead(0, 10); //������� ����
						break;
					case 'L':
						notation.append('L'); 		//��������� ��� � �������
						controller.SetText("L");
						controller.MoveHead(-10, 0);//�������� �����
						break;
					}
				}
				else{
					//�������� ����������� �������
					switch(decision){
					case 'U':
						notation.append('U'); 			//��������� ��� � �������
						controller.SetText("U");
						controller.SetWhereSnakeGoes(0);//���������� ����
						controller.MoveHead(0, -10); 	//�������� �����
						break;
					case 'R':
						notation.append('R'); 			//��������� ��� � �������� ��� � �������
						controller.SetText("R");
						controller.SetWhereSnakeGoes(1);//���������� ����        
						controller.MoveHead(10, 0); 	//�������� ������
						break;
					case 'D':
						notation.append('D'); 			//��������� ��� � �������� ��� � �������
						controller.SetText("D");
						controller.SetWhereSnakeGoes(2);//���������� ����        
						controller.MoveHead(0, 10); 	//������� ����
						break;
					case 'L':
						notation.append('L'); 			//��������� ��� � �������� ��� � �������
						controller.SetText("L");
						controller.SetWhereSnakeGoes(3);//���������� ����        
						controller.MoveHead(-10, 0); 	//�������� �����
						break;
					}
				}
			}
			else{
				End(); //��������� ������������, ����� ����
			}
		}
		else{
			MoveParts();//����� ������
			if(notation.length() == 0){
				notation.append(Saver.Load("notation.txt"));//�������� �������
				long curTime = System.currentTimeMillis();
				switch (controller.whatSorting()){
				case 1:	//Scala ����������
					if(controller.whatTypeOfSorting() == 0){
						notation = Sorter.SortNotationBTW(notation);
					}
					else{
						notation = Sorter.SortNotationWTB(notation);
					}
					break;
				case 2: //Java ����������
					if(controller.whatTypeOfSorting() == 0){
						notation = JavaNotationSort.sort(notation,false);	//Best to worst
					}
					else{
						notation = JavaNotationSort.sort(notation,true);	//Worst to best
					}
					break;
				}
				System.out.print("Time, spend on sorting: ");
				System.out.print(System.currentTimeMillis() - curTime);		//����� ������������������
				System.out.println(" ms");
			}
			//�������� ���������� ������� �������
			switch(notation.charAt(0)){
			//������ ��������� ������
			case 'A':
				controller.SetText("A");
				notation.deleteCharAt(0);//������� ������
				int i = 0;
				
				while(notation.charAt(++i) != ',');//���������� �� ����������� ���������
				
				//��������� ���������� � int � ������ � ������
				controller.SetAppleX(Integer.parseInt(notation.toString().substring(0, i)));
				i++;
				notation.delete(0, i);//������� �������������� ���������� � �����������
				
				i = 0;
				while(notation.charAt(i) >= '0' && notation.charAt(i) <= '9')//���������� �� ������ ����������
					i++;
				
				//��������� ���������� � int � ������ � ������
				controller.SetAppleY(Integer.parseInt(notation.toString().substring(0, i)));
				notation.delete(0, i);//������� �������������� ����������
				
				parts.add(new Rectangle(10, 10, Paint.valueOf("DODGERBLUE"))); //�������� ����� ������ ������
				parts.get(parts.size()-1).setStroke(Paint.valueOf("BLACK")); //������� � ������
				//������� ��������� ����� ������
				if(parts.size()==1){
					parts.get(0).setLayoutX(controller.GetHeadX());
					parts.get(0).setLayoutY(controller.GetHeadY());
				}
				else{
					parts.get(parts.size()-1).setLayoutX(parts.get(parts.size()-2).getLayoutX());
					parts.get(parts.size()-1).setLayoutY(parts.get(parts.size()-2).getLayoutY());
				}
				root.getChildren().add(parts.get(parts.size()-1)); //��������� ������ � ����
				break;
			//������ �������� �����
			case 'U':
				controller.SetText("U");
				controller.MoveHead(0, -10);//��� �����
				notation.deleteCharAt(0);	//������� ������
				break;
			//������ �������� ������
			case 'R':
				controller.SetText("R");
				controller.MoveHead(10, 0);//��� ������
				notation.deleteCharAt(0);  //������� ������
				break;
			//������ �������� ����
			case 'D':
				controller.SetText("D");
				controller.MoveHead(0, 10);//��� ����
				notation.deleteCharAt(0);  //������� ������
				break;
			//������ �������� �����
			case 'L':
				controller.SetText("L");
				controller.MoveHead(-10, 0);//��� �����
				notation.deleteCharAt(0);	//������� ������
				break;
			//������ ����� �������
			case 'E':
				controller.SetText("E");
				notation.deleteCharAt(0);	//������� ������
				if(notation.charAt(0) == '\0')
					End();						//��������� ����
				else{
					parts.clear();
					controller.SetHeadX(295);
					controller.SetHeadY(195);
					controller.SetAppleX(131);
					controller.SetAppleY(114);
				}
			}
		}
	}
	
	/**
	 * ����� ����
	 */
	public void End(){
		try{
			//���� ������� �� ����� � �� �� � ������ ���������������
			//��������� � ���������� �������� ����� �������
			if(notation.length() != 0 && !controller.isRetrospective()){
				notation.append('E');
				notation.append(Saver.Load("notation.txt"));
				Saver.Save(notation.toString(), "notation.txt");
			}
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/End.fxml")); //�������� ���������� �����������
			AnchorPane end;
			end = (AnchorPane) loader.load();
			root.getChildren().add(end); //��������� ��������� ����������� � ����
			controller.StopTheGame();
			killYourself = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �������� �� ������������ � ����� � �������
	 * @return ���� �� ������������
	 */
	public boolean CollisionCheck(){
		//������������ � �����
		if(controller.GetHeadX() <= 0 || controller.GetHeadX() >= 590 || 
				controller.GetHeadY() <= 25 || controller.GetHeadY() >= 390){
			return true;
		}
		//������������ � �������
		for(int i = 0; i<parts.size()-1; i++){
			if(parts.get(i).getLayoutX() == controller.GetHeadX() &&
					parts.get(i).getLayoutY() == controller.GetHeadY()){
				return true;
			}
		}
		return false; //������������ �� ���������
	}
	
	/**
	 * ����� ������
	 */
	public void MoveParts(){
		//��� ����� ����, ������ ������� ��-�� � ����� ���������� ����������
		if(!parts.isEmpty()){
			for(int i = parts.size()-1; i>0; i--){
				parts.get(i).setLayoutX(parts.get(i-1).getLayoutX());
				parts.get(i).setLayoutY(parts.get(i-1).getLayoutY());
			}
			parts.get(0).setLayoutX(controller.GetHeadX());
			parts.get(0).setLayoutY(controller.GetHeadY());
		}
	}
}
