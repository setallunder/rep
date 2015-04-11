package view;

import java.util.Random;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
/**
 * ����� ����������
 * �������� � ���� ������, ����������� �� fxml �����
 * @author Sanyadovskiy
 * @version 1.0
 */
public class SnakesWindowController {
	@FXML
	private AnchorPane field; //�������� ����
	@FXML
	private Circle apple;
	@FXML
	private Rectangle head; //������ ����
	@FXML
	private MenuItem start;
	@FXML
	private MenuItem playLastGame;
	@FXML
	private CheckMenuItem autoPlay;
	@FXML
	private MenuItem end;
	@FXML
	private RadioMenuItem easy;
	@FXML
	private RadioMenuItem medium;
	@FXML
	private RadioMenuItem hard;
	@FXML
	private RadioMenuItem alternativeSort;
	@FXML
	private RadioMenuItem scalaSort;
	@FXML
	private RadioMenuItem javaSort;
	@FXML
	private RadioMenuItem bestToWorst;
	@FXML
	private RadioMenuItem worstToBest;
	@FXML
	private TextField text;
	@FXML
	private TextField steps;
	@FXML
	private TextField future;
	
	private Main main; //�������� ����� � main �������
	
	private boolean startPressed = false; //���� ������� ������ start
	
	private boolean retrospective = false;
	
	private int WhereSnakeGoes = 0; //����������� �������� ����
	
	Timeline movement; //�������� ������� �� �������� ����
	
	/**
	 * ����������� �� ���������
	 */
	public SnakesWindowController(){
	}
	
	@FXML
	private void initialize(){
	}
	
	/**
	 * ��������� ������ �� main
	 * @param main ������ �� main �����
	 */
	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML
	/**�������� ������� ������ start
	 * @param e ������� ������� ������ ����
	 */
	public void StartHandle(ActionEvent e){
		if(!startPressed){
			//������ ��������
			movement = new Timeline(new KeyFrame(Duration.millis(125),
					new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if(isEasy())
						movement.setRate(0.5);//��������� �������� �����
					if(isMedium())
						movement.setRate(1);  //��������� �������� �� ���������
					if(isHard())
						movement.setRate(5);  //�������� �������� � ���� ���
					main.GameCicle();
				}
			}));
			movement.setCycleCount(Timeline.INDEFINITE); //��������� ���������� ����������
			movement.play();							 //��������� ��������
		}
		startPressed = true;							 //������������� ���� ������� ������ start
	}
	
	/**
	 * ������������� ����� ��������������
	 * ��������� ����������� ����
	 * @param e ������� ������� ������ ����
	 */
	@FXML
	public void PlayLastGameHandler(ActionEvent e){
		if(!startPressed){
			retrospective = true;
			StartHandle(e); //��������� ����
		}
	}
	
	@FXML
	/**
	 * ��������� ������� ������ Show
	 * @param e ������� ������� ������ ����
	 */
	public void ShowLastGameHandle(ActionEvent e){
		System.out.println("Show last game!");
	}
	
	@FXML
	/**
	 * ��������� ������� ������ End
	 * @param e ������� ������� ������ ����
	 */
	public void EndHandle(ActionEvent e){
		if(startPressed){
			main.End();
		}
	}	
	
	/**
	 * ������������� ������
	 */
	public void StopTheGame(){
		movement.stop();
	}
	
	public double GetAppleX(){
		return apple.getLayoutX();
	}
	
	public double GetAppleY(){
		return apple.getLayoutY();
	}

	public void SetAppleX(int x){
		apple.setLayoutX(x);
	}
	
	public void SetAppleY(int y){
		apple.setLayoutY(y);
	}
	
	public double GetHeadX(){
		return head.getLayoutX();
	}
	
	public double GetHeadY(){
		return head.getLayoutY();
	}
	
	public void SetHeadX(int x){
		head.setLayoutX(x);
	}
	
	public void SetHeadY(int y){
		head.setLayoutY(y);
	}
	
	public void SetText(String mess){
		text.setText(mess);
	}
	
	public void SetSteps(String mess){
		steps.setText(mess);
	}
	
	public void SetFuture(String mess){
		future.setText(mess);
	}
	
	/**
	 * �������� ������ �� �������� ����������
	 * @param offsetX �������� �� ��� X
	 * @param offsetY �������� �� ��� Y
	 */
	public void MoveHead(double offsetX, double offsetY){
		head.relocate(offsetX+head.getLayoutX(), offsetY+head.getLayoutY());
	}
	
	public int GetWhereSnakeGoes(){
		return WhereSnakeGoes;
	}
	
	/**
	 * ������� ���������� �������� ���� (������)
	 * @param arg ����� ����������� �������� ����
	 */
	public void SetWhereSnakeGoes(int arg){
		WhereSnakeGoes = arg;
	}
	
	/**
	 * ��������� ����������� ������
	 */
	public void RelocateApple(){
		Random r = new Random();
		double x = 0, y = 0;
		while(x<=10)
			x = r.nextDouble()*590;
		while(y<=30)
			y = r.nextDouble()*390;
		apple.relocate((int)x, (int)y);
	}
	
	/**
	 * ��������� �� ���� � �������������� ������
	 * @return �������� ���������
	 */
	public boolean isAuto(){
		if(autoPlay.isSelected())
			return true;
		else
			return false;
	}
	
	/**
	 * �������� �� ��������������� ������� ����
	 * @return �������� ���������
	 */
	public boolean isRetrospective(){
		return retrospective;
	}
	
	public boolean isEasy(){
		return easy.isSelected();
	}
	
	public boolean isMedium(){
		return medium.isSelected();
	}
	
	public boolean isHard(){
		return hard.isSelected();
	}
	
	@FXML
	public void KeyboardHandler(KeyEvent e){
		if(!isAuto()){
			//������ ������� �����
			if(e.getCode() == KeyCode.UP && GetWhereSnakeGoes() != 2){
				SetWhereSnakeGoes(0);
			}
			//������ ������� ����
			if(e.getCode() == KeyCode.DOWN && GetWhereSnakeGoes() != 0){
				SetWhereSnakeGoes(2);
			}
			//������ ������� �����
			if(e.getCode() == KeyCode.LEFT && GetWhereSnakeGoes() != 1){
				SetWhereSnakeGoes(3);
			}
			//������ ������� ������
			if(e.getCode() == KeyCode.RIGHT && GetWhereSnakeGoes() != 3){
				SetWhereSnakeGoes(1);
			}
		}
	}
	
	public int whatSorting(){
		if(scalaSort.isSelected()){
			return 1;
		}
		else{
			if(javaSort.isSelected()){
				return 2;
			}
			else{
				return 0;
			}
		}
	}
	
	public int whatTypeOfSorting(){
		if(bestToWorst.isSelected()){
			return 0;
		}
		else{
			return 1;
		}
	}
	
	public boolean isAlternativeSort(){
		return alternativeSort.isSelected();
	}
}
