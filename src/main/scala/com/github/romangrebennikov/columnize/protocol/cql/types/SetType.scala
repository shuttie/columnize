package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

import com.datastax.driver.core.exceptions.InvalidTypeException
import com.github.romangrebennikov.columnize.protocol.BinaryDecoder

/**
 * Created by shutty on 10/12/15.
 */
case class SetType(inner:CQL.Type) extends CQL.Type {
  def deserialize(raw:ByteBuffer) = SetValue(raw, inner)
}
case class SetValue(data:Set[CQL.Value]) extends CQL.Value
object SetValue extends BinaryDecoder {
  def apply(raw:ByteBuffer, inner:CQL.Type) = {
    val count = int(raw)
    val elements = for (i <- 0 until count) yield {
      val cellBytes = cell(raw)
      if (cellBytes.remaining() > 0) inner.deserialize(cellBytes) else NullValue
    }
    new SetValue(elements.toSet)
  }
}
