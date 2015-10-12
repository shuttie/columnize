package com.github.romangrebennikov.columnize.protocol.body

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.BinaryDecoder
import com.github.romangrebennikov.columnize.protocol.body.ResultBody.Result
import com.github.romangrebennikov.columnize.protocol.cql.types.CQL

/**
 * Created by shutty on 10/6/15.
 */

case class ResultBody(content:Result) extends Body

object ResultBody extends BinaryDecoder {
  case class Flags(globalTablesSpec:Boolean = false, hasMorePages:Boolean = false, noMetadata:Boolean = false)
  object Flags {
    def apply(raw:Int) = new Flags(
      globalTablesSpec = isEnabled(raw, 0x0001),
      hasMorePages = isEnabled(raw, 0x0002),
      noMetadata = isEnabled(raw, 0x0004)
    )
    private def isEnabled(raw:Int, value:Byte) = (raw & value) == value
  }
  case class ColumnSpec(name:String, xtype:CQL.Type)
  case class TableSpec(keyspace:Option[String], table:Option[String], columns:Seq[ColumnSpec])
  object TableSpec {
    def apply(raw:ByteBuffer, columnsCount:Int, isGlobal:Boolean) = {
      val keyspace = if (isGlobal) Some(string(raw)) else None
      val table = if (isGlobal) Some(string(raw)) else None
      val columns = for (i <- 0 until columnsCount) yield {
        val xname = string(raw)
        val xtype = option(raw)
        new ColumnSpec(xname, xtype)
      }
      new TableSpec(keyspace, table, columns)
    }
  }


  case class Metadata(flags:Flags, columnsCount:Int, pagingState:Option[String], tableSpec:Option[TableSpec])
  object Metadata {
    def apply(raw:ByteBuffer) = {
      val flags = Flags(raw.getInt)
      val columnCount = raw.getInt
      new Metadata(
        flags = flags,
        columnsCount = columnCount,
        pagingState = if (flags.hasMorePages) Some(string(raw)) else None,
        tableSpec = if (flags.noMetadata) None else Some(TableSpec(raw, columnCount, flags.globalTablesSpec))
      )
    }
  }

  case class Row(columns:Seq[CQL.Value])

  sealed trait Result
  case object Void extends Result
  case class Rows(meta:Metadata, rowsCount:Int, rowsContent:Seq[Row]) extends Result
  object Rows {
    def apply(raw:ByteBuffer) = new Rows(
      meta = Metadata(raw),
      rowsCount = raw.getInt,
      rowsContent = Nil
    )
  }

  def apply(raw:ByteBuffer):Body = {
    val buffer = raw.slice()
    buffer.getInt match {
      case 0x0001 => ResultBody(Void)
      case 0x0002 => ResultBody(Rows(buffer))
      case _ => throw new NotImplementedError()
    }
  }
}