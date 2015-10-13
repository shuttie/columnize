package com.github.romangrebennikov.columnize.protocol

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.cql.types._
import com.github.romangrebennikov.columnize.tools.Logging
import org.apache.commons.codec.binary.Hex

/**
 * Created by shutty on 10/12/15.
 */
trait BinaryDecoder extends Logging {
  def rawbytes(raw:ByteBuffer) = {
    val buffer = new Array[Byte](raw.remaining())
    raw.get(buffer, 0, raw.remaining())
    buffer
  }
  def cell(raw:ByteBuffer) = {
    val size = raw.getInt
    if (size < 0) {
      ByteBuffer.allocate(0)
    } else {
      val data = bytes(raw,size)
      log.debug(s"read $size bytes column: ${Hex.encodeHexString(data)}")
      ByteBuffer.wrap(data)
    }
  }
  def bytes(raw:ByteBuffer) = {
    val length = raw.getShort
    val buffer = new Array[Byte](length)
    raw.get(buffer, 0, length)
    buffer
  }
  def bytes(raw:ByteBuffer, n:Int) = {
    val buffer = new Array[Byte](n)
    raw.get(buffer, 0, n)
    buffer
  }
  def short(raw:ByteBuffer) = raw.getShort
  def ushort(raw:ByteBuffer) = {
    val length: Int = (raw.get & 0xFF) << 8
    length | (raw.get & 0xFF)
  }
  def int(raw:ByteBuffer) = raw.getInt
  def string(raw:ByteBuffer) = {
    val length = raw.getShort
    val buffer = new Array[Byte](length)
    raw.get(buffer, 0, length)
    new String(buffer)
  }
  def long(raw:ByteBuffer) = raw.getLong
  def longstring(raw:ByteBuffer) = {
    val length = raw.getInt
    val buffer = new Array[Byte](length)
    raw.get(buffer, 0, length)
    new String(buffer)
  }
  def map(raw:ByteBuffer) = {
    val length = short(raw)
    (for (i <- 0 until length) yield { string(raw) -> string(raw) }).toMap
  }
  def multimap(raw:ByteBuffer) = {
    val length = short(raw)
    (for (i <- 0 until length) yield { string(raw) -> stringlist(raw) }).toMap
  }
  def stringlist(raw:ByteBuffer) = {
    val length = short(raw)
    (for (i <- 0 until length) yield { string(raw) }).toList
  }
  def option(raw:ByteBuffer):CQL.Type = short(raw) match {
    //case 0x0000 => CustomType(string(raw))
    case 0x0001 => TextType
    case 0x0002 => BigintType
    case 0x0003 => BlobType
    case 0x0004 => BooleanType
    case 0x0005 => CounterType
    case 0x0006 => DecimalType
    case 0x0007 => DoubleType
    case 0x0008 => FloatType
    case 0x0009 => IntType
    case 0x000B => TimestampType
    case 0x000C => UUIDType
    case 0x000D => TextType
    //case 0x000E => CQL.VarintType
    case 0x000F => TimeUUIDType
    case 0x0010 => InetType
    //case 0x0011 => CQL.DateType
    //case 0x0012 => CQL.TimeType
    //case 0x0013 => CQL.SmallintType
    //case 0x0014 => CQL.TinyintType
    case 0x0020 => ListType(option(raw))
    case 0x0021 => MapType(option(raw), option(raw))
    case 0x0022 => SetType(option(raw))
    case other:Any => throw new NotImplementedError(s"not implemented type: $other")
  }
}
