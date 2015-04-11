package model;

import java.util.Vector;

public class JavaNotationSort {
	public static StringBuilder sort(StringBuilder notation, boolean reverse){
		boolean foundMax = false;
		StringBuilder sortedNotation = new StringBuilder();
		int i = 0, j = 0, maxScore = 0, bestGame = 0;
		Vector<Integer> entryes = new Vector<>();			//��������� ���
		Vector<Integer> successfulness = new Vector<>();	//���������� ���
		while(i < notation.length()){
			entryes.add(i);						//��������� ����� ����� �����
			successfulness.add(0);				//� ������� ����������
			while(notation.charAt(i) != 'E'){	//���� �� ����� ����
				if(notation.charAt(i) == 'A'){	//����������� ���������� � ������ �������� �����
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
				maxScore = 1000000;						//����������� ���������� ������� �����
			foundMax = false;
			while(i < successfulness.size()){			//���� ����� �������� ���� 
				if(!reverse && successfulness.get(i) >= maxScore || reverse && successfulness.get(i) <= maxScore){
					maxScore = successfulness.get(i);	//���������� ����
					bestGame = i;						//� ����� ����
					foundMax = true;					//������������� � ������� �������������
				}
				i++;
			}
			if(foundMax){								//���� ���� ������� ������������
				if(!reverse)
					successfulness.set(bestGame,-1);	//���������� ���� ��������� ����
				else
					successfulness.set(bestGame,1234567);//������������ ���� ������� �� ����� ������
				j = entryes.get(bestGame);				//���������� � ������ ��������� ����
				while(notation.charAt(j) != 'E'){		//���� �� ����� ����
					sortedNotation.append(notation.charAt(j));	//������������ ���� � ����� �����
					j++;
				}
				sortedNotation.append('E');				//��������� ���� ��������������� ��������
			}
		}while(foundMax);	//���� ��������� ������������
		return sortedNotation;
	}
}
