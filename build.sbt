name := "cloud"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.apache.directory.studio" % "org.apache.commons.io" % "2.4",
	"org.mindrot" % "jbcrypt" % "0.3m"
)

play.Project.playJavaSettings