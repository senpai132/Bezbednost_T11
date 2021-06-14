package adminapi.adminaplication.model.fact;

public class BlockingFact {
    private boolean blocked;

    public BlockingFact(){
        blocked = false;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}
