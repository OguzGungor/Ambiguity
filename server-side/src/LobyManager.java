import java.util.ArrayList;
import java.util.Arrays;

public class LobyManager {

    // Constants
    private final int LOBY_MAX_SIZE = 10; // useless, yet.

    // Properties
    private ArrayList<Loby> lobyList;
    private int size;

    LobyManager() {
        this.lobyList = new ArrayList<Loby>();
        this.size = 0;
    }

    public boolean createNewLoby() {
        this.lobyList.add(new Loby(this.size++));
        return true;
    }

    public Loby getLoby(int index) {
        if (this.size <= 0 || index >= this.size) {
            return null;
        }

        return this.lobyList.get(index);
    }

    public boolean deleteLoby(int index) {
        if (index < 0 || index >= size) {
            return false;
        }

        this.lobyList.remove(index);
        this.size--;
        return true;
    }

    public boolean addClientToLoby(String clientName) {
        // Add client to empty Loby
        for (Loby loby : this.lobyList) {
            if (loby.hasSpace()) {
                return loby.addClient(clientName);
            }
        }

        // Create a new Loby and add client to this Loby
        if (this.createNewLoby()) {
            if (this.lobyList.get(this.size - 1).isEmpty()) {
                return this.lobyList.get(this.size - 1).addClient(clientName);
            }
        }

        return false;
    }

    public boolean addClientToLoby(int lobyID, String clientName) {
        try {
            Loby loby = this.lobyList.get(lobyID);
            if (loby.hasSpace()) {
                return loby.addClient(clientName);
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeClientFromLoby(int lobyID, String clientName) {
        if (lobyID >= this.size) {
            return false;
        }

        // Remove client from loby
        boolean val = this.lobyList.get(lobyID).removeClient(clientName);
        if (this.lobyList.get(lobyID).isEmpty()) {
            this.lobyList.remove(lobyID);
            this.size--;
        }
        return val;
    }

    public void runCommand(String opcode, String[] params) {
        try {
            int lobyID = Integer.parseInt(params[0]);
            if (GlobalVariables.getInstance().COMMANDS_GAME.get(2).equals(opcode)) {
                // Exit game
                this.lobyList.get(lobyID).removeClient(params[1]);

                if (this.lobyList.get(lobyID).getSize() <= 0) {
                    this.lobyList.remove(lobyID);
                }
                return;
            }
            System.out.println("97");
            String[] extendedParams = new String[params.length];
            for (int i = 1; i < params.length; i++) {
                extendedParams[i - 1] = params[i];
            }
            System.out.println("102");
            for (Loby loby : this.lobyList) {
                if (loby.getLobyID() == lobyID) {
                    loby.runCommand(opcode, extendedParams);
                }
            }
            System.out.println("108");
        } catch (Exception e) {
            System.out.println("[LobyManager] Exception: " + e.getMessage());
            System.out.println(opcode + ":" + Arrays.toString(params));
        }
    }

    public String toString() {
        String val = "";
        for (int i = 0; i < this.lobyList.size(); i++) {
            val += this.lobyList.get(i).toString();

            if (i < this.lobyList.size() - 1) {
                val += ";";
            }
        }
        return val;
    }
}