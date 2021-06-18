package controllers

import foods.DealWithFood.{checkCartCalories, getFoodByName, getRecipeByName}
import foods._
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control._
import javafx.scene.layout.{BorderPane, GridPane}
import main.Time.getDate
import user.User
import user.User_Utils._

import scala.jdk.CollectionConverters.IterableHasAsJava

class FoodMenu {

  @FXML
  private var foodMenuPane: BorderPane = _
  @FXML
  private var backButton: Button = _
  @FXML
  private var addFoodButton: Button = _
  @FXML
  private var backToFoodMenuButton: Button = _
  @FXML
  private var addRecipeButton: Button = _
  @FXML
  private var foodHistoryButton: Button = _
  @FXML
  private var foodChoice: ComboBox[String] = _
  @FXML
  private var recipeChoice: ComboBox[String] = _
  @FXML
  private var recipeLabel: Label = _
  @FXML
  private var recipeView: ListView[String] = _
  @FXML
  private var historyView: ListView[String] = _
  @FXML
  private var addFoodPane: GridPane = _
  @FXML
  private var addRecipePane: GridPane = _
  @FXML
  private var historyPane: GridPane = _

  private val foodList: List[Food] = listFoods

  private val recipeList: List[Food] = listRecipes
  @FXML
  private var cartCalories: Label = _
  @FXML
  private var cartList: ListView[String] = _
  @FXML
  private var category: MenuButton = _
  @FXML
  private var categoriesRecipe: MenuButton = _
  @FXML
  private var user: User = _
  @FXML
  private var noFoodSelected: Label = _
  @FXML
  private var noFoodRemoveSelected: Label = _
  @FXML
  private var cartEmpty: Label = _
  @FXML
  private var recipeEmpty: Label = _
  @FXML
  private var cartEmpty1: Label = _

  def init(user: User): Unit = {
    this.user = user
    val foodsName = foodList.map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    foodChoice.setItems(data)
    val recipesName = recipeList.map(x => x.name)
    val data2: ObservableList[String] = FXCollections.observableArrayList(recipesName.asJavaCollection)
    recipeChoice.setItems(data2)
  }

  def backToHomePageFunc(): Unit = goToHomePage(user)

  def backToFoodMenuFunc(): Unit = {
    backButton.setVisible(true)
    backButton.setDisable(false)
    addFoodButton.setVisible(true)
    addFoodButton.setDisable(false)
    addRecipeButton.setVisible(true)
    addRecipeButton.setDisable(false)
    foodHistoryButton.setVisible(true)
    foodHistoryButton.setDisable(false)
    backToFoodMenuButton.setVisible(false)
    backToFoodMenuButton.setDisable(true)
    addRecipePane.setVisible(false)
    addFoodPane.setVisible(false)
    historyPane.setVisible(false)
  }

  def addFoodFunc(): Unit = {
    backButton.setVisible(false)
    backButton.setDisable(true)
    addFoodButton.setVisible(false)
    addFoodButton.setDisable(true)
    addRecipeButton.setVisible(false)
    addRecipeButton.setDisable(true)
    foodHistoryButton.setVisible(false)
    foodHistoryButton.setDisable(true)
    backToFoodMenuButton.setVisible(true)
    backToFoodMenuButton.setDisable(false)
    addFoodPane.setVisible(true)
    category.setText("Choose Category")
    foodChoice.getSelectionModel.clearSelection()
    cartList.getItems.removeAll(cartList.getItems)

    if (!cartList.getItems.isEmpty) {
      historyView.getItems.removeAll(historyView.getItems)
    }
    cartCalories.setText("")
    category.setText(category.getText.replace(category.getText, "Choose Category"))
  }

  def addRecipeFunc(): Unit = {
    backButton.setVisible(false)
    backButton.setDisable(true)
    addFoodButton.setVisible(false)
    addFoodButton.setDisable(true)
    addRecipeButton.setVisible(false)
    addRecipeButton.setDisable(true)
    foodHistoryButton.setVisible(false)
    foodHistoryButton.setDisable(true)
    backToFoodMenuButton.setVisible(true)
    backToFoodMenuButton.setDisable(false)
    addRecipePane.setVisible(true)
    recipeLabel.setText("")
    categoriesRecipe.setText("Choose Category")
    recipeChoice.getSelectionModel.clearSelection()
    if (!recipeView.getItems.isEmpty) {
      recipeView.getItems.remove(0)
    }
  }

  def foodHistoryFunc(): Unit = {
    backButton.setVisible(false)
    backButton.setDisable(true)
    addFoodButton.setVisible(false)
    addFoodButton.setDisable(true)
    addRecipeButton.setVisible(false)
    addRecipeButton.setDisable(true)
    foodHistoryButton.setVisible(false)
    foodHistoryButton.setDisable(true)
    backToFoodMenuButton.setVisible(true)
    backToFoodMenuButton.setDisable(false)
    historyPane.setVisible(true)
    if (!historyView.getItems.isEmpty) {
      historyView.getItems.removeAll(historyView.getItems)
    }
  }

  def clearCartList(): Unit = {
    if (cartList.getItems.isEmpty) cartEmpty1.setVisible(true)
    else {
      noFoodRemoveSelected.setVisible(false)
      noFoodSelected.setVisible(false)
      cartEmpty.setVisible(false)
      cartEmpty1.setVisible(false)

      cartList.getItems.removeAll(cartList.getItems)
    }
  }

  def chooseARecipeRemove(): Unit = recipeEmpty.setVisible(false)

  def ingredientsFunc(): Unit = {
    if (recipeChoice.getValue == null) recipeEmpty.setVisible(true)
    else {
      recipeEmpty.setVisible(false)
      recipeLabel.setText(recipeChoice.getValue)
      val msg = printIngredient(getRecipeByName(recipeChoice.getValue))
      if (!recipeView.getItems.isEmpty) {
        recipeView.getItems.remove(0)
      }
      recipeView.getItems.add(msg)
    }
  }

  def nutritionFunc(): Unit = {
    if (recipeChoice.getValue == null) recipeEmpty.setVisible(true)
    else {
      recipeEmpty.setVisible(false)
      recipeLabel.setText(recipeChoice.getValue)
      val msg = printNutrition(getRecipeByName(recipeChoice.getValue))
      if (!recipeView.getItems.isEmpty) {
        recipeView.getItems.remove(0)
      }
      recipeView.getItems.add(msg)
    }
  }

  def directionsFunc(): Unit = {
    if (recipeChoice.getValue == null) recipeEmpty.setVisible(true)
    else {
      recipeEmpty.setVisible(false)
      recipeLabel.setText(recipeChoice.getValue)
      val msg = printDirections(getRecipeByName(recipeChoice.getValue))
      if (!recipeView.getItems.isEmpty) {
        recipeView.getItems.remove(0)
      }
      recipeView.getItems.add(msg)
    }
  }

  def recipeToHistoryFunc(): Unit = {
    if (recipeChoice.getValue == null) recipeEmpty.setVisible(true)
    else {
      recipeEmpty.setVisible(false)
      recipeLabel.setText(recipeChoice.getValue)
      user = user.updateRecipeHistory(getRecipeByName(recipeChoice.getValue))
      backToFoodMenuFunc()
    }
  }

  def foodChoiceClicked(): Unit = {
    noFoodRemoveSelected.setVisible(false)
    noFoodSelected.setVisible(false)
    cartEmpty.setVisible(false)
    cartEmpty1.setVisible(false)
  }

  def categoryClicked(): Unit = {
    noFoodRemoveSelected.setVisible(false)
    noFoodSelected.setVisible(false)
    cartEmpty.setVisible(false)
    cartEmpty1.setVisible(false)
  }

  def todayHistoryFunc(): Unit = {
    val l = user.history.food.filter(x => x._2.equals(getDate()))
    if (!historyView.getItems.isEmpty) {
      historyView.getItems.removeAll(historyView.getItems)
    }
    printHistory(l).map(x => historyView.getItems.add(x))
  }

  def totalHistoryFunc(): Unit = {
    if (!historyView.getItems.isEmpty) {
      historyView.getItems.removeAll(historyView.getItems)
    }
    printHistory(user.history.food).map(x => historyView.getItems.add(x))
  }

  def addFoodToCart(): Unit = {
    cartEmpty.setVisible(false)
    noFoodRemoveSelected.setVisible(false)
    cartEmpty1.setVisible(false)
    cartEmpty.setVisible(false)

    val l: String = foodChoice.getValue
    if (l == null) noFoodSelected.setVisible(true)
    else {
      noFoodSelected.setVisible(false)
      cartList.getItems.add(l)
    }
  }

  def removeFoodFromCart(): Unit = {
    noFoodSelected.setVisible(false)
    cartEmpty.setVisible(false)
    cartEmpty1.setVisible(false)
    cartEmpty.setVisible(false)

    val f = cartList.getSelectionModel.getSelectedItem
    val l = cartList.getItems.toArray
    if (f == null) noFoodRemoveSelected.setVisible(true)
    else {
      noFoodRemoveSelected.setVisible(false)
      cartList.getItems.remove(l.indexOf(f))
    }
  }

  def confirmCartFunc(): Unit = {
    noFoodRemoveSelected.setVisible(false)
    noFoodSelected.setVisible(false)
    cartEmpty1.setVisible(false)
    cartEmpty.setVisible(false)

    val l: List[String] = cartList.getItems.toArray.toList.asInstanceOf[List[String]]
    if (l.isEmpty) cartEmpty.setVisible(true)
    else {
      cartEmpty.setVisible(false)
      user = user.updateFoodHistory(getFoodByName(l, listFoods))
      cartList.getItems.removeAll(cartList.getItems)
      backToFoodMenuFunc()
    }
  }

  def cartCaloriesFunc(): Unit = {
    noFoodRemoveSelected.setVisible(false)
    noFoodSelected.setVisible(false)
    cartEmpty.setVisible(false)
    cartEmpty1.setVisible(false)

    val l: List[String] = cartList.getItems.toArray.toList.asInstanceOf[List[String]]
    cartCalories.setText(checkCartCalories(getFoodByName(l, listFoods)))
    cartCalories.setVisible(true)
  }

  def dairyFilter(): Unit = {
    category.setText(category.getText.replace(category.getText, "Dairy"))
    val foodsName = foodList.filter(x => x.foodCategory == dairy).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    foodChoice.setItems(data)
  }

  def fatFilter(): Unit = {
    category.setText(category.getText.replace(category.getText, "Fat and Oils"))
    val foodsName = foodList.filter(x => x.foodCategory == fatAndOils).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    foodChoice.setItems(data)
  }

  def proteinFilter(): Unit = {
    category.setText(category.getText.replace(category.getText, "Protein"))
    val foodsName = foodList.filter(x => x.foodCategory == protein).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    foodChoice.setItems(data)
  }

  def fruitsFilter(): Unit = {
    category.setText(category.getText.replace(category.getText, "Fruits and Vegetables"))
    val foodsName = foodList.filter(x => x.foodCategory == fruitsAndVegetables).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    foodChoice.setItems(data)
  }

  def cerealsFilter(): Unit = {
    category.setText(category.getText.replace(category.getText, "Cereals and Derivatives"))
    val foodsName = foodList.filter(x => x.foodCategory == cerealsAndDerivatives).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    foodChoice.setItems(data)
  }

  def saltFilter(): Unit = {
    category.setText(category.getText.replace(category.getText, "Salt Products"))
    val foodsName = foodList.filter(x => x.foodCategory == saltProducts).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    foodChoice.setItems(data)
  }

  def sugarFilter(): Unit = {
    category.setText(category.getText.replace(category.getText, "Sugar Products"))
    val foodsName = foodList.filter(x => x.foodCategory == sugarProducts).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    foodChoice.setItems(data)
  }

  def legFilter(): Unit = {
    category.setText(category.getText.replace(category.getText, "Leguminous"))
    val foodsName = foodList.filter(x => x.foodCategory == leguminous).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    foodChoice.setItems(data)
  }

  def drinkFilter(): Unit = {
    category.setText(category.getText.replace(category.getText, "Drinks"))
    val foodsName = foodList.filter(x => x.foodCategory == drinks).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    foodChoice.setItems(data)
  }

  def noFilterFood(): Unit = {
    category.setText(category.getText.replace(category.getText, "No Filter"))
    val foodsName = foodList.map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    foodChoice.setItems(data)
  }

  def noFilterRecipe(): Unit = {
    categoriesRecipe.setText(categoriesRecipe.getText.replace(categoriesRecipe.getText, "No Filter"))
    val foodsName = recipeList.map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    recipeChoice.setItems(data)
  }

  def breakFilter(): Unit = {
    categoriesRecipe.setText(categoriesRecipe.getText.replace(categoriesRecipe.getText, "Breakfast"))
    val foodsName = recipeList.filter(x => x.foodCategory == breakfast).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    recipeChoice.setItems(data)
  }

  def highProtFilter(): Unit = {
    categoriesRecipe.setText(categoriesRecipe.getText.replace(categoriesRecipe.getText, "High Protein"))
    val foodsName = recipeList.filter(x => x.foodCategory == highProtein).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    recipeChoice.setItems(data)
  }

  def vegeFilter(): Unit = {
    categoriesRecipe.setText(categoriesRecipe.getText.replace(categoriesRecipe.getText, "Vegetarian"))
    val foodsName = recipeList.filter(x => x.foodCategory == vegetarian).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    recipeChoice.setItems(data)
  }

  def gluFilter(): Unit = {
    categoriesRecipe.setText(categoriesRecipe.getText.replace(categoriesRecipe.getText, "Gluten Free"))
    val foodsName = recipeList.filter(x => x.foodCategory == glutenFree).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    recipeChoice.setItems(data)
  }

  def lowCarbFilter(): Unit = {
    categoriesRecipe.setText(categoriesRecipe.getText.replace(categoriesRecipe.getText, "Low Carb"))
    val foodsName = recipeList.filter(x => x.foodCategory == lowCarb).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(foodsName.asJavaCollection)
    recipeChoice.setItems(data)
  }

  def goToHomePage(user: User): Unit = {
    val l = new FXMLLoader(getClass.getResource("/interfaces/HomePage.fxml"))
    val root: Parent = l.load()
    l.getController[HomePage].init(user)
    foodMenuPane.getScene.setRoot(root)
  }
}
