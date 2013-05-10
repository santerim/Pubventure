
package pubventure.enumit;

/**
 *
 * @author Santeri
 *
 * Luokassa määritellään pubiympäristön enumit. Suurin osa enumeista lienee
 * itsestäänselviä nimen perusteella, mutta tässä selventävä lista joistakin
 * kohteista:
 * TALUE        = tarjoilijan alue, tänne ei luoda muita kuin tarjoilijoita
 * NAKYMATON    = seinärakenne tai vastaava, joka on myös este liikkumiselle.
 *                Tätä ei piirretä kenttään.
 * MVESSA       = miesten vessa
 * NVESSA       = naisten vessa
 * VALUE        = vahtimestareiden "päivystysalue"
 */
public enum PubiobjektiEnum {
    LATTIA, POYTA, TUOLI, SEINA, TISKI, OVI, TALUE, NAKYMATON, JUKEBOKSI, ULOSKAYNTI, WCPYTTY, PISUAARI, LAVUAARI, MVESSA, NVESSA, VALUE;
}
