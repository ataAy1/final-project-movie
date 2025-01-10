import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val turkishLocale = Locale("tr", "TR")

    fun formatToTurkishDate(date: Date): String {
        val dateFormat = SimpleDateFormat("d MMMM yyyy", turkishLocale)
        return dateFormat.format(date)
    }

    fun formatToTurkishDateTime(date: Date): String {
        val turkishLocale = Locale("tr", "TR")
        val dateTimeFormat = SimpleDateFormat("d MMMM HH:mm", turkishLocale)

        val turkeyTimezone = TimeZone.getTimeZone("Europe/Istanbul")
        dateTimeFormat.timeZone = turkeyTimezone

        return dateTimeFormat.format(date)
    }
}
