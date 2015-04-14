package test

/**
 * Created by xichen on 2015/3/19.
 */
object Parser {
  def main(args: Array[String]) {

  }

  def parse(Q:String):Array[Array[String]]={

    val e = Q.split('\n')

    val p = e(0).split(",")

    var i = 1
    val queryPlan = new Array[Array[String]](e.length)
    queryPlan.update(0, p)
    while (i < e.length) {
      val arr = e(i).split("\t")

      // if this is a static triple pattern
      if (arr.length == 3) {
        val arr1 = new Array[String](4)
        arr1(0) = "0"
        arr1(1) = arr(1)
        arr1(2) = arr(0)
        arr1(3) = arr(2)
        queryPlan.update(i, arr1)
      }

      //if this is a streaming triple pattern
      else if (arr.length > 3) {
        val arr2 = new Array[String](4)
        //case (5): "s1" p ?o
        //case (6): ?s p ?o
        if (arr(1).contains("\""))
          arr2(0) = "5"
        else
          arr2(0) = "6"

        arr2(1) = arr(2)
        arr2(2) = arr(1)
        arr2(3) = arr(3)
        queryPlan.update(i, arr2)
      } //e(i).split("\t").foreach(s=>println(s+"\n"))

        //case (8): within
        //case (9): Distance
      else {
        val arr3 = new Array[String](4)

        // add the within operator to the query plan
        if (arr(0).contains("Within")) {
          arr3(0) = "8"
          arr3(1) = arr(0).substring(7, 9)
          arr3(2) = arr(0).split(",")(1) + "," + arr(0).split(",")(2)
          arr3(3) = arr(0).split(",")(3) + "," + arr(0).split(",")(4).substring(0, (arr(0).split(",")(4).length - 2))

        }
        //add the distance operator to the query plan
        else {
          arr3(0) = "9"
          arr3(1) = arr(0).substring(9, 11)
          arr3(2) = arr(0).substring(12, 14)
          arr3(3) = arr(0).substring(16, 17)
        }
        queryPlan.update(i, arr3)
      }
      i += 1
    }

    return queryPlan


  }

}