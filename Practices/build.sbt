name := "Practices"
 
version := "1.0" 
      
lazy val `practices` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"



libraryDependencies ++= Seq(
    "com.sksamuel.elastic4s" %% "elastic4s-core" % "7.6.1",
    "com.sksamuel.elastic4s" %% "elastic4s-http" % "6.7.5",
    "com.sksamuel.elastic4s" %% "elastic4s-client-esjava" % "7.6.1",
    "com.sksamuel.elastic4s" %% "elastic4s-jackson" % "6.7.8" % Test,
    "org.json4s" %% "json4s-core" % "3.5.3",
    "org.json4s" %% "json4s-jackson" % "3.5.3"
    ,jdbc , ehcache , ws , specs2 % Test , guice
  )



PlayKeys.devSettings := Seq("play.server.http.port" -> "9001")


//libraryDependencies ++= Seq(
//  "com.sksamuel.elastic4s" %% "elastic4s-core" % elastic4sVersion,
//  "com.sksamuel.elastic4s" %% "elastic4s-http" % elastic4sVersion,
//  "com.sksamuel.elastic4s" %% "elastic4s-client-esjava" % elastic4sVersion
//  ,jdbc , ehcache , ws , specs2 % Test , guice )




unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      