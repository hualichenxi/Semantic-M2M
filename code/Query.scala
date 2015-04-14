package test
import org.apache.spark
import org.apache.spark._
import org.apache.spark.SparkContext._
/**
* Created by xichen on 2015/4/8.
 * */
object Query {
  def main(args: Array[String]) {
    val conf = new SparkConf().setMaster("local").setAppName("Spark Pi")

  val sc = new SparkContext(conf)
  val staticData = "People1 hasGeo  (10,101)\n" +
    "People2 hasGeo  (11,102)"


  val textFile_static = sc.textFile("hdfs://10.214.0.172/user/cx/hasGeo")
  val textFile_stream = sc.textFile("hdfs://10.214.0.172/user/cx/hasID")
  val textFile_stream2 = sc.textFile("hdfs://10.214.0.172/user/cx/trackedAt")

  val a1=textFile_static.map(line => line.split(" ")).filter(triple => triple(1).contains("hasGeo")).map(triple => (triple(0),triple(2)))

  val a2=textFile_stream.map(line => line.split(" ")).filter(triple => triple(1).contains("hasID")).map(triple => (triple(0),triple(2)))

  val a3=textFile_stream2.map(line => line.split(" ")).filter(triple => triple(1).contains("trackedAt")).map(triple => (triple(0),triple(2)))

  val a4=a1.join(a2)

  val a5=a1.cartesian(a3)

  val a1_filter = a1.filter(t => t._2.split(",")(0).toFloat<10.5&&t._2.split(",")(1).toFloat<101.5 )

  val g1="10,100"
  val g2="10.5,110"

//  val g_filter = Within(g1,g2,a1)
//
//  println(g_filter.first())
//    println(g_filter.count())

//  val result = calDistance("120.51,30.40","121.51,30.40")
//  println(result.toString)

    //println(Distance("1",a5).count())
//   val result= Distance("1",a5)
//    result.foreach(x => println(x))

    val queryPlan = new Array[Array[String]](4)

    val arr1 = new Array[String](1)
    arr1(0) = "?r"

    queryPlan.update(0,arr1)
    val arr2 = new Array[String](4)
    arr2(0) = "0"
    arr2(1) = "hasGeo"
    arr2(2) = "?r"
    arr2(3) = "?g"

    queryPlan.update(1,arr2)

    val arr3 = new Array[String](4)
    arr3(0) = "5"
    arr3(1) = "trackedAt"
    arr3(2) = "People_ID1"
    arr3(3) = "?v"

    queryPlan.update(2,arr3)

    val arr4 = new Array[String](4)
    arr4(0) = "9"
    arr4(1) = "?g"
    arr4(2) = "121.5,31.0"
    arr4(3) = "122.0,31.5"

    queryPlan.update(3,arr4)

    var flag_static = 1
    println(queryPlan(1)(0))
    val cached_static = cacheStaticTP(sc, queryPlan)
    if(cached_static.count()==1)
      flag_static = 0
    //println(flag_static)
    //cached_static.foreach(line => println(line._1))
    //new Array[Array[String]](3)
  }




  def query(Q:Array[Array[String]])={


  }

  def cacheStaticTP(sc:SparkContext, Q:Array[Array[String]]):
  org.apache.spark.rdd.RDD[(String,String)]={
    // val tmp

    if (Q(1)(0)=="0"){
      val joined_static = sc.textFile("hdfs://10.214.0.172/user/cx/"+Q(1)(1))
      val tmp=joined_static.map(line => line.split(" ")).map(triple => (triple(0),triple(2)))
      //tmp.cache()
      tmp.foreach(line => println(line._1,line._2))
      return tmp
    }

    return sc.parallelize(List(1)).map(x => (x.toString, x.toString))
  }


def Distance(d:String,g:org.apache.spark.rdd.RDD[((String,String),(String,String))])
  :org.apache.spark.rdd.RDD[((String,String),(String,String))]={

  val g_filter = g.filter(t => calDistance(t._1._2,t._2._2)<d.toInt)
    g_filter.first()
    return g_filter
  }

def Within(g1:String,g2:String,g:org.apache.spark.rdd.RDD[(String,String)])
  :org.apache.spark.rdd.RDD[(String,String)]={
    val long_min = g1.split(",")(0).toFloat
    val la_min = g1.split(",")(1).toFloat
    val long_max = g2.split(",")(0).toFloat
    val la_max = g2.split(",")(1).toFloat

    println(long_min)
    println(la_min)
    println(long_max)
    println(la_max)

    val g_filter = g.filter(t =>
      t._2.split(",")(0).toFloat>=long_min
        &&t._2.split(",")(1).toFloat>=la_min
        &&t._2.split(",")(0).toFloat<=long_max
        &&t._2.split(",")(1).toFloat<=la_max
    )
    g_filter.first()
    return g_filter
  }


  def calDistance(g1:String, g2:String):Int={

    val long1 = g1.split(",")(0).toFloat
    val lat1 = g1.split(",")(1).toFloat
    val long2 = g2.split(",")(0).toFloat
    val lat2 = g2.split(",")(1).toFloat


    val R= 6378137 // 地球半径
    val lat_1 = lat1 * Math.PI / 180.0
    val lat_2 = lat2 * Math.PI / 180.0
    val a = lat1 - lat2
    val b = (long1 - long2) * Math.PI / 180.0
    val sa2 = Math.sin(a / 2.0);
    val sb2 = Math.sin(b / 2.0);
    val d = 2*R*Math.asin(Math.sqrt(sa2*sa2 + Math.cos(lat_1)
      * Math.cos(lat_2) * sb2 * sb2));
    return (d/1000).toInt;
  }



//  val slices = if (args.length > 0) args(0).toInt else 2
//  val n = 100000 * slices
//  val count = spark.parallelize(1 to n, slices).map { i =>
//    val x = random * 2 - 1
//    val y = random * 2 - 1
//    if (x*x + y*y < 1) 1 else 0
//  }.reduce(_ + _)
//  println("Pi is rouaghly " + 4.0 * count / n)
//  spark.stop()




}

