package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.BinaryDecoder
import com.github.romangrebennikov.columnize.protocol.cql.types.SetValue._

/**
 * Created by shutty on 10/12/15.
 */
case class MapType(key:CQL.Type, value:CQL.Type) extends CQL.Type {
  def deserialize(raw:ByteBuffer) = MapValue(raw, key, value)
}
case class MapValue(data:Map[CQL.Value,CQL.Value], key:CQL.Type, value:CQL.Type) extends CQL.Value
object MapValue extends BinaryDecoder {
  def apply(raw:ByteBuffer, key:CQL.Type, value:CQL.Type) = {
    val count = int(raw)
    val elements = for (i <- 0 until count) yield {
      val keyBytes = cell(raw)
      val keyValue = if (keyBytes.remaining() > 0) key.deserialize(keyBytes) else NullValue
      val valBytes = cell(raw)
      val valValue = if (valBytes.remaining() > 0) value.deserialize(valBytes) else NullValue

      keyValue -> valValue
    }
    new MapValue(elements.toMap, key, value)
  }
}
