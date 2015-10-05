package com.github.romangrebennikov.columnize.protocol

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.body.Body

/**
 * Created by shutty on 10/5/15.
 */
case class Frame(direction:Frame.Direction, version:Byte, flags:Flags, stream:Short, opcode:Opcode.OP, length:Int, body:Body) {
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
    val flags = Flags(buffer.get())
    val stream = buffer.getShort
    val opcode = Opcode(buffer.get())
    new Frame(
      direction = direction,
      version = version,
      flags = flags,
      stream = stream,
      opcode = opcode,
      length = buffer.getInt,
      body = Body(opcode, buffer)
    )
  }
}
