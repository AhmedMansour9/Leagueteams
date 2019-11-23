package com.leagueteams.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.leagueteams.Model.TeamDetails_Response
import com.leagueteams.Model.Teams_Response
import com.leagueteams.View.TeamDetailsDao
import com.leagueteams.View.TeamsDao


@Database(entities = arrayOf(Teams_Response.Team::class,TeamDetails_Response.Squad::class),version = 1)
abstract  class  App_Database : RoomDatabase() {


    abstract fun TeamsDao(): TeamsDao
    abstract fun TeamDetailsDao(): TeamDetailsDao

    companion object {

        private var instance: App_Database? = null

        internal fun getInstance(context: Context): App_Database? {

            if (instance == null) {
                val MIGRATION_1_2: Migration = object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("CREATE TABLE IF NOT EXISTS `Team_Details` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL, `nationality` TEXT NOT NULL,`position` TEXT NOT NULL,`shirtNumber` TEXT NOT NULL, FOREIGN KEY(`forginid`) REFERENCES `Team`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
                    }
                }
                synchronized(App_Database::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        App_Database::class.java, "Db_Team"
                    ).addMigrations(MIGRATION_1_2)
                        .fallbackToDestructiveMigration() // when version increments, it migrates (deletes db and creates new) - else it crashes
                        .build()
                }
            }
            return instance
        }
    }

}