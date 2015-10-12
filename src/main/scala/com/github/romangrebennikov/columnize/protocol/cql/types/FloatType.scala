package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

/**
 * Created by shutty on 10/12/15.
 */
case object FloatType extends CQL.Type {
  def deserialize(raw:ByteBuffer) = FloatValue(raw)
}

case class FloatValue(data:Float) extends CQL.Value
object FloatValue {
  def apply(raw:ByteBuffer) = new FloatValue(raw.getFloat)
}
