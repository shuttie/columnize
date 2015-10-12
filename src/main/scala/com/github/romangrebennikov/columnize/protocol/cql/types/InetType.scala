package com.github.romangrebennikov.columnize.protocol.cql.types

import java.net.InetAddress
import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.BinaryDecoder
import com.github.romangrebennikov.columnize.protocol.body.Body

/**
 * Created by shutty on 10/12/15.
 */
case object InetType extends CQL.Type {
  def deserialize(raw:ByteBuffer) = InetValue(raw)
}

case class InetValue(data:InetAddress) extends CQL.Value
object InetValue extends BinaryDecoder {
  def apply(raw:ByteBuffer) = new InetValue(InetAddress.getByAddress(bytes(raw)))
}
