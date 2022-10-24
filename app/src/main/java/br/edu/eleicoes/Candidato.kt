package br.edu.eleicoes

import java.io.Serializable

class Candidato: Serializable {
    var id: Int
    var nome: String
    var numero: Int
    var qtd_votos: Int

    // memória
    constructor(nome: String, numero: Int, qtd_votos: Int){
        this.id = -1
        this.nome = nome
        this.numero = numero
        this.qtd_votos = qtd_votos
    }

    // banco
    constructor(id: Int, nome: String, numero: Int, qtd_votos: Int){
        this.id = id
        this.nome = nome
        this.numero = numero
        this.qtd_votos = qtd_votos
    }

    override fun toString(): String {
        return "${nome} - ${numero}"
    }
}
