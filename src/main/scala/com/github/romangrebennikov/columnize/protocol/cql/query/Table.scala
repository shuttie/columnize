package com.github.romangrebennikov.columnize.protocol.cql.query

import com.github.romangrebennikov.columnize.protocol.cql.query.parser.CreateTableParser
import com.github.romangrebennikov.columnize.tools.Logging

import scala.util.{Failure, Success}

/**
 * Created by shutty on 10/14/15.
 */
sealed trait Element
case class Column(name:String, xtype:String, static:Boolean = false, pk:Boolean = false) extends Element {
  def asRegularColumn = copy(static = false, pk = false)
}
case class Key(partition:Seq[String], clustering:Seq[String]) extends Element
case class Table(name:String, elements:Seq[Element]) {
  def keys:Seq[Column] = {
    val columns = elements.flatMap {
      case e:Column => Some(e)
      case _ => None
    }
    val keys = elements.flatMap {
      case Column(name, _, _, true) => Seq(name)
      case key:Key => key.partition ++ key.clustering
      case _ => Nil
    }
    keys.flatMap(key => columns.find(_.name == key))
  }
}

object Table extends Logging {
  def apply(spec:String):Table = {
    val parser = new CreateTableParser(spec)
    parser.InputLine.run() match {
      case Success(tbl) => tbl
      case Failure(ex) =>
        log.error("cannot parse statement", ex)
        throw ex
    }
  }
}