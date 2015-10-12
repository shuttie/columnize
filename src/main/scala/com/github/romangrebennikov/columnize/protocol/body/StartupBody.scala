package com.github.romangrebennikov.columnize.protocol.body

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.BinaryDecoder

/**
 * Created by shutty on 10/5/15.
 */
case class StartupBody(options:Map[String,String]) extends Body

object StartupBody extends BinaryDecoder {
  def apply(raw:ByteBuffer) = new StartupBody(map(raw))
}