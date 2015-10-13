package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.BinaryDecoder
import com.github.romangrebennikov.columnize.protocol.body.Body

/**
 * Created by shutty on 10/12/15.
 */
case object BlobType extends CQL.Type {
  def deserialize(raw:ByteBuffer) = BlobValue(raw)
}
case class BlobValue(data:Array[Byte]) extends CQL.Value
object BlobValue extends BinaryDecoder {
  def apply(raw:ByteBuffer) = new BlobValue(raw.array())
}
