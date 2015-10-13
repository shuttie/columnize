package com.github.romangrebennikov.columnize.protocol.body

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol.BinaryDecoder
import com.github.romangrebennikov.columnize.protocol.body.ResultBody.Result
import com.github.romangrebennikov.columnize.protocol.cql.types.{NullValue, CQL}
import com.github.romangrebennikov.columnize.tools.Logging

/**
 * Created by shutty on 10/6/15.
 */

case class ResultBody(content:Result) extends Body

object ResultBody extends BinaryDecoder {
  case class ResultFlags(globalTablesSpec:Boolean = false, hasMorePages:Boolean = false, noMetadata:Boolean = false)
  object ResultFlags {
    def apply(raw:Int) = new ResultFlags(
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


  case class Metadata(flags:ResultFlags, columnsCount:Int, pagingState:Option[String], tableSpec:Option[TableSpec])
  object Metadata {
    def apply(raw:ByteBuffer) = {
      val flags = ResultFlags(raw.getInt)
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
  object Row extends BinaryDecoder with Logging {
    def apply(raw:ByteBuffer, tableSpecOption: Option[TableSpec]) = tableSpecOption match {
      case Some(tableSpec) => new Row(tableSpec.columns.map( col => {
        log.debug(s"Decoding ${col.xtype}(${col.name})")
        val cellData = cell(raw)
        if (cellData.remaining() > 0)
          col.xtype.deserialize(cellData)
        else
          NullValue
      }))
      case None => ???
    }
  }

  sealed trait Result
  case object Void extends Result
  case class Rows(meta:Metadata, rowsCount:Int, rowsContent:Seq[Row]) extends Result
  object Rows {
    def apply(raw:ByteBuffer) = {
      val meta = Metadata(raw)
      val count = raw.getInt()
      val rows = for (i <- 0 until count) yield { Row(raw, meta.tableSpec) }
      new Rows(meta, count, rows)
    }
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
