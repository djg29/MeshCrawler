package com.meshcrawler.dhr.actors

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

object SeedActor {
  case class Initialize(actorFactory: ActorRef, receiver: ActorRef, scale: ActorRef, url: String)
}

class SeedActor extends Actor with ActorLogging {
  import SeedActor._
  import GetContentActor._
  import ActorFactory._

  override def receive: Receive = {
    case Initialize(actorFactory, receiver, scale, url) => {
      actorFactory ! Create(Props[GetContentActor], "starter")
      context.become(initiate(url, receiver))
    }
    case _ => log.info("ignore this")
  }

  def initiate(url: String, receiver: ActorRef): Receive = {
    case Created(starter) => {
      starter ! GetContent(url, receiver)
    }
  }

}
