package ba.etf.rma22.projekat.view.upisistrazivanje

import ba.etf.rma22.projekat.data.models.Grupa
import ba.etf.rma22.projekat.data.models.Istrazivanje
import ba.etf.rma22.projekat.data.models.Korisnik
import ba.etf.rma22.projekat.data.repositories.GrupaRepository
import ba.etf.rma22.projekat.data.repositories.IstrazivanjeRepository

class UpisViewModel {

    fun getNeupisanaIstrazivanjaByGodina(godina: Int): List<Istrazivanje> {
        return IstrazivanjeRepository.getIstrazivanjeByGodina(godina).filter { istrazivanje -> !Korisnik.upisanaIstrazivanja.contains(istrazivanje.naziv) }
    }

    fun getNeupisaneGrupeByIstrazivanje(istrazivanje: String): List<Grupa> {
        return GrupaRepository.getGroupsByIstrazivanje(istrazivanje).filter { grupa -> !Korisnik.upisaneGrupe.contains(grupa.naziv) }
    }
}