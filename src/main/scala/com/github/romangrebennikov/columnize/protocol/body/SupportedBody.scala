package com.github.romangrebennikov.columnize.protocol.body

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.BinaryDecoder

/**
 * Created by shutty on 10/5/15.
 */
case class SupportedBody(options:Map[String,List[String]]) extends Body

object SupportedBody extends BinaryDecoder {
  def apply(raw:ByteBuffer) = new SupportedBody(multimap(raw))
}