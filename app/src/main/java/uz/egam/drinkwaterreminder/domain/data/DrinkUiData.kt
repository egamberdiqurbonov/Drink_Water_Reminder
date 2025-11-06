package uz.egam.drinkwaterreminder.domain.data

data class DrinkUiData(
    val id: Long,
    val name: String,
    val amount: Int,
    val time: String,
    val isFavorite: Boolean
)
