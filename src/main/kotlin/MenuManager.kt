import java.util.Scanner

// Управляет созданием, отображением и обработкой меню
class MenuManager(private val menuTitle: String) {
    private val inputScanner = Scanner(System.`in`)
    private val menuItems = mutableListOf<Pair<String, () -> Unit>>()

    fun addMenuItem(itemName: String, itemAction: () -> Unit) {
        menuItems.add(itemName to itemAction)
    }

    fun displayMenu(onExit: () -> Unit = {}) {
        while (true) {
            println(menuTitle)
            menuItems.forEachIndexed { index, (name, _) ->
                println("$index. $name")
            }
            println("${menuItems.size}. Выход")

            val userInput = inputScanner.nextLine()
            if (userInput == menuItems.size.toString()) {
                onExit()
                return
            }

            val selectedOption = userInput.toIntOrNull()
            if (selectedOption == null || selectedOption !in menuItems.indices) {
                println("Ошибка: неверный ввод. Выберите цифру в диапазоне от 0 до ${menuItems.size}")
                continue
            }

            menuItems[selectedOption].second()
        }
    }
}