package com.purang.myluckmeasuringapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.purang.myluckmeasuringapp.dao.GameResultEntity
import com.purang.myluckmeasuringapp.dao.ResultDao

//version 은 Entity 의 구조 변경해야 하는 일이 생겼을 때 이전 구조와 현재 구조를 구분해주는 역할.
//abstract 이유
//Room 라이브러리가 이 클래스를 기반으로 데이터베이스에 대한 구현을 생성하기 때문입니다.
//데이터베이스에 액세스하는 데 필요한 모든 기능을 제공해야 하므로 개발자는
//이 클래스를 상속하고 해당 추상 메서드를 구현해야 합니다. 이렇게 하면 Room 라이브러리가 데이터베이스를 처리하고 필요한 DAO 객체를 생성할 수 있습니다.

//entitiy매개변수에는 이 데이터베이스에서 관리할 엔티티 클래스를 지정
@Database(entities = [GameResultEntity::class], version = 1)
abstract class ResultDatabase : RoomDatabase() { //이 클래스는 데이터베이스에 액세스하는 데 필요한 메서드를 제공합니다.
    /*
    * 추상 메서드로, 데이터베이스와 관련된 DAO(Data Access Object)를 반환합니다.
이 메서드를 구현하는 것은 Room 라이브러리가 수행하며, 데이터베이스와 DAO 간의 연결을 설정합니다.*/
    abstract fun resultDao() : ResultDao

    // 데이터 베이스 객체를 싱글톤으로 인스턴스.
    //여기서는 싱글톤 패턴을 사용하여 한 번 생성된 인스턴스를 공유합니다.
    companion object {
        private var instance: ResultDatabase? = null

        // @Synchronized = 동시에 여러 스레드가 getInstance 메서드를 호출할 때 동기화를 보장합니다.
        //이는 여러 스레드에서 안전하게
        //데이터베이스 인스턴스를 생성하고 접근할 수 있도록 합니다.
        @Synchronized
        fun getInstance(context: Context): ResultDatabase? {
            if (instance == null)
                synchronized(ResultDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ResultDatabase::class.java,
                        "Result.db"
                    )
                        .build()
                }
            return instance
        }

        fun destroyInstance() {
            instance = null
        }
    }
}