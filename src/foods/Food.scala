package foods

case class Food(name: String, cal: Double, prot: Double, foodCategory: FoodCategories, recipe : Option[(String, String)])

sealed trait FoodCategories

case object dairy extends FoodCategories
case object fatAndOils extends FoodCategories
case object protein extends FoodCategories
case object fruitsAndVegetables extends FoodCategories
case object cerealsAndDerivatives extends FoodCategories
case object drinks extends FoodCategories
case object sugarProducts extends FoodCategories
case object saltProducts extends FoodCategories
case object leguminous extends FoodCategories
case object breakfast extends FoodCategories
case object highProtein extends FoodCategories
case object vegetarian extends FoodCategories
case object lowCarb extends FoodCategories
case object glutenFree extends FoodCategories
