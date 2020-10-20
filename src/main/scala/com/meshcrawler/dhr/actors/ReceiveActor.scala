package com.meshcrawler.dhr.actors

import java.time.OffsetDateTime

import akka.actor.{Actor, ActorLogging, Props}

object ReceiveActor {
  case class ParsedContent(key: String, links: Seq[String])
}

class ReceiveActor extends Actor with ActorLogging {
  import ReceiveActor._
  import ScaleActor._
  override def receive: Receive = {
    case ParsedContent(key, data) => {
      log.info(data.toString)
      log.info(OffsetDateTime.now().toInstant.toEpochMilli.toString)
      context.actorOf(Props[ScaleActor]) ! Analyse(data)
    }
  }
}
