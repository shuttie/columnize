package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

import com.datastax.driver.core.exceptions.InvalidTypeException
import com.github.romangrebennikov.columnize.protocol.BinaryDecoder

/**
 * Created by shutty on 10/12/15.
 */
case class SetType(inner:CQL.Type) extends CQL.Type {
  def deserialize(raw:ByteBuffer) = SetValue(raw, inner)
}
case class SetValue(data:Set[CQL.Value]) extends CQL.Value
object SetValue extends BinaryDecoder {
  def apply(raw:ByteBuffer, inner:CQL.Type) = {
    val count = ushort(raw)
    val elements = for (i <- 0 until count) yield {
      val size = short(raw)
      val blob = bytes(raw, size)
      inner.deserialize(ByteBuffer.wrap(blob))
    }
    new SetValue(elements.toSet)
  }
/*
  try {
    val input: ByteBuffer = bytes.duplicate
    val n: Int = getUnsignedShort(input)
    val l: Set[T] = new LinkedHashSet[T](n)
    {
      var i: Int = 0
      while (i < n) {
        {
          val s: Int = getUnsignedShort(input)
          val data: Array[Byte] = new Array[Byte](s)
          input.get(data)
          val databb: ByteBuffer = ByteBuffer.wrap(data)
          l.add(eltCodec.deserialize(databb))
        }
        ({
          i += 1; i - 1
        })
      }
    }
    return l
  }
  catch {
    case e: BufferUnderflowException => {
      throw new InvalidTypeException("Not enough bytes to deserialize a set")
    }
  }
  */
}
