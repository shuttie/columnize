package com.github.romangrebennikov.columnize.proxy

import java.net.InetSocketAddress

import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.io.{IO, Tcp}
import akka.util.ByteString

/**
 * Created by shutty on 10/5/15.
 */
class ClientConnection(proxy:ActorRef) extends Actor with ActorLogging {
  import Tcp._
  import context.system

  IO(Tcp) ! Connect(new InetSocketAddress("localhost", 9042))

  def receive = {
    case Connected(remote, local) =>
      log.debug(s"proxy-connected to server $remote")
      val socket = sender()
      socket ! Register(self)
      context.become {
        case Received(data) =>
          log.debug("proxied response back to client")
          proxy ! Response(data.asByteBuffer)
        case Request(data) =>
          log.debug("sending request to server")
          socket ! Write(ByteString(data))
      }
  }
}
