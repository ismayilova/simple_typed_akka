import akka.actor.ActorRef
import akka.actor.typed.{ActorSystem, Behavior}
import com.ismayilova.akka.io.{Device, RespondTemperatureActor, TemperatureRecordedActor}
import com.ismayilova.akka.io.Device.{ReadTemperature, RecordTemperature, RespondTemperature, Test}


object Try extends App {
 println(45)


val respondTemperatureActor =ActorSystem(RespondTemperatureActor() , "replyTo")
val temperatureRecorderActor = ActorSystem(TemperatureRecordedActor(), "repltToRecorder")

 val testActor = ActorSystem(Device("groupd_id_1" , "Device_id 1")  , "DeviceActor")

//testActor ! Test
testActor ! ReadTemperature(12 ,  respondTemperatureActor)
 testActor ! RecordTemperature(12 , 45 , temperatureRecorderActor)
 testActor ! ReadTemperature(12 , respondTemperatureActor)


}
