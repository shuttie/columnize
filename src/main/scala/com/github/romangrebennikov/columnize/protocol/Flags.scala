package com.github.romangrebennikov.columnize.protocol

/**
 * Created by shutty on 10/5/15.
 */
case class Flags(compression:Boolean = false, tracing:Boolean = false, customPayload:Boolean = false, warning:Boolean = false)

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
