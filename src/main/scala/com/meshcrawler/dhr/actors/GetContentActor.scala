package com.meshcrawler.dhr.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

object GetContentActor {
  case class GetContent(url: String, receiver: ActorRef)
}

class GetContentActor extends Actor with ActorLogging {
  import GetContentActor._
  import ParseActor._
  import ReceiveActor._
  override def receive: Receive = {
    case GetContent(url, receiver) => {
      val content = io.Source.fromURL(url).mkString
      val parser = context.actorOf(Props[ParseActor])
      parser ! ParseContent("amigo", content)
      context.become(receive(receiver))
    }
    case _ => log.info("not impl")
  }

  def receive(receiver: ActorRef): Receive = {
    case ParsedContent(key, parsed) => {
      receiver ! ParsedContent(key, parsed)
    }
  }
}
