package com.meshcrawler.dhr

import java.time.OffsetDateTime

import akka.actor.{ActorSystem, Props}
import com.meshcrawler.dhr.actors.{ActorFactory, ReceiveActor, ScaleActor, SeedActor}

object Main extends App {
  import SeedActor._

  implicit val as = ActorSystem("crawler")

  val start = OffsetDateTime.now().toInstant.toEpochMilli
  println(start)

  val seed = "amigo"
  val url = "https://www.bing.com/search?q=amigo"

  val mainActor = as.actorOf(Props[SeedActor], "mainActor")
  val actorFactory = as.actorOf(Props[ActorFactory], "actorFactory")
  val receiver = as.actorOf(Props[ReceiveActor], "receiver")
  val scale = as.actorOf(Props[ScaleActor], "scaling")

  mainActor ! Initialize(actorFactory, receiver, scale, url)

}
