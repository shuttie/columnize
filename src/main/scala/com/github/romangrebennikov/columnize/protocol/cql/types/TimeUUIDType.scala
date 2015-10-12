package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer
import java.util.UUID

/**
 * Created by shutty on 10/12/15.
 */
case object TimeUUIDType extends CQL.Type {
  def deserialize(raw:ByteBuffer) = TimeUUIDValue(raw)
}
case class TimeUUIDValue(data:UUID) extends CQL.Value
object TimeUUIDValue {
  def apply(raw: ByteBuffer) = new TimeUUIDValue(new UUID(raw.getLong(raw.position + 0), raw.getLong(raw.position + 8)))
}
