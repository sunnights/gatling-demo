package reactor

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class LoadSimulation extends Simulation {

  // 从系统变量读取 baseUrl、path和模拟的用户数
  val latency = 100
  //  val baseUrl = System.getProperty("base.url")
  val baseUrl = "http://10.77.9.40:10000"
  val uri = System.getProperty("uri")
  //  val path = System.getProperty("path")
  val path = "?latency=" + latency
  val qps = System.getProperty("qps").toInt
  val duration = System.getProperty("duration").toInt
  val pause = duration + System.getProperty("pause").toInt

  //  val uriPath = uri + path
  //  val requestName = qps + "_" + uri

  val httpConf = http.warmUp(baseUrl).baseUrl(baseUrl)

  // 定义模拟的场景
  val uri1 = "/hello01"
  val scn1 = scenario(uri1).exec(http(qps + "_" + uri1).get(uri1 + path))
  val uri2 = "/hello02"
  val scn2 = scenario(uri2).exec(http(qps + "_" + uri2).get(uri2 + path))
  val uri3 = "/hello03"
  val scn3 = scenario(uri3).exec(http(qps + "_" + uri3).get(uri3 + path))
  val uri4 = "/hello04"
  val scn4 = scenario(uri4).exec(http(qps + "_" + uri4).get(uri4 + path))
  val uri5 = "/hello05"
  val scn5 = scenario(uri5).exec(http(qps + "_" + uri5).get(uri5 + path))
  val uri6 = "/hello06"
  val scn6 = scenario(uri6).exec(http(qps + "_" + uri6).get(uri6 + path))

  setUp(scn1.inject(constantUsersPerSec(qps) during (duration seconds)),
    scn2.inject(nothingFor(pause seconds), constantUsersPerSec(qps) during (duration seconds)),
    scn3.inject(nothingFor(pause * 2 seconds), constantUsersPerSec(qps) during (duration seconds)),
    scn4.inject(nothingFor(pause * 3 seconds), constantUsersPerSec(qps) during (duration seconds)),
    scn5.inject(nothingFor(pause * 4 seconds), constantUsersPerSec(qps) during (duration seconds)),
    scn6.inject(nothingFor(pause * 5 seconds), constantUsersPerSec(qps) during (duration seconds))
  ).protocols(httpConf)
  // 配置并发用户的数量在30秒内均匀提高至sim_users指定的数量
  //  setUp(scn1.inject(rampUsers(simUsers).during(10 seconds)).protocols(httpConf))
}