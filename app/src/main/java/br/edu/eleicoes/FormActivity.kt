package br.edu.eleicoes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class FormActivity : AppCompatActivity() {
    private lateinit var etNome: EditText
    private lateinit var etNumero: EditText
    private lateinit var btSalvar: Button
    private lateinit var btCancelar: Button
    private lateinit var dao: CandidatoDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        this.etNome = findViewById(R.id.etFormNome)
        this.etNumero = findViewById(R.id.etFormNumero)
        this.btSalvar = findViewById(R.id.btFormSalvar)
        this.btCancelar = findViewById(R.id.btFormCancelar)

        this.dao = CandidatoDAO(this)

        if (intent.hasExtra("MSG_IDA")){
            val msg = intent.getStringExtra("MSG_IDA")
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
        }

        this.btSalvar.setOnClickListener { salvar() }
        this.btCancelar.setOnClickListener { cancelar() }
    }

    private fun salvar(){
        val nome = this.etNome.text.toString()
        val numero = this.etNumero.text.toString().toInt()
        val qtd_votos = 0
        val candidato = Candidato(nome, numero, qtd_votos)
        this.dao.insert(candidato)
        Toast.makeText(this@FormActivity, candidato.toString(), Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun cancelar(){
        finish()
    }

}