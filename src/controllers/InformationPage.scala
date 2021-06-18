package controllers

import foods.CaloriesCalculator._
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control.{RadioButton, _}
import javafx.scene.layout.BorderPane
import user.User
import user.User_Utils.writeToUser

class InformationPage {

  @FXML
  private var informationPage: BorderPane = _
  @FXML
  private var user: User = _
  @FXML
  private var finishedButton: Button = _
  @FXML
  private var height: TextField = _
  @FXML
  private var weight: TextField = _
  @FXML
  private var age: TextField = _
  @FXML
  private var activityLevel: ComboBox[String] = _
  @FXML
  private var male: RadioButton = _
  @FXML
  private var female: RadioButton = _
  @FXML
  private var lose: RadioButton = _
  @FXML
  private var maintain: RadioButton = _
  @FXML
  private var gain: RadioButton = _
  @FXML
  private var scroll: ScrollPane = _
  @FXML
  private var backToSettingsButton: Button = _
  @FXML
  private var backToRegisterButton: Button = _
  @FXML
  private var heightFieldEmpty: Label = _
  @FXML
  private var weightFieldEmpty: Label = _
  @FXML
  private var ageFieldEmpty: Label = _
  @FXML
  private var activityFieldEmpty: Label = _
  @FXML
  private var genderEmpty: Label = _
  @FXML
  private var objectiveEmpty: Label = _

  private var genderGroup = new ToggleGroup
  private var objectiveGroup = new ToggleGroup

  @FXML
  def initialize(): Unit = {
    male.setToggleGroup(genderGroup)
    male.setSelected(true)
    female.setToggleGroup(genderGroup)
    lose.setToggleGroup(objectiveGroup)
    maintain.setToggleGroup(objectiveGroup)
    maintain.setSelected(true)
    gain.setToggleGroup(objectiveGroup)
  }

  private def isInt(string: String): Boolean = "^[0-9]*$".r.matches(string)

  def finished(): Unit = {
    try {
      heightFieldEmpty.setVisible(false)
      weightFieldEmpty.setVisible(false)
      ageFieldEmpty.setVisible(false)

      if (height.getText.isEmpty) heightFieldEmpty.setVisible(true)
      else {
        heightFieldEmpty.setVisible(false)
        val h = height.getText.toInt
        if (weight.getText.isEmpty) weightFieldEmpty.setVisible(true)
        else {
          weightFieldEmpty.setVisible(false)
          val w = weight.getText.toInt
          if (age.getText.isEmpty) ageFieldEmpty.setVisible(true)
          else {
            ageFieldEmpty.setVisible(false)
            val a = age.getText.toInt
            var gender = ""
            if (male.isSelected) gender = "M"
            else {
              gender = "F"
            }
            var act = activityLevel.getValue
            if (act != null) {
              activityFieldEmpty.setVisible(false)
              act match {
                case "Sedentary" => act = "S"
                case "Lightly Active" => act = "L"
                case "Moderately Active" => act = "M"
                case "Very Active" => act = "V"
                case "Extra Active" => act = "E"
              }
              var obj = ""
              if (lose.isSelected) obj = "L"
              else {
                if (maintain.isSelected) obj = "M"
                else {
                  if (gain.isSelected) obj = "G"
                }
              }
              val info = run(h, w, a, act, gender, obj)
              writeToUser(user.updateUser(h, w, a, info._1, info._2, info._3, info._4))
              nextPage(user.updateUser(h, w, a, info._1, info._2, info._3, info._4))
            } else {
              activityFieldEmpty.setVisible(true)
            }
          }
        }
      }
    } catch {
      case _: Exception =>
        if (!height.getText.isEmpty && !isInt(height.getText)) heightFieldEmpty.setVisible(true)
        if (weight.getText.nonEmpty && !isInt(weight.getText)) weightFieldEmpty.setVisible(true)
        if (!age.getText.isEmpty && !isInt(age.getText)) ageFieldEmpty.setVisible(true)
    }
  }


  def initFromRegister(user: User): Unit = {
    backToSettingsButton.setVisible(false)
    backToSettingsButton.setDisable(true)
    backToRegisterButton.setVisible(true)
    backToRegisterButton.setDisable(false)

    this.user = user
    val data: ObservableList[String] = FXCollections.observableArrayList("Sedentary", "Lightly Active", "Moderately Active", "Very Active", "Extra Active")
    activityLevel.setItems(data)
  }

  def initFromSettings(user: User): Unit = {
    backToSettingsButton.setVisible(true)
    backToSettingsButton.setDisable(false)
    backToRegisterButton.setVisible(false)
    backToRegisterButton.setDisable(true)

    this.user = user
    val data: ObservableList[String] = FXCollections.observableArrayList("Sedentary", "Lightly Active", "Moderately Active", "Very Active", "Extra Active")
    activityLevel.setItems(data)
  }

  def nextPage(user: User): Unit = {
    val l = new FXMLLoader(getClass.getResource("/interfaces/HomePage.fxml"))
    val root: Parent = l.load()
    l.getController[HomePage].init(user)
    informationPage.getScene.setRoot(root)
  }

  def backToSettings(): Unit = {
    val l = new FXMLLoader(getClass.getResource("/interfaces/HomePage.fxml"))
    val root: Parent = l.load()
    l.getController[HomePage].init(user)
    informationPage.getScene.setRoot(root)
  }

  def backToRegister(): Unit = {
    val l = new FXMLLoader(getClass.getResource("/interfaces/RegisterPage.fxml"))
    val root: Parent = l.load()
    informationPage.getScene.setRoot(root)
  }

}
