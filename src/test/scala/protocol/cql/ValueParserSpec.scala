package protocol.cql

import com.github.romangrebennikov.columnize.protocol.cql.query.parser.CQLParser
import com.github.romangrebennikov.columnize.tools.ParserErrorPrintable
import org.parboiled2.{CharPredicate, Parser, ParserInput}
import org.scalatest.{WordSpecLike, MustMatchers}

import scala.util.{Failure, Success}

/**
 * Created by shutty on 10/14/15.
 */
class ValueParserSpec extends WordSpecLike with MustMatchers with ParserErrorPrintable {
  "Parser" must {
    "parse string consts" in {
      assert(parse("""'foo bar'""").STRING_TEST.run() == Success("foo bar"))
    }
    "parse strings with quotes" in {
      assert(parse("""'foo''bar'""").STRING_TEST.run() == Success("foo''bar"))
    }
    "fail on incorrectly quoted escape" in {
      assert(parse("""'foo'bar'""").STRING_TEST.run().isFailure)
    }
    "parse ints" in {
      assert(parse("-1234567").INTEGER_TEST.run() == Success("-1234567"))
    }
    "fail on broken ints" in {
      assert(parse("-1234-567").INTEGER_TEST.run().isFailure)
    }
    "parse ints as floats" in {
      assert(parse("-1234567").FLOAT_TEST.run() == Success("-1234567"))
    }
    "parse floats without e" in {
      assert(parse("-1.234567").FLOAT_TEST.run() == Success("-1.234567"))
    }
    "parse floats with e" in {
      assert(parse("-1.2e34").FLOAT_TEST.run() == Success("-1.2e34"))
    }
    "fail on broken floats with multiple e" in {
      assert(parse("-1e234e567").FLOAT_TEST.run().isFailure)
    }
    "fail on broken floats with dots in e" in {
      assert(parse("-1e2.34567").FLOAT_TEST.run().isFailure)
    }
    "parse special floats" in {
      assert(parse("NaN").FLOAT_TEST.run() == Success("NaN"))
      assert(parse("Nann").FLOAT_TEST.run().isFailure)
      assert(parse("Infinity").FLOAT_TEST.run() == Success("Infinity"))
    }
    "parse booleans" in {
      assert(parse("True").BOOLEAN_TEST.run() == Success("True"))
      assert(parse("true").BOOLEAN_TEST.run() == Success("true"))
      assert(parse("TRUE").BOOLEAN_TEST.run() == Success("TRUE"))
      assert(parse("False").BOOLEAN_TEST.run() == Success("False"))
      assert(parse("FALSE").BOOLEAN_TEST.run() == Success("FALSE"))
      assert(parse("false").BOOLEAN_TEST.run() == Success("false"))
    }
    "fail on incorrent booleans" in {
      assert(parse("Yes").BOOLEAN_TEST.run().isFailure)
      assert(parse("No").BOOLEAN_TEST.run().isFailure)
    }
    "parse valid uuids" in {
      assert(parse("ece5b08c-511a-40d6-915c-6e7503d58c6b").UUID_TEST.run() == Success("ece5b08c-511a-40d6-915c-6e7503d58c6b"))
      assert(parse("ECE5b08c-511a-40d6-915c-6e7503d58c6b").UUID_TEST.run() == Success("ECE5b08c-511a-40d6-915c-6e7503d58c6b"))
    }
    "fail on broken uuids" in {
      assert(parse("ece5b08c-511a-40d6-915c-6e7503d58c6").UUID_TEST.run().isFailure)
      assert(parse("ece5b08c-511a-40d6-915cf6e7503d58c6b").UUID_TEST.run().isFailure)
      assert(parse("xce5b08c-511a-40d6-915c-6e7503d58c6b").UUID_TEST.run().isFailure)
      assert(parse("ece-b08c-511a-40d6-915c-6e7503d58c6b").UUID_TEST.run().isFailure)
    }
    "parse blobs" in {
      assert(parse("0xDEADBEEF").BLOB_TEST.run() == Success("0xDEADBEEF"))
    }
    "fail on broken blobs" in {
      assert(parse("OxFFF").BLOB_TEST.run().isFailure)
    }
    "parse using constant parser" in {
      assert(parse("0xFFF").CONSTANT_TEST.run() == Success("0xFFF"))
      assert(parse("""'foo bar'""").CONSTANT_TEST.run() == Success("foo bar"))
      assert(parse("-1234567").CONSTANT_TEST.run() == Success("-1234567"))
      assert(parse("0").CONSTANT_TEST.run() == Success("0"))
      assert(parse("-1e2.34567").CONSTANT_TEST.run().isFailure)
      assert(parse("True").CONSTANT_TEST.run() == Success("True"))
      assert(parse("ece5b08c-511a-40d6-915c-6e7503d58c6b").CONSTANT_TEST.run() == Success("ece5b08c-511a-40d6-915c-6e7503d58c6b"))

    }
  }

  class TestParser(val input:ParserInput) extends Parser with CQLParser {
    def STRING_TEST = rule { STRING_CONST ~ EOI }
    def INTEGER_TEST = rule { INTEGER_CONST ~ EOI }
    def FLOAT_TEST = rule { FLOAT_CONST ~ EOI }
    def BOOLEAN_TEST = rule { BOOLEAN_CONST ~ EOI }
    def UUID_TEST = rule { UUID_CONST ~ EOI }
    def BLOB_TEST = rule { BLOB_CONST ~ EOI }
    def CONSTANT_TEST = rule { CONSTANT ~ EOI }
  }
  def parse(input:String) = new TestParser(input)
}
