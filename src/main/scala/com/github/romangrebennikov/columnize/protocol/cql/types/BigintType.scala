package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.BinaryDecoder

/**
 * Created by shutty on 10/12/15.
 */
case object BigintType extends CQL.Type {
  def deserialize(raw:ByteBuffer) = BigintValue(raw)
}

case class BigintValue(data:Long) extends CQL.Value
object BigintValue extends BinaryDecoder {
  def apply(raw:ByteBuffer) = new BigintValue(raw.getLong)
}