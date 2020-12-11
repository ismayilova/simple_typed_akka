package com.ismayilova.akka.io

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.ismayilova.akka.io.Device.RespondTemperature

object RespondTemperatureActor {
  def apply():Behavior[RespondTemperature] = Behaviors.receiveMessage{
    case RespondTemperature(id , value) =>
      println(s"Main Actor: [$id $value]")
      Behaviors.same
    case _ =>println("Default")
    Behaviors.same
  }
}

