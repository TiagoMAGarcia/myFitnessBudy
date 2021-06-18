package foods

import user.{Read, User}
import user.User_Utils._
import scala.annotation.tailrec

object DealWithFood extends Read {

  def treatsFood(lines: List[String]): List[Food] = lines match {
    case Nil => List()
    case head :: tail =>
      val food = head.split('-')
      food(3) match {
        case "Dairy" => Food(food(0), food(1).toDouble, food(2).toDouble, dairy, None) :: treatsFood(tail)
        case "FatAndOils" => Food(food(0), food(1).toDouble, food(2).toDouble, fatAndOils, None) :: treatsFood(tail)
        case "Proteins" => Food(food(0), food(1).toDouble, food(2).toDouble, protein, None) :: treatsFood(tail)
        case "FruitsAndVegetables" => Food(food(0), food(1).toDouble, food(2).toDouble,
          fruitsAndVegetables, None) :: treatsFood(tail)
        case "CerealsAndDerivatives" => Food(food(0), food(1).toDouble, food(2).toDouble,
          cerealsAndDerivatives, None) :: treatsFood(tail)
        case "Drinks" => Food(food(0), food(1).toDouble, food(2).toDouble, drinks, None) :: treatsFood(tail)
        case "SugarProducts" => Food(food(0), food(1).toDouble, food(2).toDouble, sugarProducts, None) :: treatsFood(tail)
        case "SaltProducts" => Food(food(0), food(1).toDouble, food(2).toDouble, saltProducts, None) :: treatsFood(tail)
        case "Leguminous" => Food(food(0), food(1).toDouble, food(2).toDouble, leguminous, None) :: treatsFood(tail)
        case "Breakfast" => Food(food(0), food(1).toDouble, food(2).toDouble, breakfast, Option(food(4),food(5))) :: treatsFood(tail)
        case "HighProtein" => Food(food(0), food(1).toDouble, food(2).toDouble, highProtein, Option(food(4),food(5))) :: treatsFood(tail)
        case "Vegetarian" => Food(food(0), food(1).toDouble, food(2).toDouble, vegetarian, Option(food(4),food(5))) :: treatsFood(tail)
        case "LowCarb" => Food(food(0), food(1).toDouble, food(2).toDouble, lowCarb, Option(food(4),food(5))) :: treatsFood(tail)
        case "GlutenFree" => Food(food(0), food(1).toDouble, food(2).toDouble, glutenFree, Option(food(4),food(5))) :: treatsFood(tail)
      }
  }

  def filterFoodList(foodList : List[Food], category: FoodCategories): List[Food]  = {
    val food = foodList.filter(x => x.foodCategory == category)
    printList(food, 1)
  }

  def getFoodByName(l : List[String], foodList : List[Food]): List[Food] = l match {
    case Nil => Nil
    case head :: tail => foodList.filter(x => x.name.equals(head)) ++ getFoodByName(tail, foodList)
  }

  def checkCartCalories(food: List[Food]): String = {
    val cals = food.foldLeft(0)((c, h) => (c + h.cal).toInt)
    printCalCart(cals.toString)
  }

  def getRecipeByName(string: String): Food = listRecipes.filter(x => x.name.equals(string)).head

}
