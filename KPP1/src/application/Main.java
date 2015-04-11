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
 * Основной класс приложения
 * Организует работу
 * @author Sanyadovskiy
 * @version 1.0
 */
public class Main extends Application {
	private Stage primaryStage;	//основной класс для создания JavaFX приложения
	private SnakesWindowController controller; //долступ к классу-контроллеру
	private AnchorPane root;	//базовый класс сцены
	private ArrayList <Rectangle> parts = new ArrayList<>(); //контейнер для эл-тов хвоста
	private StringBuilder notation = new StringBuilder();
	volatile char nextStep;
	volatile char decision;
	volatile int steps;
	volatile boolean killYourself = false;
	
	/**
	 * 
	 * Поток-сервер
	 * обрабатывает следующее состояние системы,
	 * вызывает поток-"предсказатель"
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
	 * Поток-предсказатель
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
			this.primaryStage.setTitle("Snake"); //задаем название окна
			this.primaryStage.centerOnScreen();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		windowInit(); //инициализируем окно
		
		Server serv = new Server();
		serv.setDaemon(true);
		serv.start();
	}
	
	/**
	 * инициализация окна
	 */
	public void windowInit(){
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/SnakesWindow.fxml"));//загрузка данных из FXML файла
			root = (AnchorPane) loader.load(); //отождествляем загруженные данные с базовым классом будущей сцены
			
	        primaryStage.setScene(new Scene(root, 600, 400)); //создаем сцену заданных размеров
	        
	        primaryStage.show(); //показываем окно
	        
	        controller = loader.getController(); //получаем контроллер окна
	        controller.setMain(this); //даем контроллеру обратную связь с main классом
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Основной цикл игры 
	 * На основе данных, полученных от потока-сервера,
	 * осуществляет изменение состояния системы.
	 * Вызывает ИИ.
	 * Воспроизводит последнюю игру
	 */
	public void GameCicle(){
		controller.SetFuture(new Character(decision).toString());
		controller.SetSteps(new Integer(steps).toString());
		//Воспроизводится ли последняя игра
		if(!controller.isRetrospective()){
			//столкновение с яблоком
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
				parts.add(new Rectangle(10, 10, Paint.valueOf("DODGERBLUE"))); //создание новой детали хвоста
				parts.get(parts.size()-1).setStroke(Paint.valueOf("BLACK")); //окраска её границ
				//задание координат новой детали
				if(parts.size()==1){
					parts.get(0).setLayoutX(controller.GetHeadX());
					parts.get(0).setLayoutY(controller.GetHeadY());
				}
				else{
					parts.get(parts.size()-1).setLayoutX(parts.get(parts.size()-2).getLayoutX());
					parts.get(parts.size()-1).setLayoutY(parts.get(parts.size()-2).getLayoutY());
				}
				root.getChildren().add(parts.get(parts.size()-1)); //добавляем деталь в окно
			}
			//столкновение с границами или хвостом
			if(!CollisionCheck()){
				MoveParts(); //смещание всего хвоста
				//Находится ли игра в атоматическом режиме
				if(!controller.isAuto()){
					switch (nextStep){
					case 'U':
						notation.append('U'); 		//сохраняем шаг в нотации
						controller.SetText("U");
						controller.MoveHead(0, -10);//движение вверх
						break;
					case 'R':
						notation.append('R'); 		//сохраняем шаг в нотации
						controller.SetText("R");
						controller.MoveHead(10, 0); //движение вправо
						break;
					case 'D':
						notation.append('D'); 		//сохраняем шаг в нотации
						controller.SetText("D");
						controller.MoveHead(0, 10); //двиение вниз
						break;
					case 'L':
						notation.append('L'); 		//сохраняем шаг в нотации
						controller.SetText("L");
						controller.MoveHead(-10, 0);//движение влево
						break;
					}
				}
				else{
					//принятие компьютером решения
					switch(decision){
					case 'U':
						notation.append('U'); 			//сохраняем шаг в нотации
						controller.SetText("U");
						controller.SetWhereSnakeGoes(0);//направляем змею
						controller.MoveHead(0, -10); 	//движение вверх
						break;
					case 'R':
						notation.append('R'); 			//сохраняем шаг в нотациим шаг в нотации
						controller.SetText("R");
						controller.SetWhereSnakeGoes(1);//направляем змею        
						controller.MoveHead(10, 0); 	//движение вправо
						break;
					case 'D':
						notation.append('D'); 			//сохраняем шаг в нотациим шаг в нотации
						controller.SetText("D");
						controller.SetWhereSnakeGoes(2);//направляем змею        
						controller.MoveHead(0, 10); 	//двиение вниз
						break;
					case 'L':
						notation.append('L'); 			//сохраняем шаг в нотациим шаг в нотации
						controller.SetText("L");
						controller.SetWhereSnakeGoes(3);//направляем змею        
						controller.MoveHead(-10, 0); 	//движение влево
						break;
					}
				}
			}
			else{
				End(); //произошло столкновение, конец игры
			}
		}
		else{
			MoveParts();//сдвиг хвоста
			if(notation.length() == 0){
				notation.append(Saver.Load("notation.txt"));//загрузка нотации
				long curTime = System.currentTimeMillis();
				switch (controller.whatSorting()){
				case 1:	//Scala сортировка
					if(controller.whatTypeOfSorting() == 0){
						notation = Sorter.SortNotationBTW(notation);
					}
					else{
						notation = Sorter.SortNotationWTB(notation);
					}
					break;
				case 2: //Java сортировка
					if(controller.whatTypeOfSorting() == 0){
						notation = JavaNotationSort.sort(notation,false);	//Best to worst
					}
					else{
						notation = JavaNotationSort.sort(notation,true);	//Worst to best
					}
					break;
				}
				System.out.print("Time, spend on sorting: ");
				System.out.print(System.currentTimeMillis() - curTime);		//вывод производительности
				System.out.println(" ms");
			}
			//проверка очередного символа нотации
			switch(notation.charAt(0)){
			//символ релокации яблока
			case 'A':
				controller.SetText("A");
				notation.deleteCharAt(0);//убираем символ
				int i = 0;
				
				while(notation.charAt(++i) != ',');//становимся на разделитель координат
				
				//переводим координату в int и задаем её яблоку
				controller.SetAppleX(Integer.parseInt(notation.toString().substring(0, i)));
				i++;
				notation.delete(0, i);//удаляем использованную координату и разделитель
				
				i = 0;
				while(notation.charAt(i) >= '0' && notation.charAt(i) <= '9')//сдвигаемся за вторую координату
					i++;
				
				//переводим координату в int и задаем её яблоку
				controller.SetAppleY(Integer.parseInt(notation.toString().substring(0, i)));
				notation.delete(0, i);//удаляем использованную координату
				
				parts.add(new Rectangle(10, 10, Paint.valueOf("DODGERBLUE"))); //создание новой детали хвоста
				parts.get(parts.size()-1).setStroke(Paint.valueOf("BLACK")); //окраска её границ
				//задание координат новой детали
				if(parts.size()==1){
					parts.get(0).setLayoutX(controller.GetHeadX());
					parts.get(0).setLayoutY(controller.GetHeadY());
				}
				else{
					parts.get(parts.size()-1).setLayoutX(parts.get(parts.size()-2).getLayoutX());
					parts.get(parts.size()-1).setLayoutY(parts.get(parts.size()-2).getLayoutY());
				}
				root.getChildren().add(parts.get(parts.size()-1)); //добавляем деталь в окно
				break;
			//символ движения вверх
			case 'U':
				controller.SetText("U");
				controller.MoveHead(0, -10);//шаг вверх
				notation.deleteCharAt(0);	//убираем символ
				break;
			//символ движения вправо
			case 'R':
				controller.SetText("R");
				controller.MoveHead(10, 0);//шаг вправо
				notation.deleteCharAt(0);  //убираем символ
				break;
			//символ движения вниз
			case 'D':
				controller.SetText("D");
				controller.MoveHead(0, 10);//шаг вниз
				notation.deleteCharAt(0);  //убираем символ
				break;
			//символ движения влево
			case 'L':
				controller.SetText("L");
				controller.MoveHead(-10, 0);//шаг влево
				notation.deleteCharAt(0);	//убираем символ
				break;
			//символ конца нотации
			case 'E':
				controller.SetText("E");
				notation.deleteCharAt(0);	//убираем символ
				if(notation.charAt(0) == '\0')
					End();						//завершаем игру
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
	 * конец игры
	 */
	public void End(){
		try{
			//если нотация не пуста и мы не в режиме воспроизведения
			//сохраняем с помещённым символом конца нотации
			if(notation.length() != 0 && !controller.isRetrospective()){
				notation.append('E');
				notation.append(Saver.Load("notation.txt"));
				Saver.Save(notation.toString(), "notation.txt");
			}
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("../view/End.fxml")); //загрузка финального изображения
			AnchorPane end;
			end = (AnchorPane) loader.load();
			root.getChildren().add(end); //добавляем финальное изображение в окно
			controller.StopTheGame();
			killYourself = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * проверка на столкновение с краем и хвостом
	 * @return было ли столкновение
	 */
	public boolean CollisionCheck(){
		//столкновение с краем
		if(controller.GetHeadX() <= 0 || controller.GetHeadX() >= 590 || 
				controller.GetHeadY() <= 25 || controller.GetHeadY() >= 390){
			return true;
		}
		//столкновение с хвостом
		for(int i = 0; i<parts.size()-1; i++){
			if(parts.get(i).getLayoutX() == controller.GetHeadX() &&
					parts.get(i).getLayoutY() == controller.GetHeadY()){
				return true;
			}
		}
		return false; //столкновение не произошло
	}
	
	/**
	 * сдвиг хвоста
	 */
	public void MoveParts(){
		//еси хвост есть, задаем каждому эл-ту с конца координаты следующего
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
