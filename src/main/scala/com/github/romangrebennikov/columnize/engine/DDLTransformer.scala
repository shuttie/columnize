package com.github.romangrebennikov.columnize.engine

import com.github.romangrebennikov.columnize.protocol.cql.query.{Key, Column, Table}

/**
  * Created by shutty on 10/15/15.
  */
object DDLTransformer {
  implicit class CreateTableTransformer(val table:Table) {
    def toColumnar = {
      val rows = table.keys.map(_.asRegularColumn)
      val columns = Seq(Column("col", "ascii"), Column("shard", "int")) ++ rows ++ Seq(Column("value", "blob"))
      val key = Key(Seq("col", "shard"), rows.map(_.name))
      Table(table.name, elements = columns :+ key)
    }
  }
}
