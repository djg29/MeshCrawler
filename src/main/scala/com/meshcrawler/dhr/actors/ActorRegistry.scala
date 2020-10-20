package com.meshcrawler.dhr.actors

import akka.actor.Actor

class ActorRegistry extends Actor {
  override def receive: Receive = {
    // to be implemented every actor will register itself here
    // other actors can ask for a particular actor by path
    case _ =>
  }
}
