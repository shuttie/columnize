package com.github.romangrebennikov.columnize.proxy

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

/**
 * Created by shutty on 10/5/15.
 */
class ConnectionProxy(val inputSocket:ActorRef) extends Actor with ActorLogging {
  val server = context.actorOf(Props(classOf[ServerConnection], self, inputSocket))
  val client = context.actorOf(Props(classOf[ClientConnection], self))
  def receive = {
    case req @ Request(data) => client ! req
    case res @ Response(data) => server ! res
  }
}
