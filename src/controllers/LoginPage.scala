package controllers

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control.{Button, Label, PasswordField, TextField}
import javafx.scene.layout.{BorderPane, Pane}
import user.User
import user.User_Utils._

class LoginPage {
  @FXML
  private var loginPagePane: BorderPane = _
  @FXML
  private var loginButton: Button = _
  @FXML
  private var name: TextField = _
  @FXML
  private var password: PasswordField = _
  @FXML
  private var passInvalid: Label = _
  @FXML
  private var userInvalid: Label = _

  def onLoginButtonClicked(): Unit = {
    passInvalid.setVisible(false)
    userInvalid.setVisible(false)

    val username : String = name.getText
    val pass : String = password.getText
    val usersList = readUsers(fileUsers)
    val checkUserName = usersList match {
      case None => true
      case Some(value) => checkIfExists(value, username)
    }
    if (!checkUserName) {
      val user: List[User] = usersList match {
        case None => List()
        case Some(value) => value.filter(u => u.name.equals(username))
      }
      if (user.head.password.equals(pass)) {
        nextPage(user.head)
      } else {
        invalidPassword()
      }
    } else {
      invalidUsername()
    }
  }

  def invalidPassword(): Unit = passInvalid.setVisible(true)
  def invalidUsername(): Unit = userInvalid.setVisible(true)

  def onBackButtonClicked(): Unit = {
    val startingPagePane: Pane = FXMLLoader.load(getClass.getResource("/interfaces/StartingPage.fxml"))
    loginPagePane.getChildren.setAll(startingPagePane)
  }

  def nextPage(user: User): Unit = {
    val l = new FXMLLoader(getClass.getResource("/interfaces/HomePage.fxml"))
    val root : Parent = l.load()
    l.getController[HomePage].init(user)
    loginPagePane.getScene.setRoot(root)
  }
}