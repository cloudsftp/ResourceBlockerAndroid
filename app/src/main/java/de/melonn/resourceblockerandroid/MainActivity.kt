package de.melonn.resourceblockerandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.preference.PreferenceManager
import de.melonn.resourceblockerandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var resources = listOf<ResourceStatus>()
    private lateinit var resourceAdapter: ResourceAdapter

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

        resourceAdapter = ResourceAdapter(resources)
        binding.content.resourceRecyclerView.adapter = resourceAdapter

        server.requestResourceIds(resourceStatsReceived, displayError)
        server.updateResource("fahrradbox1", 1, getResourceUpdatedFun("fahrradbox1"), displayError)

    }

    private val resourceStatsReceived: (Map<String, ResourceStatusResponse>) -> Unit = {
        // TODO check if all IDs exist, add missing
        // TODO update all existing IDs
    }

    private val getResourceUpdatedFun: (String) -> (ResourceStatus) -> Unit =  {
        id -> {
            // TODO get position of resource in list, update resource
        }
    }

    private val displayError: (ErrorType) -> Unit = {
        type -> this@MainActivity.runOnUiThread {
            val text = when (type) {
                ErrorType.Internal -> R.string.internal_error
                else -> R.string.connection_error
            }

            Toast.makeText(applicationContext, text, Toast.LENGTH_LONG).show()
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