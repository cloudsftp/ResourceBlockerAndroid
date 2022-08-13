package de.melonn.resourceblockerandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import de.melonn.resourceblockerandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var server: ResourceBlockerBackend
    private val resourceAdapter = ResourceAdapter()
    private val responseHandler = ResponseHandler(resourceAdapter, this)


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

        binding.content.resourceRecyclerView.adapter = resourceAdapter

        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        binding.content.resourceRecyclerView.layoutManager = llm

        server.requestResourceIds(responseHandler)
        server.updateResource("fahrradbox1", 1, responseHandler)

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