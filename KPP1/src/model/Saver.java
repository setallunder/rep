package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ����� ��� ���������� ���� � ���� � �� ������
 * @author Sanyadovskiy
 * @version 1.0
 */
public class Saver {
	/**
	 * ���������� � ����
	 * @param what ������, ������� ���������
	 * @param where ������, ���������� ���� � �����
	 */
	public static void Save(String what, String where){
		File notation = new File(where);//������� ����
		//���� ���� ����������, ������� ��� ����������
		try {
			FileWriter out = new FileWriter(notation);
			out.write(what); //���������� ������
			out.close();	 //��������� ����
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �������� ������ �� �����
	 * @param from ������, ���������� ���� � �����
	 * @return ������, ���������� �� �����, ���� ���� � ����� � ������ ������
	 */
	public static String Load(String from){
		File notation = new File(from); //��������� ����
		String str = from;
		try {
			FileReader in = new FileReader(notation);
			BufferedReader buffer = new BufferedReader(in);
			str = buffer.readLine();	//������ ������
			buffer.close();				//��������� ����
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
}
