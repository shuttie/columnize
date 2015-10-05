package com.github.romangrebennikov.columnize.protocol.body

import java.nio.ByteBuffer

/**
 * Created by shutty on 10/5/15.
 */
case class StartupBody(options:Map[String,String]) extends Body

object StartupBody {
  def apply(raw:ByteBuffer) = new StartupBody(Body.map(raw))
}