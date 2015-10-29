package com.github.romangrebennikov.columnize.protocol

/**
 * Created by shutty on 10/5/15.
 */
object Opcode {
  sealed trait OP
  case object ERROR extends OP
  case object STARTUP extends OP
  case object READY extends OP
  case object AUTHENTICATE extends OP
  case object OPTIONS extends OP
  case object SUPPORTED extends OP
  case object QUERY extends OP
  case object RESULT extends OP
  case object PREPARE extends OP
  case object EXECUTE extends OP
  case object REGISTER extends OP
  case object EVENT extends OP
  case object BATCH extends OP

  def apply(raw:Byte) = raw match {
    case 0x00 => ERROR
    case 0x01 => STARTUP
    case 0x02 => READY
    case 0x03 => AUTHENTICATE
    case 0x05 => OPTIONS
    case 0x06 => SUPPORTED
    case 0x07 => QUERY
    case 0x08 => RESULT
    case 0x09 => PREPARE
    case 0x0a => EXECUTE
    case 0x0b => REGISTER
    case 0x0c => EVENT
    case 0x0d => BATCH
    case other:Any => throw new IllegalArgumentException(s"unsupported opcode: $other")
  }

  implicit class OpcodeBinary(val opcode:OP) {
    def toBinary:Byte = opcode match {
      case ERROR => 0x00
      case STARTUP => 0x01
      case READY => 0x02
      case AUTHENTICATE => 0x03
      case OPTIONS => 0x05
      case SUPPORTED => 0x06
      case QUERY => 0x07
      case RESULT => 0x08
      case PREPARE => 0x09
      case EXECUTE => 0x0a
      case REGISTER => 0x0b
      case EVENT => 0x0c
      case BATCH => 0x0d
    }
  }
}
