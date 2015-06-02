import com.github.play2war.plugin._

name := "cloud"

version := "1.0-SNAPSHOT"

Play2WarPlugin.play2WarSettings

Play2WarKeys.servletVersion := "3.0"	

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.apache.directory.studio" % "org.apache.commons.io" % "2.4",
  "org.mindrot" % "jbcrypt" % "0.3m"
)

play.Project.playJavaSettings