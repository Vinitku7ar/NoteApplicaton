//TO USE RETROFIT WE DECLARE CLASS SINGLETON AND WHEN WE USE HILT WE USE APPLICATION CLASS EVERYTIMES
//jo humara entry point hota hh


package com.example.noteapplicaton

import android.app.Application
import dagger.hilt.android.HiltAndroidApp



@HiltAndroidApp//APPLICATION CLASS HUME MANIFEST FILE ME BHI INTIALISE KARNA PADTA HH
class NoteApplication : Application() {


}
