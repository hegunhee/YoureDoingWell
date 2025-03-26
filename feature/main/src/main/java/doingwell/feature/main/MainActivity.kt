package doingwell.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.hegunhee.youredoingwell.ui.theme.YoureDoingWellTheme
import dagger.hilt.android.AndroidEntryPoint
import doingwell.feature.main.app.YoureDoingWellApp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YoureDoingWellTheme {
                YoureDoingWellApp()
            }
        }
    }
}
