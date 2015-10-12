package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

/**
 * Created by shutty on 10/12/15.
 */
case object BigintType extends CQL.Type {
  def deserialize(raw:ByteBuffer) = BigintValue(raw)
}

case class BigintValue(data:Long) extends CQL.Value
object BigintValue {
  def apply(raw:ByteBuffer) = new BigintValue(raw.getLong)
}