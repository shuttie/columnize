package protocol.cql

import com.github.romangrebennikov.columnize.protocol.cql.query._
import com.github.romangrebennikov.columnize.protocol.cql.query.parser.CreateTableParser
import com.github.romangrebennikov.columnize.tools.ParserErrorPrintable
import org.parboiled2.ParserInput
import org.scalatest.{MustMatchers, WordSpecLike}

import scala.util.Success

/**
  * Created by shutty on 10/15/15.
  */
class CreateTableSpec extends WordSpecLike with MustMatchers with ParserErrorPrintable {
  "CreateTable parser" must {
    "parse column definitions" in {
      assert(parse("foo int").ColumnTest.run() == Success(Column("foo", "int")))
      assert(parse("foo int static").ColumnTest.run() == Success(Column("foo", "int", static = true)))
      assert(parse("foo int static primary key").ColumnTest.run() == Success(Column("foo", "int", static = true, pk = true)))
    }
    "parse key definitions" in {
      assert(parse("primary key (foo,bar)").KeyTest.run() == Success(Key(Seq("foo"), Seq("bar"))))
    }
    "parse column lists" in {
      assert(parse("foo int, baz text static").ColumnListTest.run() == Success(Seq(Column("foo", "int"), Column("baz", "text", static = true))))
    }
    "parse full table specs" in {
      assert(parse("create table foo (bar int, baz text, primary key (bar))").InputLine.run() == Success(Table("foo",Vector(Column("bar","int"), Column("baz","text"), Key(List("bar"),List())))))
    }
    "parse tables with clustering keys" in {
      assert(parse("create table foo (bar int, baz text, qux text, primary key (bar,baz))").InputLine.run() == Success(Table("foo",Vector(Column("bar","int"), Column("baz","text"), Column("qux","text"), Key(List("bar"),List("baz"))))))
    }
  }

  def parse(line:String) = new CreateTableTestParser(line)
  class CreateTableTestParser(input:ParserInput) extends CreateTableParser(input) {
    def ColumnTest = rule { ColumnDef ~ EOI }
    def ColumnListTest = rule { ColumnList ~ EOI }
    def KeyTest = rule { KeyDef ~ EOI }
  }
}
