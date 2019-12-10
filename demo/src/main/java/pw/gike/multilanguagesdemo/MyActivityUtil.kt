package pw.gike.multilanguagesdemo

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.mallotec.reb.localeplugin.utils.ActivityUtil

/**
 * Created by reborn on 2019-12-10.
 */
object MyActivityUtil : ActivityUtil() {

    override fun customWayToUpdateInterface(context: Context, intent: Intent?) {
        Toast.makeText(context, intent?.getStringExtra("Test"), Toast.LENGTH_LONG).show()
        // TODO full your way to refresh activity
    }

}