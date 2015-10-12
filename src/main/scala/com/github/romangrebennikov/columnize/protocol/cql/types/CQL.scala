package com.github.romangrebennikov.columnize.protocol.cql.types

import java.math.{BigDecimal, BigInteger}
import java.net.InetAddress
import java.nio.ByteBuffer
import java.util.{UUID, Date}

import com.datastax.driver.core.DataType
import com.datastax.driver.core.exceptions.InvalidTypeException
import com.datastax.driver.core.utils.Bytes
import com.github.romangrebennikov.columnize.protocol.body.Body
import org.joda.time.DateTime

/**
 * Created by shutty on 10/6/15.
 */
object CQL {
  trait Type {
    def deserialize(raw:ByteBuffer)
  }
  trait Value {
    def serialize = ???
  }















}

