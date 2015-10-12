package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

/**
 * Created by shutty on 10/12/15.
 */
case object DoubleType extends CQL.Type {
  def deserialize(raw:ByteBuffer) = DoubleValue(raw)
}

case class DoubleValue(data:Double) extends CQL.Value
object DoubleValue {
  def apply(raw:ByteBuffer) = new DoubleValue(raw.getDouble)
}