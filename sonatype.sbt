import xerial.sbt.Sonatype.GitHubHosting

sonatypeProfileName := "com.github.alexsniffin"

publishMavenStyle := true

licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

sonatypeProjectHosting := Some(GitHubHosting("alexsniffin", "packerman", "alexsniffin@gmail.com"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/alexsniffin/packerman"),
    "scm:git@github.com:alexsniffin/packerman.git"
  )
)

developers := List(
  Developer(
    id="alexsniffin",
    name="Alexander Sniffin",
    email="alexsniffin@gmail.com",
    url=url("https://github.com/alexsniffin")
  )
)