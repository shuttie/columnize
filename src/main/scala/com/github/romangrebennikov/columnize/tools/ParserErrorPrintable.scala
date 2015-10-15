package com.github.romangrebennikov.columnize.tools

import org.parboiled2.{ParseError, Parser}

import scala.util.{Success, Failure, Try}

/**
 * Created by shutty on 10/14/15.
 */
trait ParserErrorPrintable {
  def printError[T](parser:Parser, result:Try[T]) = result match {
    case Failure(err:ParseError) => println(parser.formatError(err))
    case Failure(ex) => println(ex)
    case Success(d) => println(d.toString.take(1000))
  }
}
