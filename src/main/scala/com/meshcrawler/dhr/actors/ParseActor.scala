package com.meshcrawler.dhr.actors

import akka.actor.Actor

object ParseActor {
  case class ParseContent(key:String, content: String)
}

class ParseActor extends Actor {
  import ParseActor._
  import ReceiveActor._

  override def receive: Receive = {
    case ParseContent(key, content) => {
      sender() ! ParsedContent(key, extractLinks(content, List()))
    }
  }

  def extractLinks(page: String, links: List[String]): List[String] = {
    val start = page.indexOf("https://")
    if (start != -1) {
      val end = page.substring(start+8).indexOf("/")
      val link = page.substring(start, start + 8 + end)
      extractLinks(page.substring(start + 8 + end), link :: links)
    } else links
  }

}
