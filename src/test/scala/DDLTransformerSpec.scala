import com.github.romangrebennikov.columnize.engine.DDLTransformer
import com.github.romangrebennikov.columnize.protocol.cql.query._
import com.github.romangrebennikov.columnize.protocol.cql.query.parser.CreateTableParser
import org.scalatest.{WordSpecLike, MustMatchers}

import scala.util.{Success, Try}

/**
  * Created by shutty on 10/19/15.
  */
class DDLTransformerSpec extends WordSpecLike with MustMatchers {
  "DDL transformer" must {
    "convert tables without clustering keys" in {
      import DDLTransformer._
      val table = parse("create table foo (bar int, baz text, primary key (bar))")
      assert(table.map(_.toColumnar) == Success(Table("foo",List(Column("col","ascii"), Column("shard","int"), Column("bar","int"), Column("value","blob"), Key(Seq("col", "shard"), Seq("bar"))))))
    }
    "convert tables with clustering keys" in {
      import DDLTransformer._
      val table = parse("create table foo (bar int, baz text, qux text, primary key (bar,baz))")
      assert(table.map(_.toColumnar) == Success(Table("foo",List(Column("col","ascii"), Column("shard","int"), Column("bar","int"), Column("baz", "text"), Column("value","blob"), Key(Seq("col", "shard"), Seq("bar", "baz"))))))
    }
  }

  def parse(table:String):Try[Table] = {
    val parser = new CreateTableParser(table)
    parser.InputLine.run()
  }
}
