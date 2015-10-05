package com.github.romangrebennikov.columnize.proxy

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorLogging, Props}
import akka.io.{IO, Tcp}

/**
 * Created by shutty on 10/5/15.
 */
class Listener extends Actor with ActorLogging{
  import Tcp._
  import context.system
  IO(Tcp) ! Bind(self, new InetSocketAddress("localhost", 19042))

  def receive = {
    case Bound(_) =>
      log.debug("bound port")
    case CommandFailed(_) =>
      log.error("cannot bind port")
      context stop self
    case Connected(remote, local) =>
      log.info(s"got connection from ${remote.getHostName}:${remote.getPort}")
      val server = context.actorOf(Props(classOf[ConnectionProxy], sender()))
  }
}
