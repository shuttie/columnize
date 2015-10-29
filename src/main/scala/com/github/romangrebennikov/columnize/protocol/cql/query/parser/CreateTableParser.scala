package com.github.romangrebennikov.columnize.protocol.cql.query.parser

import com.github.romangrebennikov.columnize.protocol.cql.query._
import org.parboiled2._

/**
 * Created by shutty on 10/14/15.
 */

class CreateTableParser(val input:ParserInput) extends Parser with CQLParser {
  def InputLine = rule { CreateTable ~ EOI }

  def CreateTable = rule { Prefix ~ WS ~ IDENTIFIER ~ WS ~ '(' ~ ColumnList ~ ')' ~> ((name:String, els:Seq[Element]) => Table(name, els)) }

  def Prefix = rule { ignoreCase("create") ~ WS ~ (ignoreCase("table") | ignoreCase("columnfamily")) ~ optional(ignoreCase("if") ~ WS ~ ignoreCase("not") ~ WS ~ ignoreCase("exists")) }

  def ColumnList = rule { oneOrMore(KeyDef | ColumnDef).separatedBy(oneOrMore(WS | ',')) }

  def ColumnDef = rule { IDENTIFIER ~ WS ~ TYPE ~ optional(WS ~ capture(ignoreCase("static"))) ~ optional(WS ~ capture(ignoreCase("primary")) ~ WS ~ ignoreCase("key")) ~> ((name:String, t:String, stat:Option[String], pk:Option[String]) => Column(name, t, stat.isDefined, pk.isDefined)) }

  def KeyDef = rule { ignoreCase("primary") ~ WS ~ ignoreCase("key") ~ WS ~ "(" ~ PartitionKey ~ optional("," ~ optional(WS) ~ oneOrMore(IDENTIFIER).separatedBy("," ~ optional(WS))) ~ ")" ~> ((pk:Seq[String], ck:Option[Seq[String]]) => Key(pk, ck.getOrElse(Nil))) }

  def PartitionKey = rule { PartitionKey1 | PartitionKeyN }
  def PartitionKey1 = rule { IDENTIFIER ~> ((key:String) => Seq(key)) }
  def PartitionKeyN = rule { "(" ~ (2 to 32).times(IDENTIFIER).separatedBy("," ~ optional(WS)) ~ ")" }

}
