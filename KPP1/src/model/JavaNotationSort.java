package model;

import java.util.Vector;

/**
 * Класс для сортировки нотации
 * содержит статические медоты для сортировки
 * @author Sanyadovskiy
 *
 */
public class JavaNotationSort {
	/**
	 * Метод для сортировки нотации
	 * @param notation	сырая нотация
	 * @param reverse	режим
	 * @return	готовую нотацию
	 */
	public static StringBuilder sort(StringBuilder notation, boolean reverse, boolean isAlternative){
		boolean foundMax = false;
		StringBuilder sortedNotation = new StringBuilder();
		int i = 0, j = 0, maxScore = 0, bestGame = 0;
		Vector<Integer> entryes = new Vector<>();			//коорднаты игр
		Vector<Integer> successfulness = new Vector<>();	//успешность игр
		while(i < notation.length()){
			entryes.add(i);						//добавляем новую точку входа
			successfulness.add(0);				//и нулевую успешность
			while(notation.charAt(i) != 'E'){	//пока не конец игры
				if(notation.charAt(i) == 'A' && !isAlternative || 
						isAlternative && notation.charAt(i) >= 'A' && notation.charAt(i) <= 'Z' ){	//увеличиваем успешность с каждым событием роста
					successfulness.set(j, successfulness.get(j)+1);
				}
				i++;
			}
			i++;
			j++;
		}
		do{
			i = j = maxScore = bestGame = 0;
			if(reverse)
				maxScore = 200000000;						//невозможное количество событий роста
			foundMax = false;
			while(i < successfulness.size()){			//ищем самую успешную игру 
				if(!reverse && successfulness.get(i) >= maxScore || reverse && successfulness.get(i) < maxScore){
					maxScore = successfulness.get(i);	//запоминаем счет
					bestGame = i;						//и номер игры
					foundMax = true;					//сигнализируем о наличии максимального
				}
				i++;
			}
			if(foundMax){								//если было найдено максимальное
				if(!reverse)
					successfulness.set(bestGame,-1);	//сбрасываем счет найденной игры
				else
					successfulness.set(bestGame,200000000);//недостижимый счет никогда не будет найден
				j = entryes.get(bestGame);				//становимся в начало найденной игры
				while(notation.charAt(j) != 'E'){		//пока не конец игры
					sortedNotation.append(notation.charAt(j));	//переписываем игру в новый буфер
					j++;
				}
				sortedNotation.append('E');				//закрываем игру соответствующим символом
			}
		}while(foundMax);	//пока находятся максимальные
		return sortedNotation;
	}
}
