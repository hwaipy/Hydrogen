name := "hydrogen"
version := "0.3.0"
scalaVersion := "2.12.7"
organization := "com.hwaipy"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "org.jscience" % "jscience" % "4.3.1"
libraryDependencies += "org.scalanlp" %% "breeze" % "0.13.1"
libraryDependencies += "org.scalanlp" %% "breeze-natives" % "0.13.1"
libraryDependencies += "javax.servlet" % "javax.servlet-api" % "3.1.0"
scalacOptions ++= Seq("-feature")
scalacOptions ++= Seq("-deprecation")