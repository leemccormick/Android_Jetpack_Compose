package com.leemccormick.jetareader

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/* To set up project to use @HiltAndroidApp --> ViewModel
1) Create all packages and folders that are necessary for a project
2) Create Application Class such as ReaderApplication class and use @HiltAndroidApp
3) Go to Android Manifest and add name of application --> android:name=".ReaderApplication"
4) On di folder, create AppModule Object where we added provider to provide instances of anything that we needed for this applications.
5) Add @AndroidEntryPoint on MainActivity, to initial where the app start
 */

@HiltAndroidApp
class ReaderApplication : Application() {}