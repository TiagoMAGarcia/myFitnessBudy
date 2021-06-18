package controllers

import exercises.DealWithExercises._
import exercises._
import javafx.collections.{FXCollections, ObservableList}
import javafx.fxml.{FXML, FXMLLoader}
import javafx.scene.Parent
import javafx.scene.control._
import javafx.scene.layout.{BorderPane, GridPane}
import main.Time.getDate
import user.User
import user.User_Utils.{listExercises, printHistory, showPlan}

import scala.jdk.CollectionConverters.IterableHasAsJava

class ExerciseMenu {

  @FXML
  private var exerciseMenuPane: BorderPane = _
  @FXML
  private var addExercisePane: GridPane = _
  @FXML
  private var exerciseHistoryPane: GridPane = _
  @FXML
  private var planPane: GridPane = _
  @FXML
  private var backButton: Button = _
  @FXML
  private var backExerciseMenu: Button = _
  @FXML
  private var plan: Button = _
  @FXML
  private var category: MenuButton = _
  @FXML
  private var exercisesHistory: Button = _
  @FXML
  private var exerciseType: MenuButton = _
  @FXML
  private var exerciseChoice: ComboBox[String] = _
  @FXML
  private var duration: TextField = _
  @FXML
  private var duration2: TextField = _
  @FXML
  private var exChoice: ComboBox[String] = _
  @FXML
  private var addExercise: Button = _
  @FXML
  private var historyCart: ListView[String] = _
  @FXML
  private var planView: ListView[String] = _
  @FXML
  private var planEmpty: Label = _
  @FXML
  private var planEmpty1: Label = _
  @FXML
  private var noExercise: Label = _
  @FXML
  private var noPlanSet: Label = _
  @FXML
  private var planSet: Label = _
  @FXML
  private var noPlanShow: Label = _
  @FXML
  private var noExerciseSelected: Label = _
  @FXML
  private var noDuration: Label = _
  @FXML
  private var noExSelected: Label = _
  @FXML
  private var noDurationSelected: Label = _
  @FXML
  private var incorrectDuration: Label = _

  private var user: User = _

  private var exPlan: List[(Exercise, Int)] = List()

  private val exerciseList = listExercises

  def init(user: User): Unit = {
    this.user = user
    val exercisesName = listExercises.map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(exercisesName.asJavaCollection)
    exerciseChoice.setItems(data)
    exChoice.setItems(data)
  }

  def backToHomePageFunc(): Unit = goToHomePage(user)

  def noFilterButton(): Unit = {
    exerciseType.setText(exerciseType.getText.replace(exerciseType.getText, "No Filter"))
    val aerobicsList = listExercises.map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(aerobicsList.asJavaCollection)
    exerciseChoice.setItems(data)
  }

  def aerobicChoiceButton(): Unit = {
    exerciseType.setText(exerciseType.getText.replace(exerciseType.getText, "Aerobics"))
    val aerobicsList = listExercises.filter(x => x.exerciseType == aerobics).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(aerobicsList.asJavaCollection)
    exerciseChoice.setItems(data)
  }

  def strengthChoiceButton(): Unit = {
    exerciseType.setText(exerciseType.getText.replace(exerciseType.getText, "Strength"))
    val strengthList = listExercises.filter(x => x.exerciseType == strength).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(strengthList.asJavaCollection)
    exerciseChoice.setItems(data)
  }

  def flexibilityChoiceButton(): Unit = {
    exerciseType.setText(exerciseType.getText.replace(exerciseType.getText, "Flexibility"))
    val flexibilityList = listExercises.filter(x => x.exerciseType == flexibility).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(flexibilityList.asJavaCollection)
    exerciseChoice.setItems(data)
  }

  def backToExerciseFunc(): Unit = {
    backButton.setVisible(true)
    backButton.setDisable(false)
    addExercise.setVisible(true)
    addExercise.setDisable(false)
    plan.setVisible(true)
    plan.setDisable(false)
    exercisesHistory.setVisible(true)
    exercisesHistory.setDisable(false)
    backExerciseMenu.setVisible(false)
    backExerciseMenu.setDisable(true)

    addExercisePane.setVisible(false)
    exerciseHistoryPane.setVisible(false)
    planPane.setVisible(false)

    historyCart.getItems.removeAll(historyCart.getItems)
    duration.setText("")
    duration2.setText("")
    clearPlanView()
    planEmpty1.setVisible(false)
    category.setText("Choose Category")
    exerciseType.setText("Choose Type")
  }

  def addExerciseFunc(): Unit = {
    backButton.setVisible(false)
    backButton.setDisable(true)
    addExercise.setVisible(false)
    addExercise.setDisable(true)
    plan.setVisible(false)
    plan.setDisable(true)
    exercisesHistory.setVisible(false)
    exercisesHistory.setDisable(true)
    backExerciseMenu.setVisible(true)
    backExerciseMenu.setDisable(false)

    addExercisePane.setVisible(true)
  }

  def exerciseHistoryFunc(): Unit = {
    backButton.setVisible(false)
    backButton.setDisable(true)
    addExercise.setVisible(false)
    addExercise.setDisable(true)
    plan.setVisible(false)
    plan.setDisable(true)
    exercisesHistory.setVisible(false)
    exercisesHistory.setDisable(true)
    backExerciseMenu.setVisible(true)
    backExerciseMenu.setDisable(false)

    exerciseHistoryPane.setVisible(true)
  }

  def planPaneFunc(): Unit = {
    backButton.setVisible(false)
    backButton.setDisable(true)
    addExercise.setVisible(false)
    addExercise.setDisable(true)
    plan.setVisible(false)
    plan.setDisable(true)
    exercisesHistory.setVisible(false)
    exercisesHistory.setDisable(true)
    backExerciseMenu.setVisible(true)
    backExerciseMenu.setDisable(false)

    planPane.setVisible(true)
  }

  private def isInt(string: String): Boolean = "^[0-9]*$".r.matches(string)

  def addExerciseHistory(): Unit = {
    noExerciseSelected.setVisible(false)
    noDuration.setVisible(false)

    if (exerciseChoice.getValue == null) {
      noExerciseSelected.setVisible(true)
    }
    else {
      if (!isInt(duration.getText) || duration.getText.isEmpty) noDuration.setVisible(true)
      else {
        val exerciseDuration = duration.getText.toInt
        val caloriesBurned = ((exerciseDuration * (listExercises.filter(x => x.name.equals(exerciseChoice.getValue)).head.met * 3.5 * user.body.weight)) / 200).toInt
        user = user.updateExerciseHistory((listExercises.filter(x => x.name.equals(exerciseChoice.getValue)).head, caloriesBurned, getDate()))
        backToExerciseFunc()
      }
    }
  }

  def todayHistoryFunc(): Unit = {
    val l = user.history.exercise.filter(x => x._3.equals(getDate()))
    if (!historyCart.getItems.isEmpty) {
      historyCart.getItems.removeAll(historyCart.getItems)
    }
    printHistory(l).map(x => historyCart.getItems.add(x))
  }

  def entireHistoryFunc(): Unit = {
    if (!historyCart.getItems.isEmpty) {
      historyCart.getItems.removeAll(historyCart.getItems)
    }
    printHistory(user.history.exercise).map(x => historyCart.getItems.add(x))
  }

  def addExPlanFunc(): Unit = {
    noExSelected.setVisible(false)
    noDurationSelected.setVisible(false)
    planEmpty1.setVisible(false)
    noPlanShow.setVisible(false)
    noPlanSet.setVisible(false)
    noExercise.setVisible(false)
    planEmpty.setVisible(false)
    planSet.setVisible(false)
    noExercise.setVisible(false)
    incorrectDuration.setVisible(false)

    if (exChoice.getValue == null) noExSelected.setVisible(true)
    else {
      if (duration2.getText.isEmpty) noDurationSelected.setVisible(true)
      else {
        if (!isInt(duration2.getText)) incorrectDuration.setVisible(true)
        else {
          if (planView.getItems.isEmpty) {
            exPlan = List()
            val exerciseDuration = duration2.getText.toInt
            val l: List[(Exercise, Int)] = List((exerciseList.filter(x => x.name.equals(exChoice.getValue)).head, exerciseDuration))
            exPlan = exPlan.appended(l.head)
            val s = showPlan(l)
            planView.getItems.add(s.head)
          } else {
            val exerciseDuration = duration2.getText.toInt
            val l: List[(Exercise, Int)] = List((exerciseList.filter(x => x.name.equals(exChoice.getValue)).head, exerciseDuration))
            exPlan = exPlan.appended(l.head)
            val s = showPlan(l)
            planView.getItems.add(s.head)
          }
        }
      }
    }
  }

  def removeExPlan(): Unit = {
    noExSelected.setVisible(false)
    noDurationSelected.setVisible(false)
    planEmpty1.setVisible(false)
    noPlanShow.setVisible(false)
    noPlanSet.setVisible(false)
    noExercise.setVisible(false)
    planEmpty.setVisible(false)
    planSet.setVisible(false)
    noExercise.setVisible(false)
    incorrectDuration.setVisible(false)

    if (planView.getItems.isEmpty || planView.getSelectionModel.getSelectedItem == null) {
      noExercise.setVisible(true)
    } else {
      val f = planView.getSelectionModel.getSelectedItem
      exPlan = exPlan.filter(x => !x._1.name.equals(f.split("-").head))
      val l = planView.getItems.toArray
      planView.getItems.remove(l.indexOf(f))
    }
  }

  def addPlanToHistoryFunc(): Unit = {
    noExSelected.setVisible(false)
    noDurationSelected.setVisible(false)
    planEmpty1.setVisible(false)
    noExercise.setVisible(false)
    noPlanShow.setVisible(false)
    noPlanSet.setVisible(false)
    planEmpty.setVisible(false)
    planSet.setVisible(false)
    noExercise.setVisible(false)
    incorrectDuration.setVisible(false)

    if (planView.getItems.isEmpty) {
      planEmpty.setVisible(true)
    } else {
      planEmpty.setVisible(false)
      setAsMyPlan()
      user = addPlanToHistory(user, user.plan)
      backToExerciseFunc()
    }
  }

  def clearPlanView(): Unit = {
    if (planView.getItems.isEmpty) planEmpty1.setVisible(true)
    else {
      noExSelected.setVisible(false)
      noDurationSelected.setVisible(false)
      planEmpty1.setVisible(false)
      noExercise.setVisible(false)
      noPlanShow.setVisible(false)
      noPlanSet.setVisible(false)
      planEmpty.setVisible(false)
      planSet.setVisible(false)
      noExercise.setVisible(false)
      incorrectDuration.setVisible(false)

      duration2.setText("")
      planView.getItems.removeAll(planView.getItems)
    }
  }

  def getPlanFunc(): Unit = {
    noExSelected.setVisible(false)
    noDurationSelected.setVisible(false)
    planEmpty1.setVisible(false)
    noExercise.setVisible(false)
    noPlanShow.setVisible(false)
    noPlanSet.setVisible(false)
    planEmpty.setVisible(false)
    planSet.setVisible(false)
    noExercise.setVisible(false)
    incorrectDuration.setVisible(false)

    if (!planView.getItems.isEmpty) {
      clearPlanView()
    }
    exPlan = List()
    val randomPlan: List[(Exercise, Int, Int)] = getRandomPlan(user, listExercises)
    exPlan = exPlan ++ randomPlan.map(x => (x._1, x._3))
    showPlan(randomPlan.map(x => (x._1, x._3))).map(x => planView.getItems.add(x))
  }

  def setAsMyPlan(): Unit = {
    noExSelected.setVisible(false)
    noDurationSelected.setVisible(false)
    planEmpty1.setVisible(false)
    noPlanShow.setVisible(false)
    noPlanSet.setVisible(false)
    planEmpty.setVisible(false)
    noExercise.setVisible(false)
    planSet.setVisible(false)
    noExercise.setVisible(false)
    incorrectDuration.setVisible(false)

    if (planView.getItems.isEmpty) noPlanSet.setVisible(true)
    else {
      val plan = exPlan.map(x => (x._1, getCalBurned(x._2, x._1), x._2))
      planSet.setVisible(true)
      user = user.updatePlan(plan)
    }
  }

  def getCalBurned(exerciseDuration: Int, exercise: Exercise): Int = {
    ((exerciseDuration * (exercise.met * 3.5 * user.body.weight)) / 200).toInt
  }

  def seePlanFunc(): Unit = {
    noExSelected.setVisible(false)
    noDurationSelected.setVisible(false)
    planSet.setVisible(false)
    planEmpty1.setVisible(false)
    noPlanShow.setVisible(false)
    noPlanSet.setVisible(false)
    noExercise.setVisible(false)
    planEmpty.setVisible(false)
    noExercise.setVisible(false)
    incorrectDuration.setVisible(false)

    if (!planView.getItems.isEmpty) {
      planView.getItems.removeAll(planView.getItems)
      if (showUserPlan(user.plan).nonEmpty) showPlan(showUserPlan(user.plan)).map(x => planView.getItems.add(x))
    }
    else {
      if (showUserPlan(user.plan).nonEmpty) showPlan(showUserPlan(user.plan)).map(x => planView.getItems.add(x))
      else {
        noPlanShow.setVisible(true)
      }
    }
  }

  def noFilter(): Unit = {
    category.setText(category.getText.replace(category.getText, "No Filter"))
    val exercisesName = exerciseList.map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(exercisesName.asJavaCollection)
    exChoice.setItems(data)
  }

  def aeroFilter(): Unit = {
    category.setText(category.getText.replace(category.getText, "Aerobics"))
    val exercisesName = exerciseList.filter(x => x.exerciseType == aerobics).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(exercisesName.asJavaCollection)
    exChoice.setItems(data)
  }

  def strengthFilter(): Unit = {
    category.setText(category.getText.replace(category.getText, "Strength"))
    val exercisesName = exerciseList.filter(x => x.exerciseType == strength).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(exercisesName.asJavaCollection)
    exChoice.setItems(data)
  }

  def flexFilter(): Unit = {
    category.setText(category.getText.replace(category.getText, "Flexibility"))
    val exercisesName = exerciseList.filter(x => x.exerciseType == flexibility).map(x => x.name)
    val data: ObservableList[String] = FXCollections.observableArrayList(exercisesName.asJavaCollection)
    exChoice.setItems(data)
  }


  def goToHomePage(user: User): Unit = {
    val l = new FXMLLoader(getClass.getResource("/interfaces/HomePage.fxml"))
    val root: Parent = l.load()
    l.getController[HomePage].init(user)
    exerciseMenuPane.getScene.setRoot(root)
  }
}