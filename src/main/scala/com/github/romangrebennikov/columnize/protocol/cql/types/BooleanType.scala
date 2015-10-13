package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.BinaryDecoder

/**
 * Created by shutty on 10/12/15.
 */
case object BooleanType extends CQL.Type {
  def deserialize(raw:ByteBuffer) = BooleanValue(raw)
}
case class BooleanValue(data:Boolean) extends CQL.Value
object BooleanValue extends BinaryDecoder {
  def apply(raw:ByteBuffer) =  raw.get() match {
    case 0 => new BooleanValue(false)
    case 1 => new BooleanValue(true)
    case _ => ???
  }
}
