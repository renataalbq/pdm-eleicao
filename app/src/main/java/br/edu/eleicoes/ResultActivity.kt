package br.edu.eleicoes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    private lateinit var tvTotalVotos: TextView
    private lateinit var tvVencedor: TextView
    private lateinit var candidatoDAO: CandidatoDAO
    private lateinit var btReiniciar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        this.tvTotalVotos = findViewById(R.id.tvTotalVotos)
        this.tvVencedor = findViewById(R.id.tvVencedor)
        this.candidatoDAO = CandidatoDAO(this)
        this.btReiniciar = findViewById(R.id.btReiniciar)

        if (intent.hasExtra("RESULTADO")){
            var votos = intent.getSerializableExtra("RESULTADO") as Int
            val msg = "Total de votos: $votos"
            this.tvTotalVotos.text = msg
        }

        val msg = "Candidato eleito: ${candidatoDAO.getCandidatoEleito()} \n\nVenceu com ${candidatoDAO.getMaiorQtdVotos()} votos\n"
        this.tvVencedor.text = msg

        this.btReiniciar.setOnClickListener(OnClickReiniciar())
    }

    inner class OnClickReiniciar: View.OnClickListener {
        override fun onClick(p0: View?) {
            this@ResultActivity.candidatoDAO.update()
            finish()
        }
    }
}