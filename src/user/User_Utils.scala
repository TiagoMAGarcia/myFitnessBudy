package user

import java.io.{File, FileInputStream, FileOutputStream, ObjectInputStream, ObjectOutputStream}

import exercises.DealWithExercises.treatsExercise
import exercises.Exercise
import foods.DealWithFood.{readFile, treatsFood}
import foods.Food
import main.Time.getDate
import scala.io.Source
import scala.io.StdIn.readLine

abstract class Read {

  def readFile(file: String): List[String] = {
    val bufferedSource = Source.fromFile(file)
    val lines = bufferedSource.getLines().toList
    bufferedSource.close()
    lines
  }
}

object User_Utils {

  val fileUsers = "./src/FilesTXT/Users"
  val listExercises: List[Exercise] = treatsExercise(readFile("./src/FilesTXT/ExercisesList.txt"))
  val listFoods: List[Food] = treatsFood(readFile("./src/FilesTXT/Foods.txt"))
  val listRecipes : List[Food] = treatsFood(readFile("./src/FilesTXT/Recipes.txt"))


  def showPlan(l : List[(Exercise, Int)]): List[String] = {
    l match {
      case Nil => Nil
      case head :: tail =>
        (head._1.name + "-" + head._2 + "Min") :: showPlan(tail)

    }
  }


  def showMyProfile(user: User): String = {
    ("⚫ Name: " + user.name + "\n⚫ Password: " + user.password + "\n⚫ Height: " + user.body.height + "\n⚫ Weight: "
      + user.body.weight + "\n⚫ Age: " + user.body.age + "\n⚫ Gender: " + user.body.gender + "\n⚫ Activity Level: "
      + user.activityLevel + "\n⚫ Objective: " + user.objective + "\n\n")
  }

  def getUserInput(): String = readLine.trim.toUpperCase

  def printCalCart(s : String): String = "You have " + s + " calories in your cart"

  def printIngredient(recipe: Food): String = {
    recipe.recipe match {
      case None => ""
      case Some(value) =>
        val s = value._1.replace(',', '\n')
        "\nIngredients: \n " + s
    }
  }

  def printDirections(recipe: Food): String = {
    recipe.recipe match {
      case None => ""
      case Some(value) =>
        val s = value._2.replace(',', '\n')
        "\nIngredients: \n " + s
    }
  }

  def printNutrition(recipe: Food): String = "Recipe Nutrition:\nCalories: " + recipe.cal + "\nProtein: " + recipe.prot


  def writeToUser(user: User): Unit = {
    val list = readUsers(fileUsers) match {
      case Some(value) => value
      case None => List()
    }
    val out = new ObjectOutputStream(new FileOutputStream(new File(fileUsers)))
    out.writeObject(list.appended(user))
    out.close()
  }

  def readUsers(file: String): Option[List[User]] = {
    try {
      val in = new ObjectInputStream(new FileInputStream(new File(file)))
      val list = in.readObject().asInstanceOf[List[User]]
      Some(list)
    } catch {
      case _: Throwable => None
    }
  }

  def checkIfExists(usersList: List[User], name: String): Boolean = (usersList foldRight true) ((user, tail) => !user.name.equals(name) && tail)

  def getTodayCalories(user: User): String = {
    val totalFoodCalories = user.history.food.filter(x => x._2.equals(getDate())).foldLeft(0)((c, f) => (c + f._1.cal).toInt)
    val totalExerciseCalories = user.history.exercise.filter(x => x._3.equals(getDate())).foldLeft(0)((c, f) => (c + f._2).toInt)
    user.dailyObjective + " (Goal) - " + totalFoodCalories + " (Food) + " + totalExerciseCalories + " (Exercise) = " + (user.dailyObjective + totalExerciseCalories - totalFoodCalories) + " (Remaining)"
  }

  def printList[A](list: List[A], number: Int): List[A] = {
    list match {
      case Nil => Nil
      case head :: tail =>
        head match {
          case food: Food =>
            println(number + ". " + food.name)
          case _ =>
            println(number + ". " + head.asInstanceOf[Exercise].name)
        }
        head :: printList(tail, number + 1)

    }
  }

  def printHistory[A](list: List[A]): List[String] = {
    list match {
      case Nil => Nil
      case head :: tail =>
        head match {
          case tuple: (Exercise, Double, String) => ("Date: " + tuple._3 + " Exercise: " + tuple._1.name + " Calories burned: " + tuple._2 + "\n") :: printHistory(tail)
          case _ => ("Date: " + head.asInstanceOf[(Food, String)]._2 + " Food: " + head.asInstanceOf[(Food, String)]._1.name + " Calories consumed: " + head.asInstanceOf[(Food, String)]._1.cal + "\n") :: printHistory(tail)
        }
    }
  }

  def logout(user: User): Unit = {
    val usersList = readUsers(fileUsers)
    val newUsersList: List[User] = usersList match {
      case None => List()
      case Some(value) => value.map(x => if (x.name.equals(user.name)) user else x)
    }
    val out = new ObjectOutputStream(new FileOutputStream(new File(fileUsers)))
    out.writeObject(newUsersList)
  }

}


