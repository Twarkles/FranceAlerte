import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
class Alerte {


    var titre: String = ""
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    @RequiresApi(Build.VERSION_CODES.O)
    var date = LocalDateTime.now().toString()

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(t: String, lat: Double, long: Double, date: String){
        this.titre = t
        this.latitude = lat
        this.longitude = long
        this.date = LocalDateTime.now().toString()
    }




}
/*
class Alerte {

    var titre: String = ""
    var latitude: String = ""
    var longitude: String = ""
    var date = System.currentTimeMillis()

    constructor(t: String, lat: String, long: String){
        this.titre = t
        this.latitude = lat
        this.longitude = long
    }


}
*/