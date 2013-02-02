package OhHa.ihmiset;

import OhHa.Sijainti;

public class Inehmo {

    private String ulkomuoto;
    private String tyyppi;
    private boolean liikkuvuus;
    private int asennePelaajaan;
    private Sijainti sijainti;
    

    public Inehmo(Sijainti sijainti, String ulkomuoto, String tyyppi, boolean liikkuvuus) {
        this.sijainti = sijainti;
        this.ulkomuoto = ulkomuoto;
        this.tyyppi = tyyppi;
        this.liikkuvuus = liikkuvuus;
    }
    
    //ehkä ei
    public void liiku(String suunta) {
        if (liikkuvuus) {
            
        }
    }
    
    public Sijainti getSijainti() {
        return this.sijainti;
    }
    
    public boolean getSankaruus() {
        if (this.tyyppi.equals("sankari")) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean getLiikkuvuus() {
        return this.liikkuvuus;
    }
    
    public String getTyyppi() {
        return this.tyyppi;
    }
    
    public String getUlkomuoto() {
        return this.ulkomuoto;
    }
    
    public int getAsenne() {
        return this.asennePelaajaan;
    }
    
    public void setAsenne(int muutos) {
        this.asennePelaajaan += muutos;
    }
    
    public void setUlkomuoto(String ulkomuoto) {
        this.ulkomuoto = ulkomuoto;
    }
    
    public void setLiikkuva(boolean liikkuva) {
        this.liikkuvuus = liikkuva;
    }
    
    public void setTyyppi(String tyyppi) {
        this.tyyppi = tyyppi;
    }
    
    
    // tarkastamatta
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (obj.getClass() == Asiakas.class) {
            return false;
        }
        
        if (obj.getClass() == Sankari.class) {
            return false;
        }
        
        if (obj.getClass() == Portsari.class) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Inehmo other = (Inehmo) obj;
        if (this.sijainti.getX() != other.getSijainti().getX()) {
            return false;
        }
        if (this.sijainti.getY() != other.getSijainti().getY()) {
            return false;
        }
        return true;
    }
    
    //tarkastamatta
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.sijainti.getX();
        hash = 37 * hash + this.sijainti.getY();
        return hash;
    }
}
