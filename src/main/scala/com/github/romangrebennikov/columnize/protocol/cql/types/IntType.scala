package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

/**
 * Created by shutty on 10/12/15.
 */
case object IntType extends CQL.Type {
  def deserialize(raw:ByteBuffer) = IntValue(raw)
}

case class IntValue(data:Int) extends CQL.Value
object IntValue {
  def apply(raw:ByteBuffer) = new IntValue(raw.getInt)
}
