package model;
/**
 *  ласс отвечающий за "искуственный интеллект"
 * ќпредел€ет поведение игрока-компьютера
 * @author Sanyadovskiy
 * @version 1.0
 */
public class AI extends java.lang.Object{
	/**
	 * ѕрин€тие решени€ о следующем шаге
	 * @param appleX X координата €блока
	 * @param appleY Y координата €блока
	 * @param headX X координата головы змеи
	 * @param headY Y координата головы змеи
	 * @param direction направление движени€ змеи
	 * @return следующий шаг
	 */
	public static int MakeMove(double appleX, double appleY, double headX, double headY, int direction){
		int step = direction;
		switch (direction){
		case 0:
			//€блоко впереди
			if(headY - appleY > 1)
				step = 0;
			//€блоко позади
			if(headY - appleY < -10)
				if(headX > 300) //если на правой половине пол€
					step = 3;
				else			//если на левой стороне пол€
					step = 1;
			//если зме€ поравн€лась с €блоком
			if(headY - appleY <= 1 && headY - appleY >= -10)
				if(headX - appleX > 0) //€блоко слева
					step = 3;
				else				   //€блоко справа
					step = 1;
			break;
		case 1:
			//€блоко впереди
			if(headX - appleX < -1)
				step = 1;
			//€блоко позади
			if(headX - appleX > 10)
				if(headY > 200) //если на нижней половине пол€
					step = 0;
				else			//если на верхней стороне пол€
					step = 2;
			//если зме€ поравн€лась с €блоком
			if(headX - appleX >= -1 && headX - appleX <= 10)
				if(headY - appleY > 0) //€блоко сверху
					step = 0;
				else				   //€блоко снизу
					step = 2;
			break;
		case 2:
			//€блоко впереди
			if(headY - appleY < -1)
				step = 2;
			//€блоко позади
			if(headY - appleY > 10)
				if(headX > 300) //если на правой половине пол€
					step = 3;
				else			//если на левой стороне пол€
					step = 1;
			//если зме€ поравн€лась с €блоком
			if(headY - appleY >= -1 && headY - appleY <= 10)
				if(headX - appleX > 0) //€блоко слева
					step = 3;
				else				   //€блоко справа
					step = 1;
			break;
		case 3:
			//€блоко впереди
			if(headX - appleX > 1)
				step = 3;
			//€блоко позади
			if(headX - appleX < -10)
				if(headY > 200) //если на нижней половине пол€
					step = 0;
				else			//если на верхней стороне пол€
					step = 2;
			//если зме€ поравн€лась с €блоком
			if(headX - appleX <= 1 && headX - appleX >= -10)
				if(headY - appleY > 0) //€блоко сверху
					step = 0;
				else			   	   //€блоко снизу
					step = 2;
			break;
		}
		return step;
	}
	
	/**
	 * ѕодсчитывает количество шагов до цели
	 * @param appleX X координата €блока    
	 * @param appleY Y координата €блока    
	 * @param headX X координата головы змеи
	 * @param headY Y координата головы змеи
	 * @return количество шагов до цели
	 */
	public static int StepsToApple(double appleX, double appleY, double headX, double headY){
		double offsetX = Math.abs(appleX - headX);
		double offsetY = Math.abs(appleY - headY);
		int countSteps = (int) (offsetX/10 + offsetY/10);
		return countSteps;
	}
}
