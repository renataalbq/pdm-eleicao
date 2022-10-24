package br.edu.eleicoes

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BancoHelper (var context: Context): SQLiteOpenHelper(context, "eleicoes.db", null, 2) {

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "create table candidatos(id integer primary key autoincrement, nome text, numero integer, qtd_votos integer)"
        db?.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, anterior: Int, atual: Int) {
        db?.execSQL("drop table candidatos")
        this.onCreate(db)
    }
}