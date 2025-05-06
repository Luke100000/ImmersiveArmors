package immersive_armors;

import immersive_armors.network.NetworkManagerImpl;

public class ClientMain {
    public static void postLoad() {
        //initialize network manager
        Main.networkManager = new NetworkManagerImpl();

        //finish the items
        ItemsClient.setupPieces();
    }
}
