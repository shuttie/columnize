package com.github.romangrebennikov.columnize.protocol.cql.query

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
