package controllers

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control.{Button, Label}
import javafx.scene.layout.{BorderPane, GridPane}
import javafx.stage.Stage
import user.User_Utils.{getTodayCalories, logout, showMyProfile}
import user.User

class HomePage {

  @FXML
  private var homePagePane: BorderPane = _
  @FXML
  private var welcome: Label = _
  @FXML
  private var settingsBackButton: Button = _
  @FXML
  private var foodsMenu: Button = _
  @FXML
  private var exerciseMenu: Button = _
  @FXML
  private var dailyObjectiveButton: Button = _
  @FXML
  private var todayCaloriesButton: Button = _
  @FXML
  private var myProfileButton: Button = _
  @FXML
  private var updateProfileButton: Button = _
  @FXML
  private var settingsButton: Button = _
  @FXML
  private var userInformation: Label = _
  @FXML
  private var phrase1: Label = _
  @FXML
  private var phrase2: Label = _
  @FXML
  private var phrase3: Label = _
  @FXML
  private var settingsLabel: Label = _
  @FXML
  private var user: User = _
  @FXML
  private var settingsGridPane: GridPane = _
  @FXML
  private var welcomeGridPane: GridPane = _
  @FXML
  private var caloriesRemainingText: Label = _
  @FXML
  private var dailyObjectiveText: Label = _

  def init(user: User): Unit = {
    this.user = user
    welcome.setText(welcome.getText + " " + user.name)
  }

  def foodsMenuFunc(): Unit = goToFoodMenu(user)

  def exerciseMenuFunc(): Unit = goToExerciseMenu(user)

  def dailyObjectiveFunc(): Unit = {
    dailyObjectiveText.setText(dailyObjectiveText.getText.replace("-", user.dailyObjective.toString))
    caloriesRemainingText.setVisible(false)
    dailyObjectiveText.setVisible(true)
  }

  def todayCaloriesFunc(): Unit = {
    caloriesRemainingText.setText("")
    caloriesRemainingText.setText(caloriesRemainingText.getText.replace("", getTodayCalories(user)))
    dailyObjectiveText.setVisible(false)
    caloriesRemainingText.setVisible(true)
  }

  def myProfileFunc(): Unit = userInformation.setText(showMyProfile(user))

  def updateProfileFunc(): Unit = goToInformation(user)

  def logoutFunc(): Unit = {
    logout(user)
    val startingPagePane: BorderPane = FXMLLoader.load(getClass.getResource("/interfaces/StartingPage.fxml"))
    homePagePane.getChildren.setAll(startingPagePane)
  }

  def goToFoodMenu(user: User): Unit = {
    val l = new FXMLLoader(getClass.getResource("/interfaces/FoodMenu.fxml"))
    val root: Parent = l.load()
    l.getController[FoodMenu].init(user)
    homePagePane.getScene.setRoot(root)
  }

  def goToExerciseMenu(user: User): Unit = {
    val l = new FXMLLoader(getClass.getResource("/interfaces/ExerciseMenu.fxml"))
    val root: Parent = l.load()
    l.getController[ExerciseMenu].init(user)
    homePagePane.getScene.setRoot(root)
  }

  def goToInformation(user: User): Unit = {
    val l = new FXMLLoader(getClass.getResource("/interfaces/InformationPage.fxml"))
    val root: Parent = l.load()
    val s = homePagePane.getScene.getWindow.asInstanceOf[Stage]
    l.getController[InformationPage].initFromSettings(user)
    s.getScene.setRoot(root)
  }

  def returnHomePage(): Unit = {
    settingsButton.setVisible(true)
    settingsButton.setDisable(false)
    settingsBackButton.setVisible(false)
    settingsBackButton.setDisable(true)
    welcomeGridPane.setVisible(true)
    settingsGridPane.setVisible(false)
    userInformation.setText("")
  }

  def settingsFunc(): Unit = {
    settingsButton.setVisible(false)
    settingsButton.setDisable(true)
    settingsBackButton.setVisible(true)
    settingsBackButton.setDisable(false)
    welcomeGridPane.setVisible(false)
    settingsGridPane.setVisible(true)
  }
}
