package controllers

import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.layout.BorderPane
import javafx.stage.Stage

class StartingPage{
  @FXML
  private var startingPagePane: BorderPane = _

  def onRegisterButtonClicked(): Unit = {
    val l = new FXMLLoader(getClass.getResource("/interfaces/RegisterPage.fxml"))
    val root: Parent = l.load()
    val s = startingPagePane.getScene.getWindow.asInstanceOf[Stage]
    s.getScene.setRoot(root)
  }
  def onLoginHyperLinkClicked(): Unit = {
    val l = new FXMLLoader(getClass.getResource("/interfaces/LoginPage.fxml"))
    val root: Parent = l.load()
    val s = startingPagePane.getScene.getWindow.asInstanceOf[Stage]
    s.getScene.setRoot(root)
  }
}