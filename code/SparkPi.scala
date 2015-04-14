/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package test
import org.apache.spark
import org.apache.spark._
import org.apache.spark.SparkContext._
import scala.math.random

import org.apache.spark._

/** Computes an approximation to pi */
object SparkPi {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir","E:\\hadoop")
    val Q1 = "?r \n" +
      "?r\thasGeo\t?g \n" +
      "S:\t\"People_ID1\"\ttrackedAt\t?v\t[T1]\n" +
      "Within(?g,121.5,31.0,122.0,31.5])\n"+
      "Distance(?g,?v)<2"
    //Distance(?g,?v)<2

    Parser.parse(Q1).foreach(ar=>ar.foreach(println))
    val queryPlan = Parser.parse(Q1)
    println(queryPlan.length)

//    val conf = new SparkConf().setMaster("local").setAppName("Spark Pi")
//    val spark = new SparkContext(conf)
//    val staticData = spark.textFile("hdfs://10.214.0.172/user/cx/hasGeo")
//    println(staticData.count())



    //val staticArray = staticData.split("\n")
    /*val conf = new SparkConf().setMaster("local").setAppName("Spark Pi")
    val spark = new SparkContext(conf)
    val slices = if (args.length > 0) args(0).toInt else 2
    val n = 100000 * slices
    val count = spark.parallelize(1 to n, slices).map { i =>
      val x = random * 2 - 1
      val y = random * 2 - 1
      if (x*x + y*y < 1) 1 else 0
    }.reduce(_ + _)
    println("Pi is roughly " + 4.0 * count / n)
    spark.stop()*/
  }
}
