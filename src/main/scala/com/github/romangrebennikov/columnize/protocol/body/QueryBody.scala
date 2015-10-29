package com.github.romangrebennikov.columnize.protocol.body

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.{BinaryEncoder, BinaryDecoder, Consistency}

/**
 * Created by shutty on 10/5/15.
 */
case class QueryBody(query:String, consistency:Consistency, flags:Byte = 0) extends Body with BinaryEncoder {
  override def toBinary = {
    val rawQuery = longstring(query)
    rawQuery.rewind()
    import Consistency._
    val raw = ByteBuffer.allocate(3 + rawQuery.remaining())
    raw.put(rawQuery)
    raw.putShort(consistency.toBinary)
    raw.put(flags)
    raw
  }
}

object QueryBody extends BinaryDecoder {
  def apply(raw:ByteBuffer) = new QueryBody(longstring(raw), Consistency(short(raw)), raw.get())
}