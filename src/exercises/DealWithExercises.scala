package exercises

import scala.annotation.tailrec
import main.Time.getDate
import user._
import user.User_Utils._

object DealWithExercises {

  def treatsExercise(lines: List[String]): List[Exercise] = lines match {
    case Nil => List()
    case head :: tail =>
      val exercise = head.split('-')
      exercise(1) match {
        case "aerobics" => Exercise(exercise(0), exercise(2).toDouble, aerobics) :: treatsExercise(tail)
        case "strength" => Exercise(exercise(0), exercise(2).toDouble, strength) :: treatsExercise(tail)
        case "flexibility" => Exercise(exercise(0), exercise(2).toDouble, flexibility) :: treatsExercise(tail)
      }
  }

  @tailrec
  def addPlanToHistory(user: User, p : List[(Exercise, Int, Int)]): User = p match {
    case Nil => user
    case head :: tail => addPlanToHistory(user.updateExerciseHistory(head._1, head._2, getDate()), tail)
  }

  def getRandomPlan(user : User, exList : List[Exercise]): List[(Exercise, Int, Int)] = {
    if(user.objective == LoseWeight){
      val exercises = getExercises(exList, aerobics, 3) ++ getExercises(exList, strength, 1) ++ getExercises(exList, flexibility, 1)
      getBurnedCal(exercises, user)
    }else{
      if(user.objective == MaintainWeight){
        val exercises = getExercises(exList, aerobics, 2) ++ getExercises(exList, strength, 2) ++ getExercises(exList, flexibility, 2)
        getBurnedCal(exercises, user)
      }else{
        val exercises = getExercises(exList, aerobics, 1) ++ getExercises(exList, strength, 4) ++ getExercises(exList, flexibility, 1)
        getBurnedCal(exercises, user)
      }
    }
  }

  def getExercises[A](list : List[Exercise], exType : ExercisesTypes, number : Int): List[Exercise] ={
    if(number > 0){
      if(exType == aerobics) {
        val nL = list.filter(x => x.exerciseType == aerobics)
        val r = (Math.random()*nL.length).toInt
        nL(r) :: getExercises(removeFromList(nL(r), list), exType, number-1)
      }else {
        if (exType == strength) {
          val nL = list.filter(x => x.exerciseType == strength)
          val r = (Math.random() * nL.length).toInt
          nL(r) :: getExercises(removeFromList(nL(r), list), exType, number - 1)
        }else{
          val nL = list.filter(x => x.exerciseType == flexibility)
          val r = (Math.random()*nL.length).toInt
          nL(r) :: getExercises(removeFromList(nL(r), list), exType, number-1)
        }
      }
    }else{
      Nil
    }
  }

  def removeFromList(e : Exercise, l : List[Exercise]): List[Exercise] = {
    l match {
      case Nil => Nil
      case head :: tail => if (head.name == e.name) removeFromList(e, tail) else head :: removeFromList(e, tail)
    }
  }

  def getBurnedCal(l : List[Exercise], user: User): List[(Exercise,Int, Int)] = l.map(h => (h, ((20 * (h.met * 3.5 * user.body.weight)) / 200).toInt, 20))

  def showUserPlan(l : List[(Exercise, Int, Int)]): List[(Exercise, Int)] = l match {
    case Nil => Nil
    case head :: tail => (head._1, head._3) :: showUserPlan(tail)
  }
}
