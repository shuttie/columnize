package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

/**
 * Created by shutty on 10/12/15.
 */
case object CounterType extends CQL.Type {
  def deserialize(raw:ByteBuffer) = CounterValue(raw)
}

case class CounterValue(data:Long) extends CQL.Value
object CounterValue {
  def apply(raw:ByteBuffer) = new CounterValue(raw.getLong)
}
