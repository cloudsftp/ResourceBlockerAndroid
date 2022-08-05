package de.melonn.resourceblockerandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.preference.PreferenceManager
import de.melonn.resourceblockerandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var server: ResourceBlockerBackend

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        server = ResourceBlockerBackend(
            preferences.getString("host", "localhost")!!,
            preferences.getString("port", "5000")!!.toInt()
        )

        server.requestResourceIds(idsReceived, displayConnectionError)

    }

    private val idsReceived: (List<String>) -> Unit = {
        it.forEach { id ->
            this@MainActivity.runOnUiThread {
                findViewById<TextView>(R.id.textView).text = id
            }
        }
    }

    private val displayConnectionError: () -> Unit = {
        this@MainActivity.runOnUiThread {
            findViewById<TextView>(R.id.textView).text = "Connection Error"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}