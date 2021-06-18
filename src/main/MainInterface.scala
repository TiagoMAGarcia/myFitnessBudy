package main

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.image.Image
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage

class MainInterface extends Application {
  val Stage = new Stage()

  override def start(primaryStage: Stage): Unit = {
    val Stage = primaryStage
    primaryStage.setTitle("myFitnessBuddy")
    val fxmlLoader = new FXMLLoader(getClass.getResource("/interfaces/StartingPage.fxml"))
    val mainViewRoot: Parent = fxmlLoader.load()
    val scene = new Scene(mainViewRoot)
    Stage.setScene(scene)
    Stage.getIcons.add(new Image("/templates/logo4-removebg-preview.png"))
    Stage.setX(340)
    Stage.setY(130)
    Stage.show()
  }
}
object FxApp {
  def main(args: Array[String]): Unit = {
    Application.launch(classOf[MainInterface], args: _*)
  }
}