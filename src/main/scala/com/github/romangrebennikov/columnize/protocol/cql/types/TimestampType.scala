package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.BinaryDecoder
import org.joda.time.DateTime

/**
 * Created by shutty on 10/12/15.
 */
case object TimestampType extends CQL.Type {
  def deserialize(raw:ByteBuffer) = TimestampValue(raw)
}
case class TimestampValue(data:DateTime) extends CQL.Value
object TimestampValue extends BinaryDecoder {
  def apply(raw:ByteBuffer) = new TimestampValue(new DateTime(long(raw)))
}
