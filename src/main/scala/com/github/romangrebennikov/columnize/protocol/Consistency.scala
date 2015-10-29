package com.github.romangrebennikov.columnize.protocol

/**
 * Created by shutty on 10/5/15.
 */
sealed trait Consistency

object Consistency {
  case object ANY extends Consistency
  case object ONE extends Consistency
  case object TWO extends Consistency
  case object THREE extends Consistency
  case object QUORUM extends Consistency
  case object ALL extends Consistency
  case object LOCAL_QUORUM extends Consistency
  case object EACH_QUORUM extends Consistency
  case object SERIAL extends Consistency
  case object LOCAL_SERIAL extends Consistency
  case object LOCAL_ONE extends Consistency
  def apply(raw:Short) = raw match {
    case 0x0000 => ANY
    case 0x0001 => ONE
    case 0x0002 => TWO
    case 0x0003 => THREE
    case 0x0004 => QUORUM
    case 0x0005 => ALL
    case 0x0006 => LOCAL_QUORUM
    case 0x0007 => EACH_QUORUM
    case 0x0008 => SERIAL
    case 0x0009 => LOCAL_SERIAL
    case 0x000a => LOCAL_ONE
  }

  implicit class ConsistencySerializer(val con:Consistency) {
    def toBinary = (con match {
      case ANY => 0x0000
      case ONE => 0x0001
      case TWO => 0x0002
      case THREE => 0x0003
      case QUORUM => 0x0004
      case ALL => 0x0005
      case LOCAL_QUORUM => 0x0006
      case EACH_QUORUM => 0x0007
      case SERIAL => 0x0008
      case LOCAL_SERIAL => 0x0009
      case LOCAL_ONE => 0x000a
    }).toShort
  }
}
