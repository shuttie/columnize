package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.BinaryDecoder

/**
 * Created by shutty on 10/12/15.
 */
case class MapType(key:CQL.Type, value:CQL.Type) extends CQL.Type {
  def deserialize(raw:ByteBuffer) = MapValue(raw, key, value)
}
case class MapValue(data:Map[CQL.Value,CQL.Value]) extends CQL.Value
object MapValue extends BinaryDecoder {
  def apply(raw:ByteBuffer, key:CQL.Type, value:CQL.Type) = ???
}
