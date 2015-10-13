package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.BinaryDecoder

/**
 * Created by shutty on 10/12/15.
 */
case class ListType(inner:CQL.Type) extends CQL.Type {
  def deserialize(raw:ByteBuffer) = ListValue(raw, inner)
}

case class ListValue(data:Seq[CQL.Value], inner:CQL.Type) extends CQL.Value
object ListValue extends BinaryDecoder {
  def apply(raw:ByteBuffer, inner:CQL.Type) = {
    val count = int(raw)
    val elements = for (i <- 0 until count) yield {
      inner.deserialize(cell(raw))
    }
    new ListValue(elements, inner)
  }
}
