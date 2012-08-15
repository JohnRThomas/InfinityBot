package java.awt;

import java.util.ArrayList;

public class ClientList {
	private static ArrayList<ClientActions> clients = new ArrayList<ClientActions>();

	public static void add(ClientActions client) {
		clients.add(client);
	}

	public static ClientActions get(int i) {
		return clients.get(i);
	}
	public static void remove (ClientActions client){
		clients.remove(client);
	}

	public static ArrayList<ClientActions> getList() {
		return clients;
	}

	public static void remove(int i) {
		clients.remove(i);
	}

}
