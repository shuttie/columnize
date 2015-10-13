package com.github.romangrebennikov.columnize.tools

import org.slf4j.LoggerFactory

/**
 * Created by shutty on 10/13/15.
 */
trait Logging {
  lazy val log = LoggerFactory.getLogger(getClass)
}
