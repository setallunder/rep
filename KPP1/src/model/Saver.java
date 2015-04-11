package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс для сохранения сток в файл и их чтения
 * @author Sanyadovskiy
 * @version 1.0
 */
public class Saver {
	/**
	 * Сохранение в файл
	 * @param what Строка, которую сохраняем
	 * @param where Строка, содержащая путь к файлу
	 */
	public static void Save(String what, String where){
		File notation = new File(where);//создаем файл
		//если файл существует, удаляем для перезаписи
		try {
			FileWriter out = new FileWriter(notation);
			out.write(what); //записываем строку
			out.close();	 //закрываем файл
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Загрузка строки из файла
	 * @param from Строка, содержащая путь к файлу
	 * @return Строку, полученную из файла, либо путь к файлу в случае ошибки
	 */
	public static String Load(String from){
		File notation = new File(from); //открываем файл
		String str = from;
		try {
			FileReader in = new FileReader(notation);
			BufferedReader buffer = new BufferedReader(in);
			str = buffer.readLine();	//читаем строку
			buffer.close();				//закрываем файл
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}
