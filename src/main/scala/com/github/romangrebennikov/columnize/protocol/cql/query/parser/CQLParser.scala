package com.github.romangrebennikov.columnize.protocol.cql.query.parser

import org.parboiled2._

/**
 * Created by shutty on 10/14/15.
 */
trait CQLParser { this:Parser =>
  def IDENTIFIER = rule { capture(oneOrMore(CharPredicate.Alpha) ~ zeroOrMore(CharPredicate.AlphaNum | '_')) ~ optional(EOI) }
  def QUOTED_IDENTIFIER = rule { '"' ~ IDENTIFIER ~ '"' }

  def CONSTANT = rule { BLOB_CONST | STRING_CONST | INTEGER_CONST | FLOAT_CONST | BOOLEAN_CONST | UUID_CONST }
  def STRING_CONST = rule { ''' ~ capture(zeroOrMore(noneOf("'") | (''' ~ '''))) ~ ''' }
  def INTEGER_CONST = rule { capture(optional("-") ~ oneOrMore(CharPredicate.Digit)) }
  def FLOAT_CONST = rule { capture("NaN" | "Infinity" | (oneOrMore(optional("-") ~ oneOrMore(CharPredicate.Digit)) ~ optional('.' ~ zeroOrMore(CharPredicate.Digit)) ~ optional(ignoreCase('e') ~ optional("+" | "-") ~ oneOrMore(CharPredicate.Digit)))) }
  def NUMBER_CONST = rule { INTEGER_CONST | FLOAT_CONST }
  def BOOLEAN_CONST = rule { capture(ignoreCase("true") | ignoreCase("false")) }
  def UUID_CONST = rule { capture(UUID(8) ~ '-' ~ UUID(4) ~ '-' ~ UUID(4) ~ '-' ~ UUID(4) ~ '-' ~ UUID(12)) }
  private def UUID(n:Int) = rule { n.times(CharPredicate.HexDigit | CharPredicate.HexLetter) }
  def BLOB_CONST = rule { capture(ignoreCase("0x") ~ oneOrMore(CharPredicate.HexDigit | CharPredicate.HexLetter)) }

  def TYPE = rule { (NATIVE_TYPE | COLLECTION_TYPE | TUPLE_TYPE) ~> ((t:String) => t)}
  def NATIVE = rule { "ascii" | "bigint" | "blob" | "boolean" | "counter" | "date" | "decimal" | "double" | "float" | "inet" | "int" | "smallint" | "text" | "time" | "timestamp" | "timeuuid" | "tinyint" | "uuid" | "varchar" | "varint" }
  def NATIVE_TYPE = rule { capture(NATIVE) }
  def COLLECTION_TYPE = rule { LIST_TYPE | SET_TYPE | MAP_TYPE }
  def LIST_TYPE = rule { capture(ignoreCase("list") ~ '<' ~ NATIVE ~ '>') }
  def SET_TYPE = rule { capture(ignoreCase("set") ~ '<' ~ NATIVE ~ '>') }
  def MAP_TYPE = rule { capture(ignoreCase("map") ~ '<' ~ NATIVE ~ ',' ~ NATIVE ~ '>') }
  def TUPLE_TYPE = rule { capture(ignoreCase("tuple") ~ '<' ~ oneOrMore(NATIVE).separatedBy(WS | ',') ~ '>') }

  def SPACE = rule { oneOrMore(" ") }
  def NEWLINE = rule { oneOrMore(optional('\r') ~ '\n') }
  def WS = rule { SPACE | NEWLINE }
}
