package OhHa.ymparisto;

public class Pubiobjekti {

    private boolean onkoEste;
    private String ulkonako;

    public Pubiobjekti() {
    }

    public Pubiobjekti(String ulkonako, boolean onkoEste) {
        this.ulkonako = ulkonako;
        this.onkoEste = onkoEste;
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
