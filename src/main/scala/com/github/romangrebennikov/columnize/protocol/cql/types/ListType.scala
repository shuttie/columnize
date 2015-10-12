package com.github.romangrebennikov.columnize.protocol.cql.types

import java.nio.ByteBuffer

/**
 * Created by shutty on 10/12/15.
 */
case class ListType(inner:CQL.Type) extends CQL.Type {
  def deserialize(raw:ByteBuffer) = ListValue(raw, inner)
}

case class ListValue(data:List[CQL.Value], inner:CQL.Type) extends CQL.Value
object ListValue {

/*  try {
    val input : ByteBuffer = bytes.duplicate
    val n : Int = getUnsignedShort(input)
    val l : List[T] = new ArrayList[T](n)

    {
      var i : Int = 0
      while (i < n) {
        {
          val s : Int = getUnsignedShort(input)
          val data : Array[Byte] = new Array[Byte](s)
          input.get(data)
          val databb : ByteBuffer = ByteBuffer.wrap(data)
          l.add(eltCodec.deserialize(databb))
        }
        ({i += 1; i - 1})
      }
    }
    return l
  }
  catch {
    case e : BufferUnderflowException => {
      throw new InvalidTypeException("Not enough bytes to deserialize list")
    }}

*/
  def apply(raw:ByteBuffer, inner:CQL.Type) = new ListValue(Nil, inner)
}
