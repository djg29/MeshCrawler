package com.meshcrawler.dhr.actors

import akka.actor.SupervisorStrategy.{Restart, Resume}
import akka.actor.{Actor, ActorLogging, ActorRef, OneForOneStrategy, Props}

object ActorFactory {
  case class Create(props: Props, name: String)
  case class Created(actorRef: ActorRef)
}

class ActorFactory extends Actor with ActorLogging {
  import ActorFactory._
  override val supervisorStrategy = OneForOneStrategy() {
    case _: Exception => Resume
  }

  override def receive: Receive = {
    case Create(props, name) => {
      val actor = context.actorOf(props, name)
      sender() ! Created(actor)
    }
  }
}
