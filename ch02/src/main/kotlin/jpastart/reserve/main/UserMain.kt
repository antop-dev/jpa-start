package jpastart.reserve.main

import jpastart.jpa.EMF
import jpastart.reserve.application.*
import jpastart.reserve.main.UserMain.Companion.changeNameService
import jpastart.reserve.main.UserMain.Companion.getUserService
import jpastart.reserve.main.UserMain.Companion.joinService
import jpastart.reserve.main.UserMain.Companion.listService
import jpastart.reserve.main.UserMain.Companion.withdrawService
import jpastart.reserve.model.User
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDateTime

class UserMain {
    companion object {
        val joinService = JoinService()
        val getUserService = GetUserService()
        val changeNameService = ChangeNameService()
        val listService = GetUserListService()
        val withdrawService = WithdrawService()
    }
}

fun main() {
    EMF.init()

    val reader = BufferedReader(InputStreamReader(System.`in`))
    while (true) {
        println("명령어를 입력하세요:")
        val line = reader.readLine()
        val commands = line.split(" ")

        when (commands[0].toLowerCase()) {
            "exit" -> {
                println("종료합니다")
                break
            }
            "join" -> handleJoinCommand(commands)
            "view" -> handleViewCommand(commands)
            "list" -> handleListCommand()
            "changename" -> handleChangeName(commands)
            "withdraw" -> handleWithdrawCommand(commands)
            else -> println("올바른 명령어를 입력하세요.")
        }
        println("----")
    }

    EMF.close()
}

fun handleWithdrawCommand(commands: List<String>) {
    if (commands.size != 2) {
        println("명령어가 올바르지 않습니다.")
        println("사용법 : withdraw 이메일")
        return
    }
    try {
        withdrawService.withdraw(commands[1])
        println("탈퇴처리 했습니다.")
    } catch (e: UserNotFoundException) {
        println("존재하지 않습니다.")
    }
}

fun handleListCommand() {
    listService.getAllUsers().run {
        if (isEmpty()) {
            println("사용자가 없습니다.")
        } else {
            forEach {
                System.out.printf("| %s | %s | %tY-%<tm-%<td |\n", it.email, it.name, it.createDate)
            }
        }
    }
}

fun handleChangeName(commands: List<String>) {
    if (commands.size != 3) {
        println("명령어가 올바르지 않습니다.")
        println("사용법: changename 이메일 새이름")
        return
    }
    try {
        changeNameService.changeName(commands[1], commands[2])
        println("이름을 변경했습니다.")
    } catch (e: UserNotFoundException) {
        println("존재하지 않습니다.")
    }
}

fun handleViewCommand(commands: List<String>) {
    if (commands.size != 2) {
        println("명령어가 올바르지 않습니다.")
        println("사용법: view 이메일")
        return
    }
    val user = getUserService.getUser(commands[1])
    user?.run {
        println("이름: $name")
        System.out.printf("생성 : %tY-%<tm-%<td\n", createDate)
    } ?: run {
        println("존재하지 않습니다.")
    }
}

fun handleJoinCommand(commands: List<String>) {
    if (commands.size != 3) {
        println("명령어가 올바르지 않습니다.")
        println("사용법: join 이메일 이름")
        return
    }

    try {
        joinService.join(User(commands[1], commands[2], LocalDateTime.now()))
        println("가입 요청을 처리했습니다.")
    } catch (e: DuplicateEmailException) {
        println("이미 같은 이메일을 가진 사용자가 존재합니다.")
    }
}
