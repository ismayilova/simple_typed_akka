package com.ismayilova.akka.io

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.ismayilova.akka.io.Device.TemperatureRecorded

object TemperatureRecordedActor {

  def apply():Behavior[TemperatureRecorded] = Behaviors.receiveMessage{

    case TemperatureRecorded(requestId) =>println(s"TemperatureRecorder recorded the $requestId !")
    Behaviors.same
  }

}
