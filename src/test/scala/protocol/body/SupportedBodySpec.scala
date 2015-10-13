package protocol.body

import java.nio.ByteBuffer

import com.github.romangrebennikov.columnize.protocol._
import com.github.romangrebennikov.columnize.protocol.Frame._
import com.github.romangrebennikov.columnize.protocol.Opcode._
import com.github.romangrebennikov.columnize.protocol.body._
import org.apache.commons.codec.binary.Hex
import org.scalatest.{WordSpecLike, MustMatchers}

/**
 * Created by shutty on 10/13/15.
 */
class SupportedBodySpec extends WordSpecLike with MustMatchers {
  "SupportedBody" must {
    "deser" in {
      val body = ByteBuffer.wrap(Hex.decodeHex("8400000006000000340002000b434f4d5052455353494f4e00020006736e6170707900036c7a34000b43514c5f56455253494f4e00010005332e332e30".toCharArray))
      assert(Frame(body) == Frame(Response,4,Flags(false,false,false,false),0,SUPPORTED,52,SupportedBody(Map("COMPRESSION" -> List("snappy", "lz4"), "CQL_VERSION" -> List("3.3.0")))))
    }
  }
}
