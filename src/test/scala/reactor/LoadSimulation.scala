package reactor

import io.gatling.core.Predef._
import io.gatling.http.Predef._

import scala.concurrent.duration._

class LoadSimulation extends Simulation {

  // 从系统变量读取 baseUrl、path和模拟的用户数
  //  val baseUrl = System.getProperty("base.url")
  //  val testPath = System.getProperty("test.path")
  //  val simUsers = System.getProperty("sim.users").toInt
  val baseUrl = "http://10.77.9.40:10000"
  val path = System.getProperty("path")
  val latency = 100
  val testPath = "/" + path + "?latency=" + latency
  val simUsers = 5000
  val requestName = simUsers + "_" + path + "_" + latency

  val httpConf = http.baseUrl(baseUrl)

  // 定义模拟的请求
  val helloRequest = during(30) {
    // 自定义测试名称
    exec(http(requestName)
      // 执行get请求
      .get(testPath))
      // 模拟用户思考时间，随机1~2秒钟
      .pause(1 second)
  }

  // 定义模拟的场景
  val scn1 = scenario("hello")
    // 该场景执行上边定义的请求
    .exec(helloRequest)

  // 配置并发用户的数量在30秒内均匀提高至sim_users指定的数量
  setUp(scn1.inject(rampUsers(simUsers).during(10 seconds))
    .protocols(httpConf))
}