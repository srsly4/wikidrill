package com.agh.wikidrill

import com.mongodb.casbah._

/**
  * Created by sirius on 24.06.17.
  */
object DatabaseProvider {
  private val mongoDatabase = MongoClient()("drill")
  com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers()
  def getDatabase: MongoDB = mongoDatabase

}

case class NotFoundException(private val message: String = "", private val cause: Throwable = None.orNull)
  extends Exception(message, cause)

trait DatabaseSupport {
  protected val database: MongoDB = DatabaseProvider.getDatabase
}
