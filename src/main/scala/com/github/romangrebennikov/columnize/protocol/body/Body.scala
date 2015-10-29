package com.github.romangrebennikov.columnize.protocol.body

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.Opcode
import com.github.romangrebennikov.columnize.protocol.cql.types._

/**
 * Created by shutty on 10/5/15.
 */
trait Body {
  def toBinary:ByteBuffer = ???
}
case object EmptyBody extends Body

object Body {
  def apply(opcode: Opcode.OP, data:ByteBuffer) = opcode match {
    case Opcode.STARTUP => StartupBody(data)
    case Opcode.SUPPORTED => SupportedBody(data)
    case Opcode.READY => EmptyBody
    case Opcode.QUERY => QueryBody(data)
    case Opcode.RESULT => ResultBody(data)
    case _ => EmptyBody
  }
}