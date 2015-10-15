package protocol.cql

import com.github.romangrebennikov.columnize.protocol.cql.query.parser.CQLParser
import org.parboiled2._
import org.scalatest.{MustMatchers, WordSpecLike}

import scala.util.Success

/**
 * Created by shutty on 10/14/15.
 */
class TypeParserSpec extends WordSpecLike with MustMatchers {
  "CQL Parser" must {
    "parse native types" in {
      assert(parse("int").TYPE_TEST.run() == Success("int"))
      assert(parse("float").TYPE_TEST.run() == Success("float"))
    }
    "fail on incorrect types" in {
      assert(parse("foo").TYPE_TEST.run().isFailure)
    }
    "parse collection types" in {
      assert(parse("map<text,text>").TYPE_TEST.run() == Success("map<text,text>"))
      assert(parse("set<text>").TYPE_TEST.run() == Success("set<text>"))
      assert(parse("tuple<text,text,text>").TYPE_TEST.run() == Success("tuple<text,text,text>"))
    }
    "fail on broken collection types" in {
      assert(parse("map<text>").TYPE_TEST.run().isFailure)
      assert(parse("set<text,int>").TYPE_TEST.run().isFailure)
      assert(parse("tuple<map>").TYPE_TEST.run().isFailure)
    }

  }

  def parse(input:String) = new TypeParser(input)

  class TypeParser(val input:ParserInput) extends Parser with CQLParser {
    def TYPE_TEST = rule { TYPE ~ EOI }
  }
}
