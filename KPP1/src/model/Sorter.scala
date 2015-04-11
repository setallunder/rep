package model

import scala.annotation.tailrec

/**
 * Объект-сортировщик нотации
 * содержит медоты для сортировки
 * @author Sanyadovskiy
 */
object Sorter {
  /**
   * Метод для сортировки нотации при помощи хвостовой рекурсии
   * @param notation сырая нотация
   * @return отсортированную нотацию 
   */
  def SortNotationBTW(notation : java.lang.StringBuilder, isAlternative : Boolean) : java.lang.StringBuilder = {
    var game = 0  //счётчик игр
    var entryes = new java.util.Vector [Int]          //точки входа для игр
    var successfulness = new java.util.Vector [Int]   //успушность игр
    var sortedNotation = new java.lang.StringBuilder  //буфер для отсортированной нотации
    entryes.add(0)        //добавляем первую точку входа
    successfulness.add(0) //добавляем успешность первой игры
    
    @tailrec
    // Функция для рекурсивного прохода по нотации, записи точек входа и успешности игр
    def CollectInf(i : Int) : Unit = {
      if (notation.charAt(i) == 'A' && !isAlternative || 
          isAlternative && notation.charAt(i) >= 'A' && notation.charAt(i) <= 'Z') {
        successfulness.set(game, successfulness.get(game)+1)  //наращиваем успешность игры
      }
      if (notation.charAt(i) == 'E'){   //если проверяемая игра закончилась
        game+=1                         //наращиваем счетчик игр
        entryes.add(i+1)                //добавляем новую точку входа
        successfulness.add(0)           //и новый счетчик успеха
      }
      if (i+1 != notation.length()){      //если есть ещё игры, продолжаем парсинг
        CollectInf(i+1)
      }
    }
    
    CollectInf(0)  //вызываем саму функцию
    
    @tailrec
    //Функция рекурсивного составления отсортированной нотации
    def WriteOrderedBTW(i : Int) : Unit = {
      var changed : Boolean = false   //была ли найдена новая максимальная игра
      var j = 0
      var bestGame = 0    //номер лучшей игры
      var maxScore = -1   //счет лучшей игры
      while (j < game){   //ищем лучшую игру
        if (successfulness.get(j) > maxScore){
          changed = true                    //устанавливаем флаг того, что найден результат
          maxScore = successfulness.get(j)  //запоминаем лучший счет
          bestGame = j                      //и номер лучшей игры
        }
        j+=1
      }
      if (changed){                        //если была найдена лучшая игра
        changed = false                    //сбрасываем флаг того, что была найдена лучшая игра
        successfulness.set(bestGame,-1)    //и сбрасываем счет лучшей игры
        j = entryes.get(bestGame)          //становимся в начало этой игры
        while(notation.charAt(j) != 'E'){  //переписываем её в буфер
          sortedNotation.append(notation.charAt(j))
          j+=1
        }
        sortedNotation.append('E')         //завершаем игру спец. символом
        if (i+1 < entryes.size()){         //если ещё есть игры, продолжаем формирование нотации
          WriteOrderedBTW(i+1)
        }
      }
    }
    WriteOrderedBTW(0)  //сам вызов функции
    sortedNotation      //возвращаем результат
  }
  
  //функция сортировки от худшей игры к лучшей, почти идентична предыдущей
  def SortNotationWTB(notation : java.lang.StringBuilder, isAlternative : Boolean) : java.lang.StringBuilder = {
    var game = 0
    var entryes = new java.util.Vector [Int]
    var successfulness = new java.util.Vector [Int]
    var sortedNotation = new java.lang.StringBuilder
    entryes.add(0)
    successfulness.add(0)
    
    @tailrec
    def CollectInf(i : Int) : Unit = {
      if (notation.charAt(i) == 'A' && !isAlternative || 
          isAlternative && notation.charAt(i) >= 'A' && notation.charAt(i) <= 'Z') {
        successfulness.set(game, successfulness.get(game)+1)
      }
      if (notation.charAt(i) == 'E'){
        game+=1
        entryes.add(i+1)
        successfulness.add(0)
      }
      if (i+1 != notation.length()){
        CollectInf(i+1)
      }
    }
    
    CollectInf(0)
    
    @tailrec
    def WriteOrderedWTB(i : Int) : Unit = {
      var changed : Boolean = false
      var j = 0
      var worstGame = 0
      var minScore = 2000000000  //выставляем недосягаемый счет
      while (j < game){
        if (successfulness.get(j) < minScore){
          changed = true
          minScore = successfulness.get(j)
          worstGame = j
        }
        j+=1
      }
      if (changed){
        changed = false
        successfulness.set(worstGame,2000000000)  //выставляем недосягаемый счет
        j = entryes.get(worstGame)
        while(notation.charAt(j) != 'E'){
          sortedNotation.append(notation.charAt(j))
          j+=1
        }
        sortedNotation.append('E')
        if (i+1 < entryes.size()){
          WriteOrderedWTB(i+1)
        }
      }
    }
    
    WriteOrderedWTB(0)
    sortedNotation
  }
}