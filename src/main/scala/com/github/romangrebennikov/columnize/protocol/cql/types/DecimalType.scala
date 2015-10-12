package com.github.romangrebennikov.columnize.protocol.cql.types

import java.math.{BigInteger, BigDecimal}
import java.nio.ByteBuffer

/**
 * Created by shutty on 10/12/15.
 */
case object DecimalType extends CQL.Type {
  def deserialize(raw:ByteBuffer) = DecimalValue(raw)
}

case class DecimalValue(data:BigDecimal) extends CQL.Value
object DecimalValue {
  def apply(raw:ByteBuffer) = {
    if (raw.remaining < 4) throw new IllegalArgumentException("Invalid decimal value, expecting at least 4 bytes but got " + raw.remaining)
    val scale: Int = raw.getInt
    val bibytes: Array[Byte] = new Array[Byte](raw.remaining)
    raw.get(bibytes)
    val bi: BigInteger = new BigInteger(bibytes)
    new DecimalValue(new BigDecimal(bi, scale))
  }
}
