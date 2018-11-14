package sample;



public class InstantMessage {
		private int  cle;

    public int getCle() {
        return cle;
    }

    public String getMsg() {
        return msg;
    }

    public InstantMessage(String msg, int cle) {

        this.msg = msg;
        this.cle = cle;
    }

    private String msg;

    public void setCle(int cle) {
        this.cle = cle;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
