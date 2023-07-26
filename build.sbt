name := "loginForm"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.18",
  "javax.servlet" % "javax.servlet-api" % "4.0.1"
)



play.Project.playJavaSettings
