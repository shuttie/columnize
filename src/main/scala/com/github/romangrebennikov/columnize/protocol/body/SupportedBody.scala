package com.github.romangrebennikov.columnize.protocol.body

import java.nio.ByteBuffer

/**
 * Created by shutty on 10/5/15.
 */
case class SupportedBody(options:Map[String,List[String]]) extends Body

object SupportedBody {
  def apply(raw:ByteBuffer) = new SupportedBody(Body.multimap(raw))
}