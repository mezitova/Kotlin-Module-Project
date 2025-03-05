import kotlin.system.exitProcess
//
// Представляет архив с заметками
class Archive(val archiveName: String) {
    val notesList = mutableListOf<Note>()
}

// Представляет заметку с названием и содержимым
class Note(val noteTitle: String, val noteContent: String)

// Глобальный список всех архивов
val archivesList = mutableListOf<Archive>()

// Главное меню: управление архивами
fun showMainMenu(onBack: () -> Unit = { exitProcess(0) }) {
    val menu = MenuManager("Список архивов:" + if (archivesList.isNotEmpty()) " ${archivesList.joinToString { it.archiveName }}" else "")
    menu.addMenuItem("Создать архив") { createArchive { showMainMenu(onBack) } }
    archivesList.forEachIndexed { index, archive ->
        menu.addMenuItem(archive.archiveName) { showArchiveMenu(index) { showMainMenu(onBack) } }
    }
    menu.displayMenu(onBack)
}

// Создает новый архив
fun createArchive(onBack: () -> Unit) {
    println("Введите название архива:")
    val archiveName = readLine()?.trim()
    if (archiveName.isNullOrEmpty()) {
        println("Поле названия архива не может оставаться пустым.")
        onBack()
        return
    }
    archivesList.add(Archive(archiveName))
    println("Архив '$archiveName' создан.")
    onBack()
}

// Меню для работы с заметками в архиве
fun showArchiveMenu(archiveIndex: Int, onBack: () -> Unit) {
    val archive = archivesList[archiveIndex]
    val menu = MenuManager("Список заметок:" + if (archive.notesList.isNotEmpty()) " ${archive.notesList.joinToString { it.noteTitle }}" else "")
    menu.addMenuItem("Создать заметку") { createNote(archive) { showArchiveMenu(archiveIndex, onBack) } }
    archive.notesList.forEachIndexed { index, note ->
        menu.addMenuItem(note.noteTitle) { showNote(archive, index) { showArchiveMenu(archiveIndex, onBack) } }
    }
    menu.displayMenu(onBack)
}

// Создает новую заметку в архиве
fun createNote(archive: Archive, onBack: () -> Unit) {
    println("Введите название заметки:")
    val noteTitle = readLine()?.trim()
    if (noteTitle.isNullOrEmpty()) {
        println("Поле названия заметки не может оставаться пустым.")
        onBack()
        return
    }

    println("Введите текст заметки:")
    val noteContent = readLine()?.trim()
    if (noteContent.isNullOrEmpty()) {
        println("Текст заметки не может оставаться пустым.")
        onBack()
        return
    }

    archive.notesList.add(Note(noteTitle, noteContent))
    println("Заметка '$noteTitle' создана.")
    onBack()
}


// Показывает содержимое заметки и предоставляет выбор действий
fun showNote(archive: Archive, noteIndex: Int, onBack: () -> Unit) {
    val note = archive.notesList[noteIndex]
    val menu = MenuManager("Заметка: ${note.noteTitle}") // Заголовок меню с названием заметки
    menu.addMenuItem("Показать текст заметки") {
        println("Текст заметки: ${note.noteContent}") // Показываем текст заметки
        showNote(archive, noteIndex, onBack) // Возвращаемся в это же меню
    }

    menu.displayMenu(onBack) // Отображаем меню
}


fun main() {
    println("Консольное приложение для заметок")
    showMainMenu()
}

//main()