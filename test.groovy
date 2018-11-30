def lsCommand = 'ls'.execute()
lsCommand.waitFor()
lsCommand.text.eachLine { line ->
    println line
}