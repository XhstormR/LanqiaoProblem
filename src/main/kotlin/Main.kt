import service.LanqiaoService

fun main(args: Array<String>) {
    LanqiaoService.saveProblems()

//    LanqiaoService.getProblems().let {
//        it.forEach { println(it) }
//        println("总共 ${it.size} 道题目")
//    }

    LanqiaoService.getProblem(215).let { println(it) }

    LanqiaoService.close()
}
