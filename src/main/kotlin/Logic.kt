import network.CatFactsApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

suspend fun mainLogic() {
    try {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://catfact.ninja/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(CatFactsApi::class.java)

        println("Получаем первую страницу фактов о котах...")

        val firstPageResponse = api.getFacts(1)

        println("Информация о первой странице:")
        println(" - Текущая страница: ${firstPageResponse.currentPage}")
        println(" - Фактов на странице: ${firstPageResponse.perPage}")
        println(" - Всего фактов: ${firstPageResponse.total}")
        println(" - Последняя страница: ${firstPageResponse.lastPage}")

        val lastPageNumber = firstPageResponse.lastPage

        println("\nПолучаем последнюю страницу (страница $lastPageNumber)...")

        val lastPageResponse = api.getFacts(lastPageNumber)

        println("Факты с последней страницы:")
        lastPageResponse.data.forEachIndexed { index, fact ->
            println(" - ${index + 1}. ${fact.fact}\n(${fact.length} символов)")
        }

        val shortestFact = lastPageResponse.data.minByOrNull { it.length }

        if (shortestFact != null) {
            println("\nСамый короткий факт с последней страницы:")
            println("\"${shortestFact.fact}\"")
        } else {
            println("\nНе удалось найти факты на последней странице")
        }

    } catch (e: Exception) {
        println("Произошла ошибка: ${e.message}")
        e.printStackTrace()
    }
}