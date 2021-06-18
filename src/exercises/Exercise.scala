package exercises

case class Exercise(name : String, met : Double, exerciseType: ExercisesTypes)

sealed trait ExercisesTypes

case object aerobics extends ExercisesTypes
case object strength extends ExercisesTypes
case object flexibility extends ExercisesTypes
