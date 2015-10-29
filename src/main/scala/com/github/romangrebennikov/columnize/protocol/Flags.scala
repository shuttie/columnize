package com.github.romangrebennikov.columnize.protocol

/**
 * Created by shutty on 10/5/15.
 */
case class Flags(compression:Boolean = false, tracing:Boolean = false, customPayload:Boolean = false, warning:Boolean = false) {
  def toBinary = {
    val b1 = setBit(0, 0x01, compression)
    val b2 = setBit(b1, 0x02, tracing)
    val b3 = setBit(b2, 0x04, customPayload)
    val b4 = setBit(b3, 0x08, warning)
    b4
  }
  private def setBit(raw:Byte, position:Int, value:Boolean) = value match {
    case true => (raw | position.toByte).toByte
    case false => (raw & ~position.toByte).toByte
  }
}

object Flags {
  def empty = new Flags(false, false, false, false)
  def apply(raw:Byte) = {
    new Flags(
      compression = isEnabled(raw, 0x01),
      tracing = isEnabled(raw, 0x02),
      customPayload = isEnabled(raw, 0x04),
      warning = isEnabled(raw, 0x08)
    )
  }
  private def isEnabled(raw:Byte, value:Byte) = (raw & value) == value
}
