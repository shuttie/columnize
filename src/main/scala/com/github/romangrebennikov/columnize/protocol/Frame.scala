package com.github.romangrebennikov.columnize.protocol

import java.nio.ByteBuffer

/**
 * Created by shutty on 10/5/15.
 */
case class Frame(direction:Frame.Direction, version:Byte, flags:Flags, stream:Short, opcode:Opcode.OP, length:Int, body:ByteBuffer) {
}

object Frame {
  sealed trait Direction
  case object Request extends Direction
  case object Response extends Direction
  val RESPONSE_FLAG = 0x80.toByte
  def apply(raw:ByteBuffer) = {
    val buffer = raw.slice()
    val header = buffer.get()
    val direction = (header & RESPONSE_FLAG) == RESPONSE_FLAG match {
      case true => Response
      case false => Request
    }
    val version = (header & ~RESPONSE_FLAG).toByte
    new Frame(
      direction = direction,
      version = version,
      flags = Flags(buffer.get()),
      stream = buffer.getShort,
      opcode = Opcode(buffer.get()),
      length = buffer.getInt,
      body = buffer.slice()
    )
  }
}
