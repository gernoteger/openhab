package com.hascode.tutorial
import com.hascode.tutorial.service._

object Main {
  def main(args: Array[String]) {
    println("We're running scala..")
    val service = new SomeJavaService
    println(service.getInfo())
  }
}