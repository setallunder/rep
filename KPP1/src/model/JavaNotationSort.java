package model;

import java.util.Vector;

/**
 * ����� ��� ���������� �������
 * �������� ����������� ������ ��� ����������
 * @author Sanyadovskiy
 *
 */
public class JavaNotationSort {
	/**
	 * ����� ��� ���������� �������
	 * @param notation	����� �������
	 * @param reverse	�����
	 * @return	������� �������
	 */
	public static StringBuilder sort(StringBuilder notation, boolean reverse, boolean isAlternative){
		boolean foundMax = false;
		StringBuilder sortedNotation = new StringBuilder();
		int i = 0, j = 0, maxScore = 0, bestGame = 0;
		Vector<Integer> entryes = new Vector<>();			//��������� ���
		Vector<Integer> successfulness = new Vector<>();	//���������� ���
		while(i < notation.length()){
			entryes.add(i);						//��������� ����� ����� �����
			successfulness.add(0);				//� ������� ����������
			while(notation.charAt(i) != 'E'){	//���� �� ����� ����
				if(notation.charAt(i) == 'A' && !isAlternative || 
						isAlternative && notation.charAt(i) >= 'A' && notation.charAt(i) <= 'Z' ){	//����������� ���������� � ������ �������� �����
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
				maxScore = 200000000;						//����������� ���������� ������� �����
			foundMax = false;
			while(i < successfulness.size()){			//���� ����� �������� ���� 
				if(!reverse && successfulness.get(i) >= maxScore || reverse && successfulness.get(i) < maxScore){
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
					successfulness.set(bestGame,200000000);//������������ ���� ������� �� ����� ������
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
