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
  def SortNotationBTW(notation : java.lang.StringBuilder) : java.lang.StringBuilder = {
    var game = 0  //счётчик игр
    var entryes = new java.util.Vector [Int]          //точки входа для игр
    var successfulness = new java.util.Vector [Int]   //успушность игр
    var sortedNotation = new java.lang.StringBuilder  //буфер для отсортированной нотации
    entryes.add(0)        //добавляем первую точку входа
    successfulness.add(0) //добавляем успешность первой игры
    
    @tailrec
    // Функция для рекурсивного прохода по нотации, записи точек входа и успешности игр
    def CollectInf(i : Int) : Unit = {
      if (notation.charAt(i) == 'A') {
        successfulness.set(game, successfulness.get(game)+1)
      }
      else{
        if (notation.charAt(i) == 'E'){
          game+=1
          entryes.add(i+1)
          successfulness.add(0)
        }
      }
      if (i+1 != notation.length()){
        CollectInf(i+1)
      }
    }
    
    CollectInf(0)
    
    @tailrec
    //Функция рекурсивного составления отсортированной нотации
    def WriteOrderedBTW(i : Int) : Unit = {
      var changed : Boolean = false
      var j = 0
      var bestGame = 0
      var maxScore = -1
      while (j < game){
        if (successfulness.get(j) > maxScore){
          changed = true
          maxScore = successfulness.get(j)
          bestGame = j
        }
        j+=1
      }
      if (changed){
        changed = false
        successfulness.set(bestGame,-1)
        j = entryes.get(bestGame)
        while(notation.charAt(j) != 'E'){
          sortedNotation.append(notation.charAt(j))
          j+=1
        }
        sortedNotation.append('E')
        if (i+1 < entryes.size()){
          WriteOrderedBTW(i+1)
        }
      }
    }
    WriteOrderedBTW(0)
    sortedNotation
  }
  
  def SortNotationWTB(notation : java.lang.StringBuilder) : java.lang.StringBuilder = {
    var game = 0
    var entryes = new java.util.Vector [Int]
    var successfulness = new java.util.Vector [Int]
    var sortedNotation = new java.lang.StringBuilder
    entryes.add(0)
    successfulness.add(0)
    @tailrec
    def CollectInf(i : Int) : Unit = {
      if (notation.charAt(i) == 'A') {
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
      var minScore = 1000000
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
        successfulness.set(worstGame,1234567)
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