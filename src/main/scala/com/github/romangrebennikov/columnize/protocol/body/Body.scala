package com.github.romangrebennikov.columnize.protocol.body

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.Opcode

/**
 * Created by shutty on 10/5/15.
 */
trait Body
case object EmptyBody extends Body

object Body {
  def apply(opcode: Opcode.OP, data:ByteBuffer) = opcode match {
    case Opcode.STARTUP => StartupBody(data)
    case Opcode.SUPPORTED => SupportedBody(data)
    case Opcode.READY => EmptyBody
    case Opcode.QUERY => QueryBody(data)
    case _ => EmptyBody
  }
  def short(raw:ByteBuffer) = raw.getShort
  def int(raw:ByteBuffer) = raw.getInt
  def string(raw:ByteBuffer) = {
    val length = raw.getShort
    val buffer = new Array[Byte](length)
    raw.get(buffer, 0, length)
    new String(buffer)
  }
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
}