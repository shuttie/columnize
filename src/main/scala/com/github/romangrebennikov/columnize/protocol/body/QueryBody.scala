package com.github.romangrebennikov.columnize.protocol.body

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.Consistency

/**
 * Created by shutty on 10/5/15.
 */
case class QueryBody(query:String, consistency:Consistency) extends Body

object QueryBody {
  def apply(raw:ByteBuffer) = new QueryBody(Body.longstring(raw), Consistency.ANY)
}