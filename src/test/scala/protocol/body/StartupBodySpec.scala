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
class StartupBodySpec extends WordSpecLike with MustMatchers {
  "StartupBody" must {
    "deser" in {
      val body = ByteBuffer.wrap(Hex.decodeHex("0400000101000000160001000b43514c5f56455253494f4e0005332e332e30".toCharArray))
      assert(Frame(body) == Frame(Request,4,Flags(false,false,false,false),1,STARTUP,22,StartupBody(Map("CQL_VERSION" -> "3.3.0"))))
    }
  }
}
