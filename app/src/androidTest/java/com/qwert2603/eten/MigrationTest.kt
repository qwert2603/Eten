package com.qwert2603.eten

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.qwert2603.eten.data.db.EtenDataBase
import com.qwert2603.eten.data.db.migrations.Migrations
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MigrationTest {
    private val dbName = "migration-test"

    @Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        EtenDataBase::class.java.canonicalName,
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migrateAll() {
        helper.createDatabase(dbName, 1)
            .apply { close() }

        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        Room
            .databaseBuilder(targetContext, EtenDataBase::class.java, dbName)
            .addMigrations(*Migrations.all)
            .build()
            .apply {
                openHelper.writableDatabase
                close()
            }
    }

    @Test
    fun migrate1To2() {
        helper.createDatabase(dbName, 1).apply {
            execSQL("INSERT INTO `MealTable` (`uuid`, `time`) VALUES ('uuid1', '1918')")
            execSQL("INSERT INTO `MealTable` (`uuid`, `time`) VALUES ('uuid2', '1919')")

            close()
        }

        helper.runMigrationsAndValidate(dbName, 2, true, Migrations.from1to2).apply {
            val cursor = query("SELECT * FROM `MealTable` ORDER BY `uuid`")

            Assert.assertEquals(2, cursor.count)

            Assert.assertEquals("uuid1", cursor.getString(cursor.getColumnIndex("uuid")))
            Assert.assertEquals(null, cursor.getString(cursor.getColumnIndex("name")))
            Assert.assertEquals(1918, cursor.getString(cursor.getColumnIndex("time")))

            cursor.moveToNext()

            Assert.assertEquals("uuid2", cursor.getString(cursor.getColumnIndex("uuid")))
            Assert.assertEquals(null, cursor.getString(cursor.getColumnIndex("name")))
            Assert.assertEquals(1919, cursor.getString(cursor.getColumnIndex("time")))

            cursor.close()

            close()
        }
    }
}