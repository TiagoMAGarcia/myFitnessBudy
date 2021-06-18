package user

sealed trait ActivityLevelTypes

case object Sedentary extends ActivityLevelTypes{val value = 1.2}
case object LightlyActive extends ActivityLevelTypes{val value = 1.375}
case object ModeratelyActive extends ActivityLevelTypes{val value = 1.55}
case object VeryActive extends ActivityLevelTypes{val value = 1.725}
case object ExtraActive extends ActivityLevelTypes{val value = 1.9}

sealed trait Objectives

case object LoseWeight extends Objectives
case object MaintainWeight extends Objectives
case object GainWeight extends Objectives
