package com.github.romangrebennikov.columnize

import akka.actor.{Props, ActorSystem}
import com.github.romangrebennikov.columnize.proxy.Listener

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
 * Created by shutty on 10/1/15.
 */
object ServerRunner {
  def main(args: Array[String]) {
    val system = ActorSystem.create("columnize")
    val server = system.actorOf(Props(classOf[Listener]), name = "listener")
    Await.result(system.whenTerminated, Duration.Inf)
  }
}
