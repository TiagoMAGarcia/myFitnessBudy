package foods

import user.{ActivityLevelTypes, ExtraActive, LightlyActive, ModeratelyActive, Sedentary, VeryActive}
import user.{GainWeight, LoseWeight, MaintainWeight, Objectives}

object CaloriesCalculator {


  def run(height: Int, weight: Int, age : Int, activityLevelString : String, g: String, obj : String): (String, ActivityLevelTypes, Objectives, Int) = {
    val activity = activityInfo(activityLevelString)
    val gender = genderInfo(height, weight, age, activity._1, g)
    val objective = objectiveInfo(gender._2, obj)
    (gender._1, activity._2, objective._1, objective._2)
  }

  def activityInfo(activityLevelString : String): (Double, ActivityLevelTypes) = activityLevelString match {
    case "S" =>
      val activityLevel = Sedentary.value
      (activityLevel, Sedentary)
    case "L" =>
      val activityLevel = LightlyActive.value
      (activityLevel, LightlyActive)
    case "M" =>
      val activityLevel = ModeratelyActive.value
      (activityLevel, ModeratelyActive)
    case "V" =>
      val activityLevel = VeryActive.value
      (activityLevel, VeryActive)
    case "E" =>
      val activityLevel = ExtraActive.value
      (activityLevel, ExtraActive)
  }

  private def genderInfo(height: Int, weight: Int, age: Int, activityLevel : Double, gender : String): (String, Double) = {

    gender match {
      case "M" =>
        val masculineBMR = 10 * height + 6.25 * weight - 5 * age + 5
        val TDEE = masculineBMR * activityLevel
        (gender, TDEE)
      case "F" =>
        val feminineBMR = 10 * height + 6.25 * weight - 5 * age - 161
        val TDEE = feminineBMR * activityLevel
        (gender, TDEE)
    }
  }

  def objectiveInfo(TDEE : Double, objective : String): (Objectives, Int) = {

    objective match {
      case "L" =>
        val dailyObjective = (TDEE * 1.1).toInt
        (LoseWeight,dailyObjective)
      case "M" =>
        val dailyObjective = TDEE.toInt
        (MaintainWeight, dailyObjective)
      case "G" =>
        val dailyObjective = (TDEE * 1.1).toInt
        (GainWeight,dailyObjective)
    }
  }
}
