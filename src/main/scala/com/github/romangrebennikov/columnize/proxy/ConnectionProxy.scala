package com.github.romangrebennikov.columnize.proxy

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import com.github.romangrebennikov.columnize.protocol.{BinaryDecoder, Frame}
import org.apache.commons.codec.binary.Hex

/**
 * Created by shutty on 10/5/15.
 */
class ConnectionProxy(val inputSocket:ActorRef) extends Actor /*with ActorLogging*/ with BinaryDecoder {
  val server = context.actorOf(Props(classOf[ServerConnection], self, inputSocket))
  val client = context.actorOf(Props(classOf[ClientConnection], self))
  def receive = {
    case req @ Request(data) =>
      log.debug(Hex.encodeHexString(rawbytes(data.slice())))
      log.debug(Frame(data).toString)
      client ! req
    case res @ Response(data) =>
      log.debug(Hex.encodeHexString(rawbytes(data.slice())))
      log.debug(Frame(data).toString)
      server ! res
  }
}
