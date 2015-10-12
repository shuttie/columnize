package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer
import java.util.UUID

/**
 * Created by shutty on 10/12/15.
 */
case object UUIDType extends CQL.Type {
  def deserialize(raw:ByteBuffer) = UUIDValue(raw)
}
case class UUIDValue(data:UUID) extends CQL.Value
object UUIDValue {
  def apply(raw: ByteBuffer) = new UUIDValue(new UUID(raw.getLong(raw.position + 0), raw.getLong(raw.position + 8)))
}
