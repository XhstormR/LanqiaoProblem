import service.LanqiaoService

fun main(args: Array<String>) {
    LanqiaoService.saveProblems()

//    LanqiaoService.getProblemsFromWeb().let {
//        it.forEach { println(it) }
//        println("试题总数：${it.size}")
//    }

    LanqiaoService.close()
}
