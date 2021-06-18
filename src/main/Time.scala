package main

import java.util.Calendar
import java.text.SimpleDateFormat

object Time {

  def getDate(): String = {
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
    dateFormat.format(Calendar.getInstance().getTime())
  }
}
