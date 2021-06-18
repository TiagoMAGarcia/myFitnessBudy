package user

import exercises.Exercise
import foods.Food
import main.Time.getDate

case class History(exercise: List[(Exercise, Double, String)], food: List[(Food, String)])

case class Body(height: Int, weight: Int, age: Int, gender: String)

case class User(name: String, password: String, body: Body, activityLevel: ActivityLevelTypes,
                objective: Objectives, dailyObjective : Int, history: History, plan : List[(Exercise, Int, Int)]){

  def updateUser(newHeight : Int, newWeight: Int, newAge: Int, newGender: String, newAL: ActivityLevelTypes, newObj: Objectives, d : Int): User =
    User.updateUser(this, newHeight, newWeight, newAge, newGender, newAL, newObj, d)

  def updateFoodHistory(list: List[Food]) : User = User.updateFoodHistory(this, list)

  def updateRecipeHistory(recipe : Food) : User = User.updateRecipeHistory(this, recipe)

  def updateExerciseHistory(newExercise : (Exercise, Double, String)) : User = User.updateExerciseHistory(this, newExercise)

  def updatePlan(p: List[(Exercise, Int, Int)]): User = User.updatePlan(this, p)
}

object User {
  type Name = String
  type Password = String
  type Height = Int
  type Weight = Int
  type Age = Int
  type Gender = String
  type ActivityLevel = ActivityLevelTypes
  type objective = Objectives
  type history = History




  def updateUser(user: User, newHeight : Int, newWeight: Int, newAge: Int, newGender: String, newAL: ActivityLevelTypes, newObj: Objectives, d : Int): User =
    user.copy(body = Body(newHeight, newWeight, newAge, newGender), activityLevel = newAL, objective = newObj, dailyObjective = d)

  def updateFoodHistory(user : User, list: List[Food]): User = user.copy(history = History(user.history.exercise, list.map(x => (x, getDate())) ++ user.history.food))

  def updateExerciseHistory(user : User, newExercise : (Exercise, Double, String)) : User =
    user.copy(history = History(newExercise :: user.history.exercise, user.history.food))

  def updatePlan(user: User, p : List[(Exercise, Int, Int)]): User = user.copy(plan = p)

  def updateRecipeHistory(user : User, recipe : Food): User = user.copy(history = History(user.history.exercise, (recipe, getDate()) :: user.history.food))

}

