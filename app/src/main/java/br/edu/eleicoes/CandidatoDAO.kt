package br.edu.eleicoes

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseUtils

class CandidatoDAO {
    val banco: BancoHelper

    constructor(context: Context){
        this.banco = BancoHelper(context)
    }

    fun insert(candidato: Candidato){
        val cv = ContentValues()
        cv.put("nome", candidato.nome)
        cv.put("numero", candidato.numero)
        cv.put("qtd_votos", candidato.qtd_votos)
        this.banco.writableDatabase.insert("candidatos", null, cv)

    }

    fun read(): ArrayList<Candidato>{
        val lista = arrayListOf<Candidato>()
        val colunas = arrayOf("id", "nome", "numero", "qtd_votos")
        val c = this.banco.readableDatabase.query("candidatos", colunas, null, null, null, null, "nome")
        c.moveToFirst()
        for (i in 1 .. c.count){
            val id = c.getInt(0)
            val nome = c.getString(1)
            val numero = c.getInt(2)
            val qtd_votos = c.getInt(3)
            val candidato = Candidato(id, nome, numero, qtd_votos)
            lista.add(candidato)
            c.moveToNext()
        }
        return lista
    }

    fun find(id: Int): Candidato?{
        val colunas = arrayOf("id","nome","numero","qtd_votos")
        val where = "id = ?"
        val pwhere = arrayOf(id.toString())
        val c = this.banco.readableDatabase.query("candidatos",colunas,where,pwhere,null,null, null)
        c.moveToFirst()
        if(c.count == 1){
            val id = c.getInt(0)
            val nome = c.getString(1)
            val numero = c.getInt(2)
            val qtd_votos = c.getInt(3)
            val candidato = Candidato(id, nome, numero, qtd_votos)
            return candidato
        }
        return null
    }

    fun getMaiorQtdVotos(): Int {
        var c = banco.readableDatabase.rawQuery("SELECT MAX(qtd_votos) FROM candidatos", null)
        c.moveToFirst()
        return c.getInt(0)
    }

    fun getCandidatoEleito(): String {
        val c = banco.readableDatabase.rawQuery("SELECT * from candidatos order by qtd_votos desc limit 1", null)
        c.moveToFirst()
        return c.getString(1)

    }

    fun delete(id: Int){
        val where = arrayOf(id.toString())
        this.banco.writableDatabase.delete("candidatos", "id = ?", where)
    }

    fun delete(candidato: Candidato){
        this.delete(candidato.id)
    }

    fun update(candidato: Candidato){
        val where = "id = ?"
        val pWhere = arrayOf(candidato.id.toString())
        val cv = ContentValues()
        cv.put("qtd_votos", candidato.qtd_votos)

        this.banco.writableDatabase.update("candidatos", cv, where, pWhere)
    }

    fun update(){
        val cv = ContentValues()
        cv.put("qtd_votos", 0)

        this.banco.writableDatabase.update("candidatos", cv, null, null)
    }

}

