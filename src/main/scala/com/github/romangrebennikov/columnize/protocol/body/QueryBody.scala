package com.github.romangrebennikov.columnize.protocol.body

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.{BinaryDecoder, Consistency}

/**
 * Created by shutty on 10/5/15.
 */
case class QueryBody(query:String, consistency:Consistency) extends Body

object QueryBody extends BinaryDecoder {
  def apply(raw:ByteBuffer) = new QueryBody(longstring(raw), Consistency.ANY)
}