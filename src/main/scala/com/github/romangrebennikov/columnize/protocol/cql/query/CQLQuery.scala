package com.github.romangrebennikov.columnize.protocol.cql.query

/**
 * Created by shutty on 10/14/15.
 */
sealed trait Element
case class Column(name:String, xtype:String, static:Boolean = false, pk:Boolean = false) extends Element
case class Key(partition:Seq[String], clustering:Seq[String]) extends Element
case class Table(name:String, elements:Seq[Element])



trait CQLQuery {
}
