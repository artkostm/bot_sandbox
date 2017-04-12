import java.net.URLEncoder

import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, Uri}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.util.ByteString
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

  on("/hello", "Just to say Hello") { implicit msg => _ =>
    reply("Sorry")
  }

  val ttsApiBase = "http://translate.google.com/translate_tts?ie=UTF-8&client=tw-ob&tl=ru-ru&q="
  on("/speak", "Please, type any text you would like after the command") { implicit msg => args =>
    val text = args mkString " "
    val url = ttsApiBase + URLEncoder.encode(text, "UTF-8")
    for {
      response <- Http().singleRequest(HttpRequest(uri = Uri(url)))
      if response.status.isSuccess()
      bytes <-  Unmarshal(response).to[ByteString]
    } {
      val voiceMp3 = InputFile("voice.mp3", bytes)
      request(SendVoice(msg.sender, voiceMp3))
    }
  }
}

object BotRunner extends App {
  TestBot.run()
}