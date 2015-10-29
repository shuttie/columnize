package com.github.romangrebennikov.columnize.protocol

import java.nio.ByteBuffer

/**
  * Created by shutty on 10/19/15.
  */
trait BinaryEncoder {
  def string(value:String) = {
    val payload = value.getBytes
    val raw = ByteBuffer.allocate(2 + payload.length)
    raw.putShort(payload.length.toShort)
    raw.put(payload)
    raw
  }
  def longstring(value:String) = {
    val payload = value.getBytes
    val raw = ByteBuffer.allocate(4 + payload.length)
    raw.putInt(payload.length)
    raw.put(payload)
    raw
  }
}
