package com.github.romangrebennikov.columnize.protocol

import java.nio.ByteBuffer

/**
 * Created by shutty on 10/5/15.
 */
case class Frame(version:Byte, flags:Byte, stream:Byte, opcode:Byte, length:Int, body:ByteBuffer)

object Frame {
  def apply(raw:ByteBuffer) = {
    //raw.get
  }
}
