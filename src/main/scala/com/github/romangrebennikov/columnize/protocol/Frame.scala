package com.github.romangrebennikov.columnize.protocol

import java.nio.ByteBuffer

/**
 * Created by shutty on 10/5/15.
 */
case class Frame(direction:Frame.Direction, version:Byte, flags:Flags, stream:Byte, opcode:Byte, length:Int, body:ByteBuffer) {
}

object Frame {
  sealed trait Direction
  case object Request extends Direction
  case object Response extends Direction
  def apply(raw:ByteBuffer) = {
    val header = raw.get() match {
      case 4 => (Request, 4.toByte)
      case 84 => (Response, 4.toByte)
      case _ => throw new IllegalArgumentException("unsupported version")
    }
    new Frame(
      direction = header._1,
      version = header._2,
      flags = Flags(raw.get()),
      stream = raw.get(),
      opcode = raw.get(),
      length = raw.getInt,
      body = raw.slice()
    )
  }
}
