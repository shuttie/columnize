package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

/**
 * Created by shutty on 10/12/15.
 */
case class SetType(inner:CQL.Type) extends CQL.Type {
  def deserialize(raw:ByteBuffer) = SetValue(raw, inner)
}
case class SetValue(data:Set[CQL.Value]) extends CQL.Value
object SetValue {
  def apply(raw:ByteBuffer, inner:CQL.Type) = ???
}
