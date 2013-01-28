
package OhHa.ymparisto;


public class PubiObjekti {

    private boolean onkoEste;
    private String ulkonako;
    
    public PubiObjekti(String ulkonako, boolean este) {
        this.ulkonako = ulkonako;
        this.onkoEste = este;
    }
    
    public String getUlkonako() {
        return this.ulkonako;
    }
    
    public boolean getEste() {
        return this.onkoEste;
    }
    
    public void setUlkonako(String ulkonako) {
        this.ulkonako = ulkonako;
    }
    
    public void setEste(boolean onkoEste) {
        this.onkoEste = onkoEste;
    }
}
