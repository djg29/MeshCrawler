package com.meshcrawler.dhr.actors

import akka.actor.SupervisorStrategy.Restart
import akka.actor.{Actor, AllForOneStrategy, OneForOneStrategy, Props}

object ScaleActor {
  case class Analyse(data: Seq[String])
}

class ScaleActor extends Actor {

  override val supervisorStrategy = OneForOneStrategy() {
    case _: Exception => Restart
  }

  import ScaleActor._
  import GetContentActor._

  override def receive: Receive = {
    case Analyse(data) => {
      data.map(url => {
        context.actorOf(Props[GetContentActor]) ! GetContent(url, sender())
      })
    }
  }

}
