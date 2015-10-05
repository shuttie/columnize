package com.github.romangrebennikov.columnize.proxy

import java.nio.ByteBuffer

import akka.actor.{Actor, ActorLogging, ActorRef}
import akka.io.Tcp.{PeerClosed, Received, Register, Write}
import akka.util.ByteString

/**
 * Created by shutty on 10/5/15.
 */
case class Request(data:ByteBuffer)
case class Response(data:ByteBuffer)

class ServerConnection(proxy:ActorRef, socket:ActorRef) extends Actor with ActorLogging {
  socket ! Register(self)

  def receive = {
    case Received(data) =>
      //log.debug(s"received $data")
      proxy ! Request(data.asByteBuffer)
    case Response(data) =>
      //log.debug(s"proxying response $data back to client")
      socket ! Write(ByteString(data))
    case PeerClosed =>
      log.info("connection closed")
      context stop self
  }
}
