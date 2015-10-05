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
      val socket = sender()
      log.debug(s"proxy-connected to server $remote: $socket")
      socket ! Register(self)
      context.become {
        case Received(data) =>
          log.debug("proxied response back to client")
          proxy ! Response(data.asByteBuffer)
        case Request(data) =>
          log.debug(s"sending request to server via $socket")
          socket ! Write(ByteString(data))
        case e:Any =>
          log.warning(s"unknown message: $e")
      }
    case e:Any =>
      log.warning(s"unknown message: $e")
  }
}
