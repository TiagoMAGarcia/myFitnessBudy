package controllers

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control.{Button, Label, PasswordField, TextField}
import javafx.scene.layout.BorderPane
import javafx.stage.Stage
import user.User_Utils._
import user._

class RegisterPage {
  @FXML
  private var registerPagePane: BorderPane = _
  @FXML
  private var registerButton: Button = _
  @FXML
  private var registerBackButton: Button = _
  @FXML
  private var name: TextField = _
  @FXML
  private var password: PasswordField = _
  @FXML
  private var confirmPassword: PasswordField = _
  @FXML
  private var userAlreadyExists: Label = _
  @FXML
  private var userEmpty: Label = _
  @FXML
  private var passNotEqual: Label = _
  @FXML
  private var passEmpty: Label = _

  def userAlreadyExistsPopUp(): Unit = userAlreadyExists.setVisible(true)

  def userEmptyPopUp(): Unit = userEmpty.setVisible(true)

  def passNotEqualPopUp(): Unit = passNotEqual.setVisible(true)

  def passEmptyPopUp(): Unit = passEmpty.setVisible(true)

  def onRegisterButtonClicked(): Unit = {
    userEmpty.setVisible(false)
    passEmpty.setVisible(false)
    userAlreadyExists.setVisible(false)
    passNotEqual.setVisible(false)
    val username: String = name.getText
    val pass: String = password.getText
    val confPass: String = confirmPassword.getText
    val usersList = readUsers(fileUsers)
    val checkUserName = usersList match {
      case None => true
      case Some(value) => checkIfExists(value, username)
    }
    if (username.isBlank) {
      userEmptyPopUp()
    } else {
      userEmpty.setVisible(false)
      if (pass.isBlank) {
        passEmptyPopUp()
      } else {
        passEmpty.setVisible(false)
        if (!checkUserName) {
          userAlreadyExistsPopUp()
        } else {
          userAlreadyExists.setVisible(false)
          if (!confPass.equals(pass)) {
            passNotEqualPopUp()
          } else {
            passNotEqual.setVisible(false)
            val user: User = User(username, pass, Body(0, 0, 0, ""), LightlyActive, MaintainWeight, 0, History(List(), List()), List())
            nextPage(user)
          }
        }
      }
    }
  }

  def onBackButtonClicked(): Unit = {
    val l = new FXMLLoader(getClass.getResource("/interfaces/StartingPage.fxml"))
    val root: Parent = l.load()
    val s = registerPagePane.getScene.getWindow.asInstanceOf[Stage]
    s.getScene.setRoot(root)
  }

  def nextPage(user: User): Unit = {
    val l = new FXMLLoader(getClass.getResource("/interfaces/InformationPage.fxml"))
    val root: Parent = l.load()
    val s = registerPagePane.getScene.getWindow.asInstanceOf[Stage]
    l.getController[InformationPage].initFromRegister(user)
    s.getScene.setRoot(root)
  }
}