package br.edu.eleicoes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var btCadastro: Button
    private lateinit var btVotacao: Button
    private lateinit var imageView: ImageView
    private lateinit var candidatoDAO: CandidatoDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.candidatoDAO = CandidatoDAO(this)
        this.btCadastro = findViewById(R.id.btCadastro)
        this.btVotacao = findViewById(R.id.btVotacao)

        this.imageView = findViewById(R.id.imageView)

        this.btCadastro.setOnClickListener {
            val intent = Intent(this, FormActivity::class.java)
            startActivity(intent)
        }

        this.btVotacao.setOnClickListener {
            val intent = Intent(this, VoteActivity::class.java)
            startActivity(intent)
        }

        this.candidatoDAO.update()

    }

}