package model;
/**
 * ����� ���������� �� "������������ ���������"
 * ���������� ��������� ������-����������
 * @author Sanyadovskiy
 * @version 1.0
 */
public class AI extends java.lang.Object{
	/**
	 * �������� ������� � ��������� ����
	 * @param appleX X ���������� ������
	 * @param appleY Y ���������� ������
	 * @param headX X ���������� ������ ����
	 * @param headY Y ���������� ������ ����
	 * @param direction ����������� �������� ����
	 * @return ��������� ���
	 */
	public static int MakeMove(double appleX, double appleY, double headX, double headY, int direction){
		int step = direction;
		switch (direction){
		case 0:
			//������ �������
			if(headY - appleY > 1)
				step = 0;
			//������ ������
			if(headY - appleY < -10)
				if(headX > 300) //���� �� ������ �������� ����
					step = 3;
				else			//���� �� ����� ������� ����
					step = 1;
			//���� ���� ����������� � �������
			if(headY - appleY <= 1 && headY - appleY >= -10)
				if(headX - appleX > 0) //������ �����
					step = 3;
				else				   //������ ������
					step = 1;
			break;
		case 1:
			//������ �������
			if(headX - appleX < -1)
				step = 1;
			//������ ������
			if(headX - appleX > 10)
				if(headY > 200) //���� �� ������ �������� ����
					step = 0;
				else			//���� �� ������� ������� ����
					step = 2;
			//���� ���� ����������� � �������
			if(headX - appleX >= -1 && headX - appleX <= 10)
				if(headY - appleY > 0) //������ ������
					step = 0;
				else				   //������ �����
					step = 2;
			break;
		case 2:
			//������ �������
			if(headY - appleY < -1)
				step = 2;
			//������ ������
			if(headY - appleY > 10)
				if(headX > 300) //���� �� ������ �������� ����
					step = 3;
				else			//���� �� ����� ������� ����
					step = 1;
			//���� ���� ����������� � �������
			if(headY - appleY >= -1 && headY - appleY <= 10)
				if(headX - appleX > 0) //������ �����
					step = 3;
				else				   //������ ������
					step = 1;
			break;
		case 3:
			//������ �������
			if(headX - appleX > 1)
				step = 3;
			//������ ������
			if(headX - appleX < -10)
				if(headY > 200) //���� �� ������ �������� ����
					step = 0;
				else			//���� �� ������� ������� ����
					step = 2;
			//���� ���� ����������� � �������
			if(headX - appleX <= 1 && headX - appleX >= -10)
				if(headY - appleY > 0) //������ ������
					step = 0;
				else			   	   //������ �����
					step = 2;
			break;
		}
		return step;
	}
	
	/**
	 * ������������ ���������� ����� �� ����
	 * @param appleX X ���������� ������    
	 * @param appleY Y ���������� ������    
	 * @param headX X ���������� ������ ����
	 * @param headY Y ���������� ������ ����
	 * @return ���������� ����� �� ����
	 */
	public static int StepsToApple(double appleX, double appleY, double headX, double headY){
		double offsetX = Math.abs(appleX - headX);
		double offsetY = Math.abs(appleY - headY);
		int countSteps = (int) (offsetX/10 + offsetY/10);
		return countSteps;
	}
}
