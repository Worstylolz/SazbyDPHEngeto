package src;


public class StateDPHInformation {
    private String shortCutOfState,state;
    private Float fullDPH,lowerDPH;
    private boolean isSpecialDPH;

    public StateDPHInformation(String shortCutOfState, String state, Float fullDPH, Float lowerDPH, boolean isSpecialDPH) {
        this.shortCutOfState = shortCutOfState;
        this.state = state;
        this.fullDPH = fullDPH;
        this.lowerDPH = lowerDPH;
        this.isSpecialDPH = isSpecialDPH;
    }

    public String getShortCutOfState() {
        return shortCutOfState;
    }

    public void setShortCutOfState(String shortCutOfState) {
        this.shortCutOfState = shortCutOfState;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Float getFullDPH() {
        return fullDPH;
    }

    public void setFullDPH(Float fullDPH) {
        this.fullDPH = fullDPH;
    }

    public Float getLowerDPH() {
        return lowerDPH;
    }

    public void setLowerDPH(Float lowerDPH) {
        this.lowerDPH = lowerDPH;
    }

    public boolean isSpecialDPH() {
        return isSpecialDPH;
    }

    public void setSpecialDPH(boolean specialDPH) {
        isSpecialDPH = specialDPH;
    }

}
