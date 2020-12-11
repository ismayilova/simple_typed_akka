package com.ismayilova.akka.io

import akka.actor.typed.{ActorRef, Behavior, PostStop, Signal}
import akka.actor.typed.scaladsl.AbstractBehavior
import akka.actor.typed.scaladsl.ActorContext
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.scaladsl.LoggerOps

object Device {
 sealed trait Command
  final case class  ReadTemperature(requestId: Long ,replyTo:ActorRef[RespondTemperature]) extends Command
  final  case class RespondTemperature(requestId: Long , value:Option[Double])
  object Test extends Command

   //Write Protocol
   final case class RecordTemperature(requestId: Long, value: Double, replyTo: ActorRef[TemperatureRecorded])
     extends Command
  final case class TemperatureRecorded(requestId: Long)

  def apply(groupId:String , deviceId:String):Behavior[Command] = Behaviors.setup[Command](context =>new Device(context , groupId  , deviceId ))
  // requsetId will be obtained from requester
}


class Device(context: ActorContext[Device.Command] ,groupId: String, deviceId: String) extends AbstractBehavior[Device.Command](context) {
  import Device._

  var lastTemperature:Option[Double] = None
  override def onMessage(msg: Command): Behavior[Device.Command] = {
    msg match {
      case Test =>println(s"[Received] $msg")
        Behaviors.same
      case ReadTemperature(id , replyTo)=>
        replyTo ! RespondTemperature(id , lastTemperature)
        println(s"[$replyTo] ! RespondTemperature($id , $lastTemperature)")
        Behaviors.same
      case RecordTemperature(id , value , replyTo) =>
        println(s"[$replyTo] ! TemperatureRecorded($id ) : $value ")
        context.log.info(s"Persist this info   $value")
          lastTemperature =Some(value)
        replyTo ! TemperatureRecorded(id)
        Behaviors.same
    }

  }

  override def onSignal: PartialFunction[Signal, Behavior[Command]] = {
    case PostStop =>context.log.info(s"Device actor $groupId $deviceId is stopped  ")
      this

  }
}


