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
 * Класс контроллер
 * Помещает в себе данные, загруженные из fxml файла
 * @author Sanyadovskiy
 * @version 1.0
 */
public class SnakesWindowController {
	@FXML
	private AnchorPane field; //основное поле
	@FXML
	private Circle apple;
	@FXML
	private Rectangle head; //голова змеи
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
	
	private Main main; //обратная связь с main классом
	
	private boolean startPressed = false; //флаг нажатия кнопки start
	
	private boolean retrospective = false;
	
	private int WhereSnakeGoes = 0; //направление движения змеи
	
	Timeline movement; //вызывает запросы на движение змеи
	
	/**
	 * конструктор по умолчанию
	 */
	public SnakesWindowController(){
	}
	
	@FXML
	private void initialize(){
	}
	
	/**
	 * получение ссылки на main
	 * @param main ссылка на main класс
	 */
	public void setMain(Main main) {
		this.main = main;
	}
	
	@FXML
	/**бработка нажатия кнопки start
	 * @param e событие нажатой кнопки меню
	 */
	public void StartHandle(ActionEvent e){
		if(!startPressed){
			//создам анимацию
			movement = new Timeline(new KeyFrame(Duration.millis(125),
					new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					if(isEasy())
						movement.setRate(0.5);//замедляем анимацию вдвое
					if(isMedium())
						movement.setRate(1);  //оставляем значение по умолчанию
					if(isHard())
						movement.setRate(5);  //ускоряем анимацию в пять раз
					main.GameCicle();
				}
			}));
			movement.setCycleCount(Timeline.INDEFINITE); //количесво повторение бесконечно
			movement.play();							 //запускаем анимацию
		}
		startPressed = true;							 //устанавливаем флаг нажатия кнопки start
	}
	
	/**
	 * Устанавливает режим вопроизведения
	 * последней завершенной игры
	 * @param e событие нажатой кнопки меню
	 */
	@FXML
	public void PlayLastGameHandler(ActionEvent e){
		if(!startPressed){
			retrospective = true;
			StartHandle(e); //запускаем игру
		}
	}
	
	@FXML
	/**
	 * обработка нажатия кнопки Show
	 * @param e событие нажатой кнопки меню
	 */
	public void ShowLastGameHandle(ActionEvent e){
		System.out.println("Show last game!");
	}
	
	@FXML
	/**
	 * обработка нажатия кнопки End
	 * @param e событие нажатой кнопки меню
	 */
	public void EndHandle(ActionEvent e){
		if(startPressed){
			main.End();
		}
	}	
	
	/**
	 * Останавливает таймер
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
	 * смещение головы на заданное отклонение
	 * @param offsetX смещение по оси X
	 * @param offsetY смешение по оси Y
	 */
	public void MoveHead(double offsetX, double offsetY){
		head.relocate(offsetX+head.getLayoutX(), offsetY+head.getLayoutY());
	}
	
	public int GetWhereSnakeGoes(){
		return WhereSnakeGoes;
	}
	
	/**
	 * задание направлния движения змеи (головы)
	 * @param arg новое направление движения змеи
	 */
	public void SetWhereSnakeGoes(int arg){
		WhereSnakeGoes = arg;
	}
	
	/**
	 * случайное перемещение яблока
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
	 * находится ли игра в автоматическом режиме
	 * @return значение состояния
	 */
	public boolean isAuto(){
		if(autoPlay.isSelected())
			return true;
		else
			return false;
	}
	
	/**
	 * Включено ли воспроизведение прошлой игры
	 * @return значение состояния
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
			//нажата клавиша ВВЕРХ
			if(e.getCode() == KeyCode.UP && GetWhereSnakeGoes() != 2){
				SetWhereSnakeGoes(0);
			}
			//нажата клавиша ВНИЗ
			if(e.getCode() == KeyCode.DOWN && GetWhereSnakeGoes() != 0){
				SetWhereSnakeGoes(2);
			}
			//нажата клавиша ВЛЕВО
			if(e.getCode() == KeyCode.LEFT && GetWhereSnakeGoes() != 1){
				SetWhereSnakeGoes(3);
			}
			//нажата клавиша ВПРАВО
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
