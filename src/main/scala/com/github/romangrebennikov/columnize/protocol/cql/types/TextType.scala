package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.BinaryDecoder
import com.github.romangrebennikov.columnize.protocol.body.Body

/**
 * Created by shutty on 10/12/15.
 */
case object TextType extends CQL.Type {
  def deserialize(raw:ByteBuffer) = TextValue(raw)

}
case class TextValue(data:String) extends CQL.Value
object TextValue extends BinaryDecoder {
  def apply(raw:ByteBuffer) = {
    new TextValue(new String(raw.array()))
  }
}
