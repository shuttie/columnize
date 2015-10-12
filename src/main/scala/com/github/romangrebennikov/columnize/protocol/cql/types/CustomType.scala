package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.BinaryDecoder
import com.github.romangrebennikov.columnize.protocol.body.Body

/**
 * Created by shutty on 10/12/15.
 */
case class CustomType(clazz:String) extends CQL.Type {
  def deserialize(raw:ByteBuffer) = CustomValue(raw)
}

case class CustomValue(data:String) extends CQL.Value
object CustomValue extends BinaryDecoder {
  def apply(raw:ByteBuffer) = new CustomValue(string(raw))
}
