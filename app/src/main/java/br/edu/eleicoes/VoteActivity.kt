package br.edu.eleicoes

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts

class VoteActivity : AppCompatActivity() {

    private lateinit var lvCandidatos: ListView
    private lateinit var candidatoDAO: CandidatoDAO
    private lateinit var btResultado: Button
    private lateinit var btCancelar: Button
    private var totalVotos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vote)

        this.candidatoDAO = CandidatoDAO(this)

        this.lvCandidatos = findViewById(R.id.lvMainCandidatos)
        this.btResultado = findViewById(R.id.btResultado)
        this.btCancelar = findViewById(R.id.btCancelar)

        this.lvCandidatos.setOnItemClickListener(ItemClickList())
        this.lvCandidatos.onItemLongClickListener = OnItemLongClick()

        this.atualizar()

        this.btCancelar.setOnClickListener { cancelar() }

        this.btResultado.setOnClickListener {
            val intent = Intent(this@VoteActivity, ResultActivity::class.java)
            intent.putExtra("RESULTADO", this@VoteActivity.getTotalVotos())
            intent.putExtra("VENCEDOR", this@VoteActivity.getQtdVotosCandidato())
            startActivity(intent)
        }

    }

    private fun atualizar(){
        this.lvCandidatos.adapter = ArrayAdapter<Candidato>(this, android.R.layout.simple_list_item_1, this.candidatoDAO.read())
    }

    private fun cancelar(){
        finish()
    }

    fun getTotalVotos(): Int {
        return this.totalVotos
    }
    fun setTotalVotos(totalVotos: Int) {
        this.totalVotos = totalVotos
    }

    fun getQtdVotosCandidato(): String {
        return candidatoDAO.getMaiorQtdVotos().toString()
    }

    override fun onResume() {
        super.onResume()
        this.totalVotos = 0
    }

    inner class OnItemLongClick: AdapterView.OnItemLongClickListener{
        override fun onItemLongClick(adapter: AdapterView<*>?, view: View?, index: Int, id: Long): Boolean {
            val candidato = adapter?.getItemAtPosition(index) as Candidato
            this@VoteActivity.candidatoDAO.delete(candidato.id)
            val msg = " ${candidato.nome} removido com sucesso!"
            Toast.makeText(this@VoteActivity, msg, Toast.LENGTH_SHORT).show()
            this@VoteActivity.atualizar()
            return true
        }
    }

    inner class ItemClickList: AdapterView.OnItemClickListener{
        override fun onItemClick(adapter: AdapterView<*>?, view: View?, index: Int, id: Long) {
            val candidato = adapter?.getItemAtPosition(index) as Candidato
            candidato.qtd_votos += 1
            totalVotos += 1
            this@VoteActivity.setTotalVotos(totalVotos)
            this@VoteActivity.candidatoDAO.find(candidato.id)
            this@VoteActivity.candidatoDAO.update(candidato)
            val msg = "Você votou em ${candidato.nome}"
            Toast.makeText(this@VoteActivity, msg, Toast.LENGTH_SHORT).show()
        }
    }
}