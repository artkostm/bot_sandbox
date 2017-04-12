import info.mukel.telegrambot4s._
import info.mukel.telegrambot4s.api._
import info.mukel.telegrambot4s.methods._
import info.mukel.telegrambot4s.models._
import info.mukel.telegrambot4s.Implicits._

import scala.io.Source

/**
  * Created by artsiom.chuiko on 12/04/2017.
  */
object TestBot extends TelegramBot with Polling with Commands {
  override def token = Source.fromFile("bot.token").getLines().mkString

  on("/hello") { implicit msg => _ =>
    reply("Ди на хуй, пдр")
  }
}

object BotRunner extends App {
  TestBot.run()
}